package net.sjl.fragrans.net;

import java.util.Vector;

public class TransportLayer extends Thread {
    public static final int MAX_SEQ = 7; // 3bit sequence number
    public static final int EVENT_PACKET_ARRIVAL = 0;
    public static final int EVENT_TIMEOUT = 1;
    public static final int EVENT_MSG_READY = 2;
    public static final long TIMEOUT = 1000; // in millisecond

    // temporary send buffer to store raw payload from application
    private Vector m_currentMsgs = new Vector(1);

    // receive buffer that stores the packets that have arrived
    // in order
    private Vector m_recvBuffer = new Vector(MAX_SEQ+1);

    // internal send buffer. It stores the packets to be sent
    // instead of the raw application payload
    private Packet[] m_packetsToSend = new Packet[MAX_SEQ+1];

    // number of packets buffered for sending
    private int m_numBuffered = 0;

    private Vector m_events = new Vector(10);

    private NetworkLayer m_networkLayer = null;

    public TransportLayer(int senderPort, int receiverPort) {
        m_networkLayer = new NetworkLayer(senderPort, receiverPort);
        m_networkLayer.setTransportLayer(this);
    }

    int increment(int seq) {
        int newseq;
        if(seq < MAX_SEQ)
            newseq = seq + 1;
        else
            newseq = 0;
        return newseq;
    }

    void sendToNetworkLayer(Packet packet) {
        // We do not retransmit null (ack) packets
        if(packet.length > 0)
            packet.setTransportLayer(this); // to enable timeout callback
        packet.printWithPayload("   TL:Send");

        m_networkLayer.send(packet.toBytes());
        if(packet.length > 0)
            packet.startTimer();
    }

    Packet receiveFromNetworkLayer() {
        byte[] receivedData = m_networkLayer.receive();
        if(null == receivedData)
            return null;
        Packet packet = new Packet(receivedData);
        return packet;
    }

    public synchronized int waitForEvent() {

        while(m_events.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        if(m_events.isEmpty())
            return -1;
        else {
            Integer event = (Integer)m_events.remove(0);
            return event.intValue();
        }
    }

    public synchronized void onPacketArrival() {
        // if there is already a packet arrival event scheduled to be
        // handled, do nothing
        for(int i=0; i<m_events.size(); i++) {
            Integer event = (Integer)m_events.elementAt(i);
            if(EVENT_PACKET_ARRIVAL == event.intValue()) {
                return;
            }
        }

        m_events.add(EVENT_PACKET_ARRIVAL);
        notifyAll();
    }

    public synchronized void onTimeout() {
        // if there is already a timeout event scheduled to be handled
        // do nothing
        for(int i=0; i<m_events.size(); i++) {
            Integer event = (Integer)m_events.elementAt(i);
            if(EVENT_TIMEOUT == event.intValue()) {
                return;
            }
        }
        m_events.add(EVENT_TIMEOUT);
        notifyAll();
    }

    // API provided to upper layer (application layer) to
    // send a message. Unlike TCP, our reliable transfer protocol
    // preserves the message boundary. As a trade-off, we do not
    // provide fragmentation service, i.e., the application level
    // message must be smaller or equal to the max payload allowed
    public synchronized int send(String msg) {
        // If the send buffer is full, stop take messages from apps
        // From Tanenbaum's book:
        // "Note that a maximum of MAX_SEQ frames and not MAX_SEQ + 1
        // frames may be outstanding at any instant, even though there
        // are MAX_SEQ + 1 distinct sequence numbers: 0, 1, 2, ..., MAX_SEQ.
        // To see why this restriction is required, consider the following
        // scenario with MAX_SEQ = 7.
        // 1. The sender sends frames 0 through 7.
        // 2. A piggybacked acknowledgement for frame 7 eventually comes
        //    back to the sender.
        // 3. The sender sends another eight frames, again with sequence
        //    numbers 0 through 7.
        // 4. Now another piggybacked acknowledgement for frame 7 comes in.
        // The question is this: Did all eight frames belonging to the
        // second batch arrive successfully, or did all eight get lost
        // (counting discards following an error as lost)? In both cases
        // the receiver would be sending frame 7 as the acknowledgement.
        // The sender has no way of telling. For this reason the
        // maximum number of outstanding frames must be restricted to MAX_SEQ.
        if(m_numBuffered + m_currentMsgs.size()> MAX_SEQ) {
            //System.out.println("Send buffer full: "+MAX_SEQ+"["+m_numBuffered);
            return -1;
        }

        // perform msg size check, if msg is too big, return -1
        // and send nothing
        if(msg.length() > Packet.MAX_PACKET_PAYLOAD) {
            System.out.println("Message too big");
            return -1;
        }

        m_currentMsgs.add(msg);

        m_events.add(EVENT_MSG_READY);
        notifyAll();
        return msg.length();
    }

    // API provided to upper layer (application layer) to
    // receive a message. The caller will be blocked if
    // no message available. For simplicity, we assume
    // the buffer provided by the receiver is big enough
    // to hold the complete message
    public synchronized int recv(byte[] buffer) {
        // have to simulate blocking when no msg is available
        while(m_recvBuffer.isEmpty()) {
            try {
                wait();
            }catch (InterruptedException e) {}
        }
        // retrieve the first msg stored in the recv buffer (actually a vector)
        Packet p = (Packet)m_recvBuffer.remove(0);
        for(int i=0; i<p.length; i++)
            buffer[i] = p.payload[i];
        assert(p.length > 0);
        return p.length;
    }

    // Main go-back-n protocol implementation
    // to be completed by students
    public void run() {
        int nextPacketToSend = 0;
        int ackExpected = 0;
        int packetExpected = 0;
        Packet packet;

        while(true) {
            int event = waitForEvent();
            switch(event) {
                case EVENT_MSG_READY: // higher layer has msg to send
                    // accept, save, and transmit a new packet

                    packet = new Packet();
                    getPacketToSend(packet);
                    m_packetsToSend[nextPacketToSend] = packet;

                    // to be completed

                    break;

                case EVENT_PACKET_ARRIVAL:
                    packet = receiveFromNetworkLayer();

                    // to be completed

                    break;

                case EVENT_TIMEOUT: // trouble; retransmit all outstanding packets

                    // to be completed

            }
        }
    }

    void getPacketToSend(Packet packet) {
        String msg = (String)m_currentMsgs.remove(0);
        byte[] sendData = msg.getBytes();
        packet.length = sendData.length;
        packet.payload = sendData;
    }

    void deliverPacket(Packet packet) {
        m_recvBuffer.add(packet);
    }

    boolean between(int a, int b, int c) {
        // return true if a <= b < c circularly; false otherwise.
        if(((a <= b) && (b < c)) || ((c < a) && (a <= b)) ||
                ((b < c) && (c < a)))
            return true;
        else
            return false;
    }
}

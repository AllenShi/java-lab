package net.sjl.fragrans.net;

import java.util.TimerTask;

public class Packet {
    public static final int MAX_PACKET_PAYLOAD = 1024;
    public static final int HEADER_LEN = 12; // size of 3 integers
    // max_packet_size = payload length + header fields
    public static final int MAX_PACKET_SIZE = MAX_PACKET_PAYLOAD +
            HEADER_LEN;

    public int seq; // sequence number for duplicate detection
    public int ack; // acked sequence number
    public int length; // payload length
    public byte[] payload = new byte[MAX_PACKET_PAYLOAD];

    private java.util.Timer m_timer;
    private SendTimerTask m_timerTask = new SendTimerTask();
    // set right before initial sending to enable timeout callback
    private TransportLayer m_transportLayer = null;

    // constructor to be used when sending
    public Packet() {
        seq = -1;
        ack = -1;
        length = 0;
    }

    // constructor to be used when receiving
    public Packet(byte[] receivedData) {
        int index = 0;

        // set seq number
        byte[] intArray = {receivedData[index], receivedData[index+1],
                receivedData[index+2], receivedData[index+3]};
        seq = ByteArrayUtils.readInt(intArray);
        index += 4;

        // set ack sequence number
        intArray[0] = receivedData[index++];
        intArray[1] = receivedData[index++];
        intArray[2] = receivedData[index++];
        intArray[3] = receivedData[index++];
        ack = ByteArrayUtils.readInt(intArray);

        // payload length
        intArray[0] = receivedData[index++];
        intArray[1] = receivedData[index++];
        intArray[2] = receivedData[index++];
        intArray[3] = receivedData[index++];
        length = ByteArrayUtils.readInt(intArray);

        for(int i=0; i<length; i++) {
            payload[i] = receivedData[index+i];
        }
    }

    public void setTransportLayer(TransportLayer tl) {
        m_transportLayer = tl;
    }

    public byte[] toBytes() {
        // we want to construct a byte array consisting
        // a sequence number, an ack,
        // a length field (4 bytes), and the payload
        int totalLen = length + HEADER_LEN;
        byte[] data = new byte[totalLen];
        //System.out.println("Send total length: "+totalLen);

        int i = 0;
        byte[] intArray = new byte[4];
        ByteArrayUtils.writeInt(intArray, seq);
        int k = 0;
        data[i++] = intArray[k++];
        data[i++] = intArray[k++];
        data[i++] = intArray[k++];
        data[i++] = intArray[k++];

        ByteArrayUtils.writeInt(intArray, ack);
        k = 0;
        data[i++] = intArray[k++];
        data[i++] = intArray[k++];
        data[i++] = intArray[k++];
        data[i++] = intArray[k++];

        ByteArrayUtils.writeInt(intArray, length);
        k = 0;
        data[i++] = intArray[k++];
        data[i++] = intArray[k++];
        data[i++] = intArray[k++];
        data[i++] = intArray[k++];

        for(int j=0; j<length; j++,i++)
            data[i] = payload[j];

        return data;
    }

    void startTimer() {
        print("...start timer for packet");
        try {
            if(m_timer != null)
                m_timer.cancel();
            m_timer = new java.util.Timer();
            m_timer.schedule(new SendTimerTask(), TransportLayer.TIMEOUT);
        } catch(Exception e) {}
    }

    void stopTimer() {
        print("...cancel timer for packet");
        try {
            m_timer.cancel();
            m_timer = null;
        } catch(Exception e) {}
    }

    public class SendTimerTask extends TimerTask {
        public void run() {
            print("...timeout for packet");
            m_transportLayer.onTimeout();
        }
    }

    public void printWithPayload(String comment) {
        System.out.println(comment+": seq="+seq+", ack="+ack+", len="+length+
                " \""+new String(payload)+"\"");
    }
    public void print(String comment) {
        System.out.println(comment+": seq="+seq+", ack="+ack+", len="+length);
    }
}

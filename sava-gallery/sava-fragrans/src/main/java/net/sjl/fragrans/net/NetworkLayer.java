package net.sjl.fragrans.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
import java.util.Vector;

public class NetworkLayer {
    private InetAddress m_IPAddress;
    private DatagramSocket m_socket = null;
    private int m_localport = 0;
    private int m_remoteport = 0;

    private Vector m_receiveBuffer = new Vector(10);
    private TransportLayer m_transportLayer = null;

    public NetworkLayer() { }

    public NetworkLayer(int localport, int remoteport) {
        m_localport = localport;
        m_remoteport = remoteport;
        try {
            m_IPAddress = InetAddress.getByName("localhost");
            m_socket = new DatagramSocket(localport);
        } catch(Exception e) {
            System.out.println("Cannot create UDP socket: "+e);
        }

        // start the reading thread
        new ReadThread().start();
    }

    public void setTransportLayer(TransportLayer tl) {
        m_transportLayer = tl;
    }

    public void send(byte[] payload) {
        // simulate random loss of packet
        Random rand = new Random();
        int randnum = rand.nextInt(10); // range 0-10
        if(randnum < 3) {
            //System.out.println("X");
            return; // simulate a loss
        }

        try {
            DatagramPacket p =
                    new DatagramPacket(payload, payload.length,
                            m_IPAddress, m_remoteport);
            m_socket.send(p);
        } catch(Exception e) {
            System.out.println("Error sending packet: "+e);
        }
    }

    // Interface provided to the data link layer to pick
    // up message received
    public byte[] receive() {
        if(m_receiveBuffer.size() > 0)
            return ((DatagramPacket)m_receiveBuffer.remove(0)).getData();
        else
            return null;
    }

    // Thread to read packets arrived from the network and to notify
    // the data link layer
    public class ReadThread extends Thread {
        public void run() {
            while(true) {
                // if there is buffered received packets, notify
                // the transport layer instead of blocking reading
                if(m_receiveBuffer.size() > 0) {
                    if(m_transportLayer != null)
                        m_transportLayer.onPacketArrival();
                    continue;
                }

                byte[] buffer = new byte[Packet.MAX_PACKET_SIZE];
                DatagramPacket p =
                        new DatagramPacket(buffer, buffer.length);

                try {
                    m_socket.receive(p);
                } catch(Exception e) {
                    System.out.println("Cannot receive from socket: "+e);
                }

                m_receiveBuffer.add(p);

                if(m_transportLayer != null)
                    m_transportLayer.onPacketArrival();
            }
        }
    }
}
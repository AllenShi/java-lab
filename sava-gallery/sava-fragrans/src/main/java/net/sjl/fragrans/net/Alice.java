package net.sjl.fragrans.net;

public class Alice {
    public static final int RECEIVER_PORT = 9888;
    public static final int SENDER_PORT = 9887;

    public static void main(String args[]) throws Exception {
        TransportLayer tl =
                new TransportLayer(SENDER_PORT, RECEIVER_PORT);
        // Start the transport layer protocol thread
        tl.start();

        int counter = 0;
        byte[] receiveBuffer = new byte[Packet.MAX_PACKET_PAYLOAD];
        // exercise the send and recv functionality
        while(true) {
            // We want to send a full window of packets at a time
            for(int i=0; i<2; i++) {
                String msgToSend = "[Alice]-("+counter+"): It is a nice day.";
                System.out.println(">>>App:"+msgToSend);
                while(true) {
                    int status = tl.send(msgToSend);
                    if(status > 0)
                        break;
                }
                counter++;
            }
            int len = tl.recv(receiveBuffer);
            byte[] payload = new byte[len];
            for(int i=0; i<len; i++) {
                payload[i] = receiveBuffer[i];
            }
            //System.out.println("$");
            String msgRecvd = new String(payload);
            System.out.println("<<<App:"+new String(msgRecvd));
        }
    }
}

package peer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Enumeration;

import CryptoCurrancy.Transication;
import sun.security.x509.IPAddressName;

public class Mserver extends Thread {

    private long FIVE_SECONDS = 5000;

    protected MulticastSocket socket;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;
    InetAddress group;

    public Mserver() throws IOException {
        super("MulticastServerThread");
        socket = new MulticastSocket(4446);
        group = InetAddress.getByName("230.0.0.1");
        in = new BufferedReader(new FileReader("/home/mego/Desktop/SoftWareAssignment3/test/test/one-liners.txt"));
    }
    
  public void needInitialData() throws IOException
  {
	  byte[] message="SendIntialData".getBytes();
	  DatagramPacket packet = new DatagramPacket(message, message.length, group, 4446);
	  socket.send(packet);
  }
    
    public void multicating(String multicastingData) throws IOException
    {
    	byte[] sendData=multicastingData.getBytes();
    	  DatagramPacket packet = new DatagramPacket(sendData, sendData.length, group, 4446);
    	  socket.send(packet);
    	  
    }

    public void run() {
        while (moreQuotes) {
            try {
                byte[] buf = new byte[256];

                // construct quote
                String dString;
                if (in == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();
                buf = dString.getBytes();

                // send it
                
               DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
           

                socket.send(packet);

                // sleep for a while
                try {
                    sleep((long)(Math.random() * FIVE_SECONDS));
                } catch (InterruptedException e) { }

            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        socket.close();
    }

    protected String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
                moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }
}

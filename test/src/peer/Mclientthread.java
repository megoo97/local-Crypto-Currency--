package peer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import CryptoCurrancy.Transication;

public class Mclientthread implements Runnable  {
    MulticastSocket socket = null;
    Socket singleSocket;
    InetAddress address;
    InetAddress address1;
    Boolean terminate;
    Set<String>ips=new HashSet<>();
    ArrayList<Transication>transaction;
    
    void terminateConnection()
    {
    	terminate=true;
    }
    
    void sendIntialData(InetAddress ipAddress,Integer port, byte [] localIp) throws IOException
    {
    	DatagramSocket socket=new DatagramSocket();
    	DatagramPacket packet=new DatagramPacket(localIp, localIp.length, ipAddress,port);
    	socket.send(packet);
    	socket.close();
    }
    
   public void SendData(byte[] data,Integer port) throws IOException
    {
    	DatagramSocket socket=new DatagramSocket();
    	for(String x:ips)
    	{
    		InetAddress myIp=InetAddress.getByName(x);
    	DatagramPacket packet=new DatagramPacket(data, data.length,myIp ,port);
    	socket.send(packet);
    	}
    	socket.close();
    }
    
   public String getIp() throws SocketException
    {
    	Enumeration interfaces = null;	
    	String ip = null;
    	interfaces = NetworkInterface.getNetworkInterfaces();
    	 while(interfaces.hasMoreElements())
         {
         NetworkInterface x=(NetworkInterface)interfaces.nextElement();
         Enumeration v= x.getInetAddresses();
         while(v.hasMoreElements())
         {
         InetAddress z=(InetAddress)v.nextElement();
          ip=z.getHostAddress();
         if(ip.contains("192"))
         {
        // System.out.println(ip);
         break;
         }
         
         
         }
         
         if(ip.contains("192"))
         {
         	break;
         }
         }
    	 return ip;
    }
   ArrayList<Transication> getTransaction()
   {
	   ArrayList<Transication>sendTransaction=new ArrayList<>();
	   for(int i=0;i<transaction.size();i++)
		   sendTransaction.add(transaction.get(i));
	   transaction.clear();
	   return transaction;
   }
    Mclientthread() throws IOException{
        socket = new MulticastSocket(4446);
        address = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(address);
        terminate=false;
        transaction=new ArrayList<>();
     
    }
    @Override
    public void run() {
        DatagramPacket packet;
        // get a few quotes
        while (true){
        
			
            String ip = null;
           
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            
           
            try {
                socket.receive(packet);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String received = new String(packet.getData(), 0, packet.getLength());
           try {
			if(!(getIp()).equals(packet.getAddress().toString().substring(1)))
			   {
				if(received.equals("SendIntialData"))
				{
					sendIntialData(packet.getAddress(), 4446, getIp().toString().getBytes());
				}
				else if(received.contains("192."))
				{
					ips.add(received);
				}
				
				else if(received.contains("Transaction"))
				{
				  String[] Transaction=received.split("|");
				  Transication t=new Transication(Transaction[1], Transaction[2], Transaction[3]);
				  transaction.add(t);
				}
			    System.out.println("Quote of the Moment: " + received+packet.getAddress()); 
			   }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            if(terminate) break;
        }

        try {
            socket.leaveGroup(address);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        socket.close();

    }


}

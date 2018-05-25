package User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import CryptoCurrancy.Block;
import CryptoCurrancy.Transication;
import peer.Mclientthread;
import peer.Mserver;

public class Client {
	private Mserver server;
	private Mclientthread client;
	private ArrayList<Transication>pendingTransaction;
	private Set<InetAddress>networkIPS;
	private String IP;
	
	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public Mserver getServer() {
		return server;
	}

	public void setServer(Mserver server) {
		this.server = server;
	}

	public Mclientthread getClient() {
		return client;
	}

	public void setClient(Mclientthread client) {
		this.client = client;
	}

	public ArrayList<Transication> getPendingTransaction() {
		return pendingTransaction;
	}

	public void setPendingTransaction(ArrayList<Transication> pendingTransaction) {
		this.pendingTransaction = pendingTransaction;
	}

	public Set<InetAddress> getNetworkIPS() {
		return networkIPS;
	}

	public void setNetworkIPS(Set<InetAddress> networkIPS) {
		this.networkIPS = networkIPS;
	}

	public Client(Mserver server, Mclientthread client, ArrayList<Transication> pendingTransaction,
			Set<InetAddress> networkIPS) throws SocketException {
		super();
		this.server = server;
		this.client = client;
		this.pendingTransaction = pendingTransaction;
		this.networkIPS = networkIPS;
		this.IP = client.getIp();
		try {
			server.needInitialData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void CreateTransaction() throws IOException
	{
		Scanner in = new Scanner(System.in);
		String ToAddress = in.next();
		String TransactionAmount = in.next();
		in.close();
		String FromAddress = IP;
		Transication t = new Transication(FromAddress, ToAddress, TransactionAmount);
		pendingTransaction.add(t);
		String data=prepareTransaction(t);
		client.SendData(data.getBytes(), 4446);
	}
	
	public String prepareTransaction(Transication transaction)
	{
	
		String data="";
		data+="Transaction"+"|";
		data+=transaction.getToAddress()+"|"+transaction.getFromAddress()+"|"+transaction.getTransactionAmount();
		return data;
	}
	public String prepareBlock(Block block)
	{
	
		String data="";
		data+="Block"+"|";
		data+=block.getHash()+"|"+block.getPreviousHash()+"|"+block.getTimeStamp()+"|"+block.getNonce()+"|"+block.getRoot()+"|"+prepareSubTransaction(block.getBlockTransaction())+"|"+prepareSubTransaction(block.getTree().getTxArrayList());
		return data;
	}
	
	public String prepareSubTransaction(ArrayList<Transication> blockTransaction)
	{
		String temp="";
		for(int i=0;i<blockTransaction.size();i++)
		{
			temp+=blockTransaction.get(i).getFromAddress().toString();
			temp+="*";
			temp+=blockTransaction.get(i).getToAddress().toString();
			temp+="*";
			temp+=blockTransaction.get(i).getTransactionAmount().toString();
			temp+=",";
			
			
		}
		return temp;
	}
}

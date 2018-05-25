package CryptoCurrancy;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BlockChain {
	private ArrayList<Block>blocks;
	private ArrayList<Transication>pendingTransaction;
	private Integer difficulty;
	private Integer miningReward;
	
	 public Double getBalanceOfAddress(String address)
	    {
	        Double balance=0.0;
	        for(int i=0;i<blocks.size();i++)
	        {
	        	Block block=blocks.get(i);
	            for(int j=0;j<block.getBlockTransaction().size();j++)
	            {
	            	Transication transaction=block.getBlockTransaction().get(j);
	            	if(transaction.getFromAddress().equals(address))
	            	{
	            		balance-=Double.parseDouble(transaction.getTransactionAmount());
	            	}
	            	
	            	if(transaction.getToAddress().equals(address))
	            	{
	            		balance+=Double.parseDouble(transaction.getTransactionAmount());
	            	}
	            }
	        }

	        return balance;
	    }
	 
	 public boolean isChainValid() throws NoSuchAlgorithmException
	 {
		 for(int i=1;i<blocks.size();i++)
		 {
			 Block firstBlock=blocks.get(i-1);
			 Block secondBlock=blocks.get(i);
			 
			   if(secondBlock.getHash()!=secondBlock.calculateHash()){return false;}
	            if(secondBlock.getPreviousHash()!=firstBlock.getHash()){return false;}
	            return true;
		 }
		 return false;
	 }
	 public void createGenisisBlock()
	 {
		 Block intitalBlock=new Block();
		
		
	 }
	 public void createTransaction(Transication transaction)
	 {
		pendingTransaction.add(transaction);
	 }
	 
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}
	public ArrayList<Transication> getPendingTransaction() {
		return pendingTransaction;
	}
	public void setPendingTransaction(ArrayList<Transication> pendingTransaction) {
		this.pendingTransaction = pendingTransaction;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	public Integer getMiningReward() {
		return miningReward;
	}
	public void setMiningReward(Integer miningReward) {
		this.miningReward = miningReward;
	}
	


}

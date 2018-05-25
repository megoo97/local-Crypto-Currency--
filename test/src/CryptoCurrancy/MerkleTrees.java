package CryptoCurrancy;

import java.security.MessageDigest;
import java.util.ArrayList;

public class MerkleTrees {

  // transaction ArrayList
  ArrayList<Transication> txArrayList;
  // Merkle Root
  String root;
  
  /**
   * constructor
   * @param txArrayList transaction ArrayList
   */
  public MerkleTrees(ArrayList<Transication> txArrayList) {
    this.txArrayList = txArrayList;
    root = "";
  }
   
  /**
   * execute merkle_tree and set root.
   */
  public void merkle_tree() {
    
    ArrayList<String> tempTxArrayList = new ArrayList<String>();
    
    for (int i = 0; i < this.txArrayList.size(); i++) {
      tempTxArrayList.add(this.txArrayList.get(i).getFromAddress()+this.txArrayList.get(i).getToAddress()+this.txArrayList.get(i).getTransactionAmount());
    }
    
    ArrayList<String> newTxArrayList = getNewTxArrayList(tempTxArrayList);
    while (newTxArrayList.size() != 1) {
      newTxArrayList = getNewTxArrayList(newTxArrayList);
    }
    
    this.root = newTxArrayList.get(0);
  }
  
  public ArrayList<Transication> getTxArrayList() {
	return txArrayList;
}

public void setTxArrayList(ArrayList<Transication> txArrayList) {
	this.txArrayList = txArrayList;
}

public void setRoot(String root) {
	this.root = root;
}

/**
   * return Node Hash ArrayList.
   * @param tempTxArrayList
   * @return
   */
  private ArrayList<String> getNewTxArrayList(ArrayList<String> tempTxList) {
	    
	    ArrayList<String> newTxList = new ArrayList<String>();
	    int index = 0;
	    while (index < tempTxList.size()) {
	      // left
	      String left = tempTxList.get(index);
	      index++;

	      // right
	      String right = "";
	      if (index != tempTxList.size()) {
	        right = tempTxList.get(index);
	      }

	      // sha2 hex value
	      String sha2HexValue = getSHA2HexValue(left + right);
	      newTxList.add(sha2HexValue);
	      index++;
	      
	    }
	    
	    return newTxList;
	  }
  
  /**
   * Return hex string
   * @param str
   * @return
   */
  public String getSHA2HexValue(String str) {
        byte[] cipher_byte;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            cipher_byte = md.digest();
            StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
            for(byte b: cipher_byte) {
              sb.append(String.format("%02x", b&0xff) );
            }
            return sb.toString();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return "";
  }
  
  /**
   * Get Root
   * @return
   */
  public String getRoot() {
    return this.root;
  }
    
}
        
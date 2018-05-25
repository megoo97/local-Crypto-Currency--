package CryptoCurrancy;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;



public class Block {
private String hash;
private String previousHash;
private int nonce;
private String timeStamp;
ArrayList<Transication>blockTransaction=new ArrayList<>();
private String Root;
private MerkleTrees tree;


public String getRoot() {
	return Root;
}
public void setRoot(String root) {
	Root = root;
}
public MerkleTrees getTree() {
	return tree;
}
public void setTree(MerkleTrees tree) {
	this.tree = tree;
}
public Block(String hash, String previousHash, int nonce, String timeStamp, ArrayList<Transication> blockTransaction) {
	super();
	this.hash = hash;
	this.previousHash = previousHash;
	this.nonce = nonce;
	this.timeStamp = timeStamp;
	this.blockTransaction = blockTransaction;
	this.tree = new MerkleTrees(blockTransaction);
	Root = tree.getRoot();

}
public Block()
{
	
}
public ArrayList<Transication> getBlockTransaction() {
	return blockTransaction;
}
public void setBlockTransaction(ArrayList<Transication> blockTransaction) {
	this.blockTransaction = blockTransaction;
}
public String getHash() {
	return hash;
}
public void setHash(String hash) {
	this.hash = hash;
}
public String getPreviousHash() {
	return previousHash;
}
public void setPreviousHash(String previousHash) {
	this.previousHash = previousHash;
}
public int getNonce() {
	return nonce;
}
public void setNonce(int nonce) {
	this.nonce = nonce;
}
public String getTimeStamp() {
	return timeStamp;
}
public void setTimeStamp(String timeStamp) {
	this.timeStamp = timeStamp;
}

private String createZeros(Integer diffculty)
{
String zeros="";
for(int i=0;i<diffculty;i++)
{
zeros+="0";	
}

return zeros;
}


String hashWith256(String textToHash) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] byteOfTextToHash = textToHash.getBytes(StandardCharsets.UTF_8);
    byte[] hashedByetArray = digest.digest(byteOfTextToHash);
    String encoded = Base64.encode(hashedByetArray);
    return encoded;
}


String  calculateHash( ) throws NoSuchAlgorithmException
{
hash=hashWith256(Root+timeStamp+nonce);
return hash;
}

String mineBlock(Integer diffculty) throws NoSuchAlgorithmException
{
	calculateHash();
	while(hash.substring(0,2+1).equals(createZeros(diffculty)))
	{
		nonce++;
		calculateHash();
	}
	
	return hash;
}



}

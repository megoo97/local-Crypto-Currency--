package CryptoCurrancy;

public class Transication {
private String fromAddress;
private String toAddress;
private String transactionAmount;
public String getFromAddress() {
	return fromAddress;
}
public void setFromAddress(String fromAddress) {
	this.fromAddress = fromAddress;
}
public String getToAddress() {
	return toAddress;
}
public void setToAddress(String toAddress) {
	this.toAddress = toAddress;
}
public String getTransactionAmount() {
	return transactionAmount;
}
public void setTransactionAmount(String transactionAmount) {
	this.transactionAmount = transactionAmount;
}
public Transication(String fromAddress, String toAddress, String transactionAmount) {
	super();
	this.fromAddress = fromAddress;
	this.toAddress = toAddress;
	this.transactionAmount = transactionAmount;
}

}

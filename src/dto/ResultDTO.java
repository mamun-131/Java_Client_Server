package dto;

import java.io.Serializable;

public class ResultDTO implements Serializable{

	private static final long SERIAL_VERSION_UID = 3341215555710225029L;
	
	private String mMessage;
	private double mBalance;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return mMessage;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		mMessage = message;
	}
	/**
	 * @return the balance
	 */
	public double getBalance() {
		return mBalance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		mBalance = balance;
	}	
}
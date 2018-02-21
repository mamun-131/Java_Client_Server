package dto;

import java.io.Serializable;

// Data transfer object
public class AccountDTO implements Serializable {
	
	private static final long SERIAL_VERSION_UID = 8296077336599202180L;
	
	private int mAccountNumnber;
	private int mAccountPin;
	private double mAmount;
	private String mOperation;
	
	/**
	 * Constructor
	 */
	public AccountDTO(int accountNumber, int accountPin, double amount) {
		mAccountNumnber = accountNumber;
		mAccountPin = accountPin;
		mAmount = amount;
	}
	/**
	 * @return the account number
	 */
	public int getAccountNumber() {
		return mAccountNumnber;
	}
	/**
	 * @param accountNumber the account number to set
	 */
	public void setAccountNumber(int accountNumber) {
		mAccountNumnber = accountNumber;
	}
	/**
	 * @return the account pin
	 */
	public int getAccountPin() {
		return mAccountPin;
	}
	/**
	 * @param accountPin the account pin to set
	 */
	public void setmAccountPin(int accountPin) {
		mAccountPin = accountPin;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return mAmount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		mAmount = amount;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return mOperation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		mOperation = operation;
	}
}
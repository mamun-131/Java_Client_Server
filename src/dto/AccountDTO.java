package dto;

import java.io.Serializable;

// Data transfer object
public class AccountDTO implements Serializable {
	
	private static final long serialVersionUID = 8296077336599202180L;
	int accNumber;
	int accPin;
	double amount;
	String operation;
	
	public AccountDTO(int accNumber, int accPin, double amount) {
		this.accNumber = accNumber;
		this.accPin = accPin;
		this.amount = amount;
	}
	/**
	 * @return the accNumber
	 */
	public int getAccNumber() {
		return accNumber;
	}
	/**
	 * @param accNumber the accNumber to set
	 */
	public void setAccNumber(int accNumber) {
		this.accNumber = accNumber;
	}
	/**
	 * @return the accPin
	 */
	public int getAccPin() {
		return accPin;
	}
	/**
	 * @param accPin the accPin to set
	 */
	public void setAccPin(int accPin) {
		this.accPin = accPin;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
}

package atmserver;

import atmserver.Transaction;

public class Account extends Transaction {

	private int mPin;
	
	/**
	 * Constructor
	 */
	public Account(int accountNumber, int pin, double totalBalance) {
		super(accountNumber, totalBalance);
		mPin = pin;
	}

	/**
	 * @param amount determines whether a user-specified PIN matches PIN in the Account
	 */
	public boolean validatePIN(int pin) {
	    if (pin == mPin) {
			return true;
		}
	    
		return false;
	} 
}
package atmserver;

public abstract class Transaction implements ITransaction {
	
	private int mAccountNumber; 
	private double mTotalBalance;

	/**
	 * Constructor
	 */
	public Transaction(int accountNumber, double totalBalance) {
		mAccountNumber = accountNumber;
		mTotalBalance = totalBalance;
	}
	
	@Override
	/**
	 * @return the total balance
	 */
	public double getTotalBalance() {
	    return mTotalBalance;
	} 

	@Override
	/**
	 * @param amount deposits an amount to the account
	 */
	public void deposit(double amount) {
	    mTotalBalance += amount; 
	} 
	
	@Override
	/**
	 * @param amount withdraws an amount from the account
	 */
	public void withdraw(double amount) {
		mTotalBalance -= amount; 
	} 

	@Override
	/**
	 * @return the account number
	 */
	public int getAccountNumber() {
	    return mAccountNumber;  
	} 
}
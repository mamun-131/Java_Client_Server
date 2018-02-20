package atmserver;

public abstract class Transection implements ITransection {
	public int accountNumber; // account number
	public double totalBalance; // funds available
	
	@Override
	 // returns the total balance
	   public double getTotalBalance()
	   {
	      return totalBalance;
	   } // end method getTotalBalance

	@Override
	 // deposits an amount to the account
	   public void deposit(double amount)
	   {
	      totalBalance += amount; // add to total balance
	   } // end method deposit
	@Override
	// withdraws an amount from the account
	   public void withdraw(double amount)
	   {
	      totalBalance -= amount; // subtract from total balance
	   } // end method withdraw


	@Override
	 // returns account number
	   public int getAccountNumber()
	   {
	      return accountNumber;  
	   } // end method getAccountNumber

}

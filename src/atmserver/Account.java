package atmserver;

import atmserver.Transection;

public class Account extends Transection {

	   private int pin; // PIN for authentication
	
	   // Account constructor initializes attributes
	   public Account(int theAccountNumber, int thePIN, double theTotalBalance)
	   {
	      this.accountNumber = theAccountNumber;
	      this.pin = thePIN;
	      this.totalBalance = theTotalBalance;
	   } // end Account constructor

	   // determines whether a user-specified PIN matches PIN in Account
	   public boolean validatePIN(int userPIN)
	   {
	      if (userPIN == pin)
	         return true;
	      else
	         return false;
	   } // end method validatePIN



}

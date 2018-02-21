package atmserver;

import java.util.ArrayList;

public class BankDatabase
{
      private ArrayList<Account> accounts; // array of Accounts

      /**
	 * Constructor
	 */
      public BankDatabase() {
            accounts = new ArrayList<Account>();
            accounts.add(new Account(12345, 54321, 1200.0));
            accounts.add(new Account(23456, 65432, 800.0));
            accounts.add(new Account(34567, 76543, 700.0));
            accounts.add(new Account(45678, 87654, 600.0));
            accounts.add(new Account(56789, 98765, 500.0));
            accounts.add(new Account(98765, 56789, 200.0));  
      } 
   
      /**
	 * Retrieves Account object containing specified account number  
	 */
      private Account getAccount(int accountNumber) {           
            for (Account currentAccount : accounts) {
                  if (currentAccount.getAccountNumber() == accountNumber) {
                        return currentAccount;
                  }
            } 

            return null; // If no matching account is found, returns null
      } 

      /**
       * Determines whether the user-specified account number and PIN match
       * those of an account in the database
       */
      public boolean authenticateUser(int userAccountNumber, int userPIN) {
            Account userAccount = getAccount(userAccountNumber);
            if (userAccount != null) {
                  return userAccount.validatePIN(userPIN);
            }
            
            return false; // account number not found, so return false
      } 

      /**
       * Returns the total balance of the account with the specified account number
       */
      public double getTotalBalance(int userAccountNumber) {
            Account userAccount = getAccount(userAccountNumber);
            if (userAccount != null) {
                  return userAccount.getTotalBalance();
            }

            return -1.0f;
      } 

      /**
       * Deposits an amount to the account with the specified account number
       */
      public void deposit(int userAccountNumber, double amount) {
            Account userAccount = getAccount(userAccountNumber);
            if (userAccount != null) {
                  userAccount.deposit(amount);
            }
      } 

      /**
       * Withdraws an amount from the with the specified account number
       */
      public void withdraw(int userAccountNumber, double amount) {
            Account userAccount = getAccount(userAccountNumber);
            if (userAccount != null) {
                  userAccount.withdraw(amount);
            }
      }
}
package atmserver;

import java.util.ArrayList;

// BankDatabase.java
// Represents the bank account information database 

public class BankDatabase
{
   private ArrayList<Account> accounts; // array of Accounts
   
   // no-argument BankDatabase constructor initializes accounts
   public BankDatabase()
   {
	  accounts = new ArrayList<Account>();
      accounts.add(new Account(12345, 54321, 1200.0));
      accounts.add(new Account(23456, 65432, 800.0));
      accounts.add(new Account(34567, 76543, 700.0));
      accounts.add(new Account(45678, 87654, 600.0));
      accounts.add(new Account(56789, 98765, 500.0));
      accounts.add(new Account(98765, 56789, 200.0));  
   } // end no-argument BankDatabase constructor
   
   // retrieve Account object containing specified account number
   private Account getAccount(int accountNumber)
   {
      // loop through accounts searching for matching account number
      for (Account currentAccount : accounts)
      {
         // return current account if match found
         if (currentAccount.getAccountNumber() == accountNumber)
            return currentAccount;
      } // end for

      return null; // if no matching account was found, return null
   } // end method getAccount

   // determine whether user-specified account number and PIN match
   // those of an account in the database
   public boolean authenticateUser(int userAccountNumber, int userPIN)
   {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount(userAccountNumber);

      // if account exists, return result of Account method validatePIN
      if (userAccount != null)
         return userAccount.validatePIN(userPIN);
      else
         return false; // account number not found, so return false
   } // end method authenticateUser

   // return total balance of Account with specified account number
   public double getTotalBalance(int userAccountNumber)
   {
      return getAccount(userAccountNumber).getTotalBalance();
   } // end method getTotalBalance

   // deposit an amount to Account with specified account number
   public void deposit(int userAccountNumber, double amount)
   {
      getAccount(userAccountNumber).deposit(amount);
   } // end method deposit

   // withdraws an amount from Account with specified account number
   public void withdraw(int userAccountNumber, double amount)
   {
      getAccount(userAccountNumber).withdraw(amount);
   } // end method withdraw
} // end class BankDatabase

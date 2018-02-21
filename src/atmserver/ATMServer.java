// ATMServer.java
// A server that uses multithreading to handle any number of clients.
package atmserver;

import java.io.*;
import java.net.*;
import dto.*;

class ServerOne extends Thread {
	private Socket mSocket;
	private ObjectInputStream mObjectInputStream;
	private ObjectOutputStream mObjectOutputStream;
	private BankDatabase mBankDataBase;

	/**
	 * Constructor
	 */
	public ServerOne(Socket s, BankDatabase db) throws IOException {
		mSocket = s;
		mBankDataBase = db;
		mObjectInputStream = new ObjectInputStream(mSocket.getInputStream());
		mObjectOutputStream = new ObjectOutputStream(mSocket.getOutputStream());
		start(); // Calls run()
	}

	public void run() {
		try {
			AccountDTO accountDTO = (AccountDTO) mObjectInputStream.readObject();
			ResultDTO resultTDO = new ResultDTO();

			if (mBankDataBase.authenticateUser(accountDTO.getAccountNumber(), accountDTO.getAccountPin())) {
				final int accountNumber = accountDTO.getAccountNumber();
				final double amount = accountDTO.getAmount();

				if (accountDTO.getOperation().toUpperCase().equals("DEPOSIT")) {
					mBankDataBase.deposit(accountNumber, amount);
				} else if (accountDTO.getOperation().toUpperCase().equals("WITHDRAW")) {
					// Checks first if withdrawal will place the balance below zero					
					double totalBalance = mBankDataBase.getTotalBalance(accountNumber);
					if (totalBalance - amount < 0.00) {
						resultTDO.setMessage("Not enough funds in the account");					
					} else {	// Allows the withdrawal
						mBankDataBase.withdraw(accountNumber, amount);
					}
				} else if (accountDTO.getOperation().toUpperCase().equals("BALANCE")) {
					// Nothing
				}
				resultTDO.setBalance(mBankDataBase.getTotalBalance(accountNumber));			
			} else { // account or associated pin not valid
				resultTDO.setMessage("Account number or pin not valid");
			}
			mObjectOutputStream.writeObject(resultTDO);
			mObjectOutputStream.flush();
		} catch (IOException | ClassNotFoundException e) {
			// Nothing
		} finally {
			try {
				mObjectOutputStream.close();
				mObjectInputStream.close();
				mSocket.close();
			} catch (IOException e) {
			}
		} // finally
	}
}

public class ATMServer {
	private static final int PORT = 8080;
	private static BankDatabase sBankDb = new BankDatabase(); 

	public static void main(String[] args) throws IOException {
		ServerSocket severSocket = new ServerSocket(PORT);
		
		System.out.println("Server Started");
		
		try {
			while (true) {
				// Blocks until a connection occurs:
				Socket socket = severSocket.accept();
				try {
					new ServerOne(socket, sBankDb);
				} catch (IOException e) {
					System.out.println("failed");
					// If it fails, close the socket,
					// otherwise the thread will close it:
					socket.close();
				} // catch (IOException e)
			} // while (true)
		} finally {
			severSocket.close();
		}
	}
}
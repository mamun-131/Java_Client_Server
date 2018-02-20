// ATMServer.java
// A server that uses multithreading to handle
// any number of clients.

package atmserver;

import java.io.*;
import java.net.*;
import dto.*;

class ServerOne extends Thread {
	private Socket socket;
	private ObjectInputStream oInStream;
	private ObjectOutputStream oOutStream;
	private BankDatabase bankDatabase;

	// Constructor
	public ServerOne(Socket s, BankDatabase db) throws IOException {
		socket = s;
		bankDatabase = db;
		oInStream = new ObjectInputStream(socket.getInputStream());
		oOutStream = new ObjectOutputStream(socket.getOutputStream());
		start(); // Calls run()
	}

	public void run() {
		try {
			AccountDTO oAccountDTO = (AccountDTO) oInStream.readObject();
			ResultDTO oResultDTO = new ResultDTO();

			if (bankDatabase.authenticateUser(oAccountDTO.getAccNumber(), oAccountDTO.getAccPin())) {
			// account and associated pin valid
				if (oAccountDTO.getOperation().toUpperCase().equals("DEPOSIT")) {
					bankDatabase.deposit(oAccountDTO.getAccNumber(), oAccountDTO.getAmount());
				} else if (oAccountDTO.getOperation().toUpperCase().equals("WITHDRAW")) {
					// check first if withdrawal will place the balance below zero
					double balance = bankDatabase.getTotalBalance(oAccountDTO.getAccNumber());
					if (balance - oAccountDTO.getAmount() < 0.00) {
						oResultDTO.setMessage("Not enough funds in the account");
					// allow the withdrawal
					} else {
						bankDatabase.withdraw(oAccountDTO.getAccNumber(), oAccountDTO.getAmount());
					}
				} else if (oAccountDTO.getOperation().toUpperCase().equals("BALANCE")) {
					// do nothing. This is just for readability
				}
				oResultDTO.setBalance(bankDatabase.getTotalBalance(oAccountDTO.getAccNumber()));
			// account or associated pin not valid
			} else {
				oResultDTO.setMessage("Account number or pin not valid");
			}
			oOutStream.writeObject(oResultDTO);
			oOutStream.flush();
		} catch (IOException | ClassNotFoundException e) {
		} finally {
			try {
				oOutStream.close();
				oInStream.close();
				socket.close();
			} catch (IOException e) {
			}
		}
	}
}

public class ATMServer {
	static final int PORT = 8080;
	static BankDatabase bankDb = new BankDatabase(); // create instance of the
														// bank database

	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server Started");
		try {
			while (true) {
				// Blocks until a connection occurs:
				Socket socket = s.accept();
				try {
					new ServerOne(socket, bankDb);
				} catch (IOException e) {
					System.out.println("failed");
					// If it fails, close the socket,
					// otherwise the thread will close it:
					socket.close();
				}
			}
		} finally {
			s.close();
		}
	}
}
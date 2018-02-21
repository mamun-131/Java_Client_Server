package atmserver;

public interface ITransaction {
	
	int getAccountNumber();
	double getTotalBalance();
	void deposit(double amount);
	void withdraw(double amount);
}
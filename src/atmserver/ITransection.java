package atmserver;

public interface ITransection {
	

	double getTotalBalance();
	void deposit(double amount);
	void withdraw(double amount);
	int getAccountNumber();
	
}
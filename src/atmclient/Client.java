package atmclient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import dto.AccountDTO;
import dto.ResultDTO;

import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JEditorPane;
import java.awt.Font;
import javax.swing.JPasswordField;

public class Client {

	private JFrame mFrame;
	private JTextField mUserNameTF;
	private JTextField mAmountTF;
	private JPasswordField mPINTF;
	private JLabel mShowBlanceLabel;
	private JLabel mErrorMessageLabel;
	private	JLabel mLoginStatusLabel; 
	
	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.mFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mFrame = new JFrame();
		mFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
		mFrame.setBounds(100, 100, 450, 300);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
			FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("default:grow"),
			FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}
		));
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runTransaction("Balance");
			}
		});
		mFrame.getContentPane().add(loginBtn, "2, 12, left, default");
		
		JLabel atmSystemLabel = new JLabel("ATM SYSTEM");
		atmSystemLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		atmSystemLabel.setForeground(Color.BLUE);
		mFrame.getContentPane().add(atmSystemLabel, "2, 2");
		
		JLabel balanceLabel = new JLabel("Balance");
		mFrame.getContentPane().add(balanceLabel, "6, 2");
		
		JLabel userNameLabel = new JLabel("User Name");
		mFrame.getContentPane().add(userNameLabel, "2, 4");
		
		mUserNameTF = new JTextField();
		mFrame.getContentPane().add(mUserNameTF, "2, 6, fill, default");
		mUserNameTF.setColumns(10);
		
		mShowBlanceLabel = new JLabel("");
		mShowBlanceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		mShowBlanceLabel.setBackground(new Color(72, 61, 139));
		mShowBlanceLabel.setForeground(new Color(255, 192, 203));
		mShowBlanceLabel.setOpaque(true);
		mFrame.getContentPane().add(mShowBlanceLabel, "6, 3, 1, 7, fill, default");
		
		JLabel pinLabel = new JLabel("Passward");
		mFrame.getContentPane().add(pinLabel, "2, 8");
		
		mPINTF = new JPasswordField();
		mFrame.getContentPane().add(mPINTF, "2, 10, fill, default");
		
		mErrorMessageLabel = new JLabel("");
		mErrorMessageLabel.setForeground(Color.RED);
		mFrame.getContentPane().add(mErrorMessageLabel, "6, 10");
		
		JLabel depositWithrawLabel = new JLabel("Insert the AMOUNT to deposit or withdraw below");
		depositWithrawLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		depositWithrawLabel.setForeground(Color.BLUE);
		mFrame.getContentPane().add(depositWithrawLabel, "6, 12");
		
	    mLoginStatusLabel = new JLabel("");
		mFrame.getContentPane().add(mLoginStatusLabel, "2, 14");
		
		mAmountTF = new JTextField();
		mFrame.getContentPane().add(mAmountTF, "6, 14, fill, default");
		mAmountTF.setColumns(10);
		
		JButton depoistBtn = new JButton("Deposit");
		depoistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runTransaction(depoistBtn.getText());
			}
		});
		
		JButton balanceBtn = new JButton("Balance");
		balanceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runTransaction(balanceBtn.getText());
			}
		});
		mFrame.getContentPane().add(balanceBtn, "2, 16");
		mFrame.getContentPane().add(depoistBtn, "6, 16, center, default");
		
		JButton withdrawBtn = new JButton("Withdraw");
		withdrawBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runTransaction(withdrawBtn.getText());
			}
		});
		mFrame.getContentPane().add(withdrawBtn, "6, 18, center, default");
	}
	
	
	/**
	 * Validate fields
	 */
	public Boolean validateFields() {
		try{
			Integer.parseInt(mUserNameTF.getText());
			Integer.parseInt(mPINTF.getText());
			if (mAmountTF.getText().trim().equals("")) {
				mAmountTF.setText("0.00");
			}
			Double.parseDouble(mAmountTF.getText());
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}
	
	
	public void runTransaction(String action) {

		// validate fields first
		Boolean bIsValid = validateFields();
		
		if (!bIsValid) {
			mErrorMessageLabel.setText("Error");
			mShowBlanceLabel.setText("<html>Login first or <br>Please verify that fields are correct");
			return; // exit the method
		}
		else {
			mLoginStatusLabel.setText("You are Logged In");		
		}
		
		// Guard everything in a try-finally to make
		// sure that the socket is closed:
		try {
			InetAddress inetAddress = InetAddress.getByName(null);
			Socket socket = new Socket(inetAddress, 8080);
			ObjectOutputStream objectOutStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

			AccountDTO accountDTO = new AccountDTO(Integer.parseInt(mUserNameTF.getText()),
			Integer.parseInt(mPINTF.getText()), Double.parseDouble(mAmountTF.getText()));
			accountDTO.setOperation(action);
			objectOutStream.writeObject(accountDTO);
			objectOutStream.flush();
			ResultDTO oResult = (ResultDTO) objectInputStream.readObject();

			if (oResult.getMessage() != null) {
				mErrorMessageLabel.setText("Error");
				mShowBlanceLabel.setText(oResult.getMessage());
			} else {
				mErrorMessageLabel.setText("Balance");
				mShowBlanceLabel.setText(String.format("$%,.2f", oResult.getBalance()));
			}

			objectOutStream.close();
			objectInputStream.close();
			socket.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}

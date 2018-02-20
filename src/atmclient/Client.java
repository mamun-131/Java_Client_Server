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

	JFrame frame;
	private JTextField txtUserName;
	private JTextField txtAmount;
	private JPasswordField txtPIN;
	private JLabel lblShowBalance;
	private JLabel lblErrorMsg;
	private	JLabel lblLogInStatus; 
	/**
	 * Launch the application........
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
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
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnLogIn = new JButton("Login");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runTransaction("Balance");
			}
		});
		
		JLabel lblAtmSystem = new JLabel("ATM SYSTEM");
		lblAtmSystem.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		lblAtmSystem.setForeground(Color.BLUE);
		frame.getContentPane().add(lblAtmSystem, "2, 2");
		
		JLabel lblBalance = new JLabel("Balance");
		frame.getContentPane().add(lblBalance, "6, 2");
		
		JLabel lblUserName = new JLabel("User Name");
		frame.getContentPane().add(lblUserName, "2, 4");
		
		txtUserName = new JTextField();
		frame.getContentPane().add(txtUserName, "2, 6, fill, default");
		txtUserName.setColumns(10);
		
		lblShowBalance = new JLabel("");
		lblShowBalance.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblShowBalance.setBackground(new Color(72, 61, 139));
		lblShowBalance.setForeground(new Color(255, 192, 203));
		lblShowBalance.setOpaque(true);
		frame.getContentPane().add(lblShowBalance, "6, 3, 1, 7, fill, default");
		
		JLabel lblPIN = new JLabel("Passward");
		frame.getContentPane().add(lblPIN, "2, 8");
		
		txtPIN = new JPasswordField();
		frame.getContentPane().add(txtPIN, "2, 10, fill, default");
		
		lblErrorMsg = new JLabel("");
		lblErrorMsg.setForeground(Color.RED);
		frame.getContentPane().add(lblErrorMsg, "6, 10");
		frame.getContentPane().add(btnLogIn, "2, 12, left, default");
		
		JLabel lblDepoWith = new JLabel("Insert the AMOUNT to deposit or withdraw below");
		lblDepoWith.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblDepoWith.setForeground(Color.BLUE);
		frame.getContentPane().add(lblDepoWith, "6, 12");
		
	    lblLogInStatus = new JLabel("");
		frame.getContentPane().add(lblLogInStatus, "2, 14");
		
		txtAmount = new JTextField();
		frame.getContentPane().add(txtAmount, "6, 14, fill, default");
		txtAmount.setColumns(10);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runTransaction(btnDeposit.getText());
			}
		});
		
		JButton btnBalance = new JButton("Balance");
		btnBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runTransaction(btnBalance.getText());
			}
		});
		frame.getContentPane().add(btnBalance, "2, 16");
		frame.getContentPane().add(btnDeposit, "6, 16, center, default");
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runTransaction(btnWithdraw.getText());
			}
		});

		frame.getContentPane().add(btnWithdraw, "6, 18, center, default");
	}
	
	
	
	
	/**
	 * Validate fields
	 */
	public Boolean validateFields(){
		try{
			Integer.parseInt(txtUserName.getText());
			Integer.parseInt(txtPIN.getText());
			if (txtAmount.getText().trim().equals("")) {
				txtAmount.setText("0.00");
			}
			Double.parseDouble(txtAmount.getText());
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}
	
	
	
	public void runTransaction(String action) {

		// validate fields first
		Boolean areValid = validateFields();
		
		if (!areValid) {
			lblErrorMsg.setText("Error");
			lblShowBalance.setText("<html>Login first or <br>Please verify that fields are correct");
			return; // exit the method
		}
		else
		{
			lblLogInStatus.setText("You are Logged In");
			
		}
		
		// Guard everything in a try-finally to make
		// sure that the socket is closed:
		try {
			InetAddress addr = InetAddress.getByName(null);
			Socket socket = new Socket(addr, 8080);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			AccountDTO oAccDTO = new AccountDTO(Integer.parseInt(txtUserName.getText()),
					Integer.parseInt(txtPIN.getText()), Double.parseDouble(txtAmount.getText()));
			oAccDTO.setOperation(action);
			out.writeObject(oAccDTO);
			out.flush();
			ResultDTO oResult = (ResultDTO) in.readObject();

			if (oResult.getMessage() != null) {
				lblErrorMsg.setText("Error");
				lblShowBalance.setText(oResult.getMessage());
			} else {
				lblErrorMsg.setText("Balance");
				lblShowBalance.setText(String.format("$%,.2f", oResult.getBalance()));
			}

			out.close();
			in.close();
			socket.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}

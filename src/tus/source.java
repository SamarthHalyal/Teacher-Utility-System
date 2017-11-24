package tus;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class source extends JFrame {

	private JFrame frame;
	private JTextField tname;
	private JTextField email;
	private JPasswordField pw;
	private JTextField mail;
	private JPasswordField password;
	private Connection conn;
	private JPasswordField adminpass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() { 
				try {
					source window = new source();
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
	public source() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 432, 423);
		frame.getContentPane().add(tabbedPane);

		Register registerButton = new Register();
		registerButton.setBackground(Color.DARK_GRAY);
		tabbedPane.add("Register", registerButton);
		registerButton.setLayout(null);

		JLabel lblName = new JLabel("Name :");
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(94, 13, 56, 16);
		registerButton.add(lblName);

		tname = new JTextField();
		tname.setBounds(146, 10, 199, 22);
		registerButton.add(tname);
		tname.setColumns(10);

		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setBounds(94, 45, 56, 16);
		registerButton.add(lblEmail);

		email = new JTextField();
		email.setBounds(146, 42, 199, 22);
		registerButton.add(email);
		email.setColumns(10);

		JLabel lblDepartment = new JLabel("Department :");
		lblDepartment.setForeground(Color.WHITE);
		lblDepartment.setBounds(57, 80, 76, 16);
		registerButton.add(lblDepartment);

		JComboBox did = new JComboBox();
		did.setBounds(146, 77, 199, 22);
		registerButton.add(did);
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery("select * from dept;");

			int i = 0;
			while (set.next()) {
				did.addItem(set.getString("dname"));
			}
			conn.close();
		} catch (Exception e) {
			System.out.println("Error Occured 1");
		}

		JLabel lblSemester = new JLabel("Semester :");
		lblSemester.setForeground(Color.WHITE);
		lblSemester.setBounds(67, 115, 66, 16);
		registerButton.add(lblSemester);

		JComboBox semid = new JComboBox();
		semid.setBounds(146, 112, 66, 22);
		registerButton.add(semid);
		semid.addItem("5");
		semid.addItem("6");

		JLabel lblSubject = new JLabel("Subject :");
		lblSubject.setForeground(Color.WHITE);
		lblSubject.setBounds(77, 151, 56, 16);
		registerButton.add(lblSubject);

		JComboBox sid = new JComboBox();
		sid.setBounds(146, 148, 156, 22);
		registerButton.add(sid);
		// if(did.){

		// }

		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(67, 186, 66, 16);
		registerButton.add(lblPassword);

		pw = new JPasswordField();
		pw.setBounds(146, 183, 199, 22);
		registerButton.add(pw);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String teachername = tname.getText().toString();
				String em = email.getText().toString();
				String depname = did.getSelectedItem().toString();
				String sem = semid.getSelectedItem().toString();
				String subname = sid.getSelectedItem().toString();
				String pass = pw.getText().toString();

				try {
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
					Statement statement = conn.createStatement();
					ResultSet set = statement.executeQuery("select did from dept where dname=\"" + depname + "\";");
					int d1 = 0;
					while (set.next()) {
						d1 = set.getInt("did");
					}

					set = statement.executeQuery("select semid from semester where sname=\"" + subname + "\";");
					int d2 = 0;
					while (set.next()) {
						d2 = set.getInt("semid");
					}

					statement.executeUpdate("insert into register(tname,email,did,semid,sname,pw,sem) values(\""
							+ teachername + "\",\"" + em + "\"," + d1 + "," + d2 + ",\"" + subname + "\",\"" + pass
							+ "\",\"" + sem + "\");");
					JOptionPane.showMessageDialog(null, "Registration Successfull", "",
							JOptionPane.INFORMATION_MESSAGE);
					statement.executeUpdate("insert into teacher(email,pass) values (\"" + em + "\",\"" + pass + "\")");
					// statement.executeUpdate("update teacher set tid="++" &&
					// email=\""+em+"\" && pass=\""+pass+"\";");

					String did = null, semid = null, sid = null, sname = null, tname = null;
					set = statement.executeQuery("select tname,did,semid,sname from register;");
					while (set.next()) {
						did = set.getString("did");
						semid = set.getString("semid");
						sname = set.getString("sname");
						tname = set.getString("tname");
					}
					set = statement.executeQuery("select sid,sname from subjects;");
					while (set.next()) {
						if (sname.contains(set.getString("sname"))) {
							sid = set.getString("sid");
						}
					}
					statement.executeUpdate("insert into updates(did,semid,sid,tname,sname,mod_rem,time_rem) values("
							+ did + "," + semid + "," + sid + ",\"" + tname + "\",\"" + sname + "\"," + 5 + "," + 50
							+ ");");
					conn.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Registration Un-Successfull!!", "",
							JOptionPane.INFORMATION_MESSAGE);
					System.out.println(e.getMessage());
				}
			}
		});
		btnRegister.setBounds(146, 309, 97, 25);
		registerButton.add(btnRegister);

		JSeparator separator = new JSeparator();
		separator.setBounds(35, 259, 359, 2);
		registerButton.add(separator);

		JButton btnSetSubjects = new JButton("Set Subjects");
		btnSetSubjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sid.removeAllItems();
				//////
				if (did.getSelectedItem().equals("Computer Science") && semid.getSelectedItem().equals("5")) {
					sid.addItem("5CS1");
					sid.addItem("5CS2");
				}
				if (did.getSelectedItem().equals("Computer Science") && semid.getSelectedItem().equals("6")) {
					sid.addItem("6CS1");
					sid.addItem("6CS2");
				}
				if (did.getSelectedItem().equals("Mechanical") && semid.getSelectedItem().equals("5")) {
					sid.addItem("5ME1");
					sid.addItem("5ME2");
				}
				if (did.getSelectedItem().equals("Mechanical") && semid.getSelectedItem().equals("6")) {
					sid.addItem("6ME1");
					sid.addItem("6ME2");
				}
				if (did.getSelectedItem().equals("Civil") && semid.getSelectedItem().equals("5")) {
					sid.addItem("5CV1");
					sid.addItem("5CV2");
				}
				if (did.getSelectedItem().equals("Civil") && semid.getSelectedItem().equals("6")) {
					sid.addItem("6CV1");
					sid.addItem("6CV2");
				}
			}
		});
		btnSetSubjects.setBounds(224, 112, 121, 25);
		registerButton.add(btnSetSubjects);
		Login login = new Login();
		login.setBackground(Color.DARK_GRAY);
		tabbedPane.add("Login", login);
		login.setLayout(null);

		JLabel lblEmail_1 = new JLabel("Email :");
		lblEmail_1.setForeground(Color.WHITE);
		lblEmail_1.setBounds(99, 111, 56, 16);
		login.add(lblEmail_1);

		mail = new JTextField();
		mail.setBounds(151, 108, 196, 22);
		login.add(mail);
		mail.setColumns(10);

		JLabel lblPassword_1 = new JLabel("Password :");
		lblPassword_1.setForeground(Color.WHITE);
		lblPassword_1.setBounds(76, 153, 72, 16);
		login.add(lblPassword_1);

		password = new JPasswordField();
		password.setBounds(151, 150, 196, 22);
		login.add(password);

		JButton Loginbutton = new JButton("Login");
		Loginbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!adminpass.isEditable()){
					String m = mail.getText().toString();
					String p = password.getText().toString();
					boolean flag = false;
					try {
						conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
						Statement statement = conn.createStatement();
						ResultSet set = statement.executeQuery("select * from teacher;");
						while (set.next()) {
							if (m.contains(set.getString("email")) && p.contains(set.getString("pass"))) {
								flag = true;
								// JOptionPane.showMessageDialog(null, "Login
								// Successful", "",
								// JOptionPane.INFORMATION_MESSAGE);
								String did, tname = null, department = null, sem = null, subject = null;
								try {
									set = statement.executeQuery("select tname,email,did,sname,sem from register;");
									while (set.next()) {
										if (m.contains(set.getString("email"))) {
											tname = set.getString("tname");
											subject = set.getString("sname");
											sem = set.getString("sem");
											did = set.getString("did");
											set = statement.executeQuery("select * from dept;");
											while (set.next()) {
												if (did.contains(set.getString("did"))) {
													department = set.getString("dname");
													break;
												}
											}
											break;
										}
									}
								} catch (Exception e) {
								}
								tus.Page1 page1 = new tus.Page1(tname, department, subject, sem);
								setVisible(false);
								frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
								frame.dispose();
								page1.setVisible(true);
							}
						}
						if(!flag){
							JOptionPane.showMessageDialog(null, "Login Un-Successfull!!", "",
									JOptionPane.INFORMATION_MESSAGE);
						}
						conn.close();
					} catch (Exception e) {
	//					JOptionPane.showMessageDialog(null, "Login Un-Successfull!!", "",
	//							JOptionPane.INFORMATION_MESSAGE);
						System.out.println(e.getMessage());
					}
	
				}else{
					if(adminpass.getText().toString().contains("iamadmin")){
						Page3 p3 = new Page3();
						p3.setVisible(true);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Admin Passcode Wrong!");
					}
				}
		}
		});
		Loginbutton.setBounds(164, 270, 77, 25);
		login.add(Loginbutton);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(23, 237, 372, 2);
		login.add(separator_1);
		
		JButton btnAdminLogin = new JButton("Admin Login");
		btnAdminLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mail.setEditable(false);
				password.setEditable(false);
				adminpass.setEditable(true);
			}
		});
		btnAdminLogin.setBounds(231, 41, 121, 23);
		login.add(btnAdminLogin);
		
		JLabel lblAdminPasscode = new JLabel("Admin passcode :");
		lblAdminPasscode.setForeground(Color.WHITE);
		lblAdminPasscode.setBounds(43, 197, 101, 14);
		login.add(lblAdminPasscode);
		
		JButton btnNewButton = new JButton("Faculty Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adminpass.setEditable(false);
				mail.setEditable(true);
				password.setEditable(true);
			}
		});
		btnNewButton.setBounds(55, 41, 130, 23);
		login.add(btnNewButton);
		
		adminpass = new JPasswordField();
		adminpass.setBounds(151, 194, 167, 20);
		login.add(adminpass);
	}
}

class Login extends JPanel {
}

class Register extends JPanel {
}
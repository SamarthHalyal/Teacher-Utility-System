package tus;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Page2 extends JFrame {

	private JPanel contentPane;
	private JLabel name;
	private static Page2 frame;
	private static String nam;
	private JLabel lblSubject;
	private JLabel subject;
	private JLabel lblModulesRemaining;
	private JLabel lblTimeRemaining;
	private JLabel modules;
	private JLabel time;
	private Connection conn;
	private JButton btnBack;
	private JButton btnExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Page2(nam);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Page2(String n) {
		nam = n;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		name = new JLabel("Name");
		name.setForeground(Color.WHITE);
		name.setBounds(175, 13, 163, 16);
		contentPane.add(name);
		name.setText(n);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 46, 408, 2);
		contentPane.add(separator);
		
		lblSubject = new JLabel("Subject :");
		lblSubject.setForeground(Color.WHITE);
		lblSubject.setBounds(128, 78, 74, 16);
		contentPane.add(lblSubject);
		
		subject = new JLabel("subject");
		subject.setForeground(Color.WHITE);
		subject.setBounds(227, 78, 56, 16);
		contentPane.add(subject);
		
		lblModulesRemaining = new JLabel("Modules Remaining :");
		lblModulesRemaining.setForeground(Color.WHITE);
		lblModulesRemaining.setBounds(60, 129, 126, 16);
		contentPane.add(lblModulesRemaining);
		
		lblTimeRemaining = new JLabel("Time Remaining :");
		lblTimeRemaining.setForeground(Color.WHITE);
		lblTimeRemaining.setBounds(80, 177, 111, 16);
		contentPane.add(lblTimeRemaining);
		
		modules = new JLabel("modules");
		modules.setForeground(Color.WHITE);
		modules.setBounds(227, 129, 56, 16);
		contentPane.add(modules);
		
		time = new JLabel("time");
		time.setForeground(Color.WHITE);
		time.setBounds(227, 177, 56, 16);
		contentPane.add(time);
		
		btnBack = new JButton("new login");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				source s = new source();
				s.main(null);
			}
		});
		btnBack.setBounds(116, 215, 97, 25);
		contentPane.add(btnBack);
		
		btnExit = new JButton("exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(222, 215, 97, 25);
		contentPane.add(btnExit);
		
		
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery("select tname,sname,mod_rem,time_rem from updates;");
			while(set.next()){
				if(nam.contains(set.getString("tname"))){
					subject.setText(set.getString("sname"));
					modules.setText(set.getString("mod_rem"));
					time.setText(set.getString("time_rem"));
				}
			}
			conn.close();
		}
		catch(Exception e)
		{
			System.out.println("page2 Error");
		}
	}
}
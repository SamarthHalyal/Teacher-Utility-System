package tus;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Page1 extends JFrame {

	private JPanel contentPane;
	private JTextField modules;
	private JTextField timetaken;
	private static String name,department,subject,sem;
	private JLabel name1;
	private JLabel subject1;
	private JLabel department1;
	private JLabel lblSemester;
	private JLabel semester;
	private Connection conn;
	private static Page1 frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Page1(name,department,subject,sem);
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
	
	public Page1(String n,String d,String s,String se) {
		name=n;
		department=d;
		subject=s;
		sem=se;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		name1 = new JLabel("Name");
		name1.setForeground(Color.WHITE);
		name1.setBounds(176, 13, 88, 16);
		contentPane.add(name1);
		name1.setText(name);
		
		JLabel lblSubject = new JLabel("Subject :");
		lblSubject.setForeground(Color.WHITE);
		lblSubject.setBounds(117, 103, 56, 16);
		contentPane.add(lblSubject);
		
		subject1 = new JLabel("subject");
		subject1.setForeground(Color.WHITE);
		subject1.setBounds(215, 106, 182, 16);
		contentPane.add(subject1);
		subject1.setText(subject);
		
		JLabel lblModulesCompleted = new JLabel("Modules Completed :");
		lblModulesCompleted.setForeground(Color.WHITE);
		lblModulesCompleted.setBounds(57, 138, 128, 16);
		contentPane.add(lblModulesCompleted);
		
		modules = new JTextField();
		modules.setBounds(215, 135, 96, 22);
		contentPane.add(modules);
		modules.setColumns(10);
		
		JLabel lblTimeTaken = new JLabel("Time Taken :");
		lblTimeTaken.setForeground(Color.WHITE);
		lblTimeTaken.setBounds(99, 171, 117, 16);
		contentPane.add(lblTimeTaken);
		
		timetaken = new JTextField();
		timetaken.setBounds(215, 168, 96, 22);
		contentPane.add(timetaken);
		timetaken.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 42, 408, 2);
		contentPane.add(separator);
		
		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String mod = modules.getText().toString();
				Integer mods = Integer.parseInt(mod);
				System.out.println(mods);
				String tt = timetaken.getText().toString();
				Integer time = Integer.parseInt(tt);
				System.out.println(time);
				String tname = name;
				boolean flag=false;
				try
				{
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","");
					Statement statement = conn.createStatement();
					ResultSet set = statement.executeQuery("select * from scheduler;");
					while(set.next())
					{
						if(tname.contains(set.getString("tname")))
						{
							flag = true;
							statement.executeUpdate("update scheduler set today_mod = " +mods+  " where tname = \""+tname+"\";");
							statement.executeUpdate("update scheduler set today_time = " +time+  " where tname = \""+tname+"\";");
							break;
						}
					}
					if(!flag)
						statement.executeUpdate("insert into scheduler values(\""+tname+"\","+mods+","+time+");");
					
					conn.close();
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
			}
		});
		btnUpdate.setBounds(99, 215, 97, 25);
		contentPane.add(btnUpdate);
		
		JLabel lblDepartment = new JLabel("Department :");
		lblDepartment.setForeground(Color.WHITE);
		lblDepartment.setBounds(99, 57, 88, 16);
		contentPane.add(lblDepartment);
		
		department1 = new JLabel("department");
		department1.setForeground(Color.WHITE);
		department1.setBounds(215, 57, 161, 16);
		contentPane.add(department1);
		department1.setText(department);
		
		lblSemester = new JLabel("Semester :");
		lblSemester.setForeground(Color.WHITE);
		lblSemester.setBounds(106, 81, 79, 16);
		contentPane.add(lblSemester);
		
		semester = new JLabel("New label");
		semester.setForeground(Color.WHITE);
		semester.setBounds(215, 81, 79, 16);
		contentPane.add(semester);
		semester.setText(se);
		
		JButton btnShow = new JButton("show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Page2 page2 = new Page2(name);
				setVisible(false);
				page2.setVisible(true);
			}
		});
		btnShow.setBounds(225, 215, 97, 25);
		contentPane.add(btnShow);
	}
}
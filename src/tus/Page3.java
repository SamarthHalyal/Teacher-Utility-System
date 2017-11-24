package tus;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Page3 extends JFrame{
	public Page3() {
		Object[][] data = null;
		data = new String[10][6];
	
		String[] column_headers = {"number","Name","Department","Remaining Modules","Remaining Time"};
		
		try{
			// 1. Create a connection to database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","");
			// 2. Create  a statement
			Statement statement = conn.createStatement();
			// 3. Execute SQL query
			ResultSet set = statement.executeQuery("select uid,tname,did from register;");
			// 4. Process the result set
			String[] list = {"Computer Science","Mechanical","Civil"};
			int i=0;
			while(set.next()) {
				data[i][0] = set.getString("uid").toString();
				data[i][1] = set.getString("tname").toString();
				data[i][2] = list[Integer.parseInt(set.getString("did").toString())-1];
				i++;
			}
			
			set = statement.executeQuery("select mod_rem,time_rem from updates;");
			// 4. Process the result set
			i = 0;
			while(set.next()) {
				data[i][3] = set.getString("mod_rem").toString();
				data[i][4] = set.getString("time_rem").toString();
				i++;
			}
			conn.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

		JTable table = new JTable(data,column_headers);
		table.setBounds(24, 346, 442, 231);
		JScrollPane sp = new JScrollPane(table);
		getContentPane().add(sp);
		
		
		
		this.setSize(700, 300);
		this.setVisible(true);
	}
	
	public static void main(String arg[]){
		
	}
}

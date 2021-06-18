package DatabaseCM;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Connection;

public class DBConnection {
	static Connection conn;
	private final static DBConnection instance = new DBConnection();
	
	public static DBConnection getInstance() {
		return instance;
	}
	
	public Connection getConn() {
		return conn;
	}
	public static void setConn(Connection conn) {
		DBConnection.conn = conn;
	}
	
	
		
	public DBConnection() {

		try {
        String dbHost = "cabinet.c2hccmzxgo5q.us-east-2.rds.amazonaws.com";
        String dbPort = "3306";
        String dbUser = "cabinet";
        String dbPass = "cabinet123";

        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/cabinet";
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        conn = DriverManager.getConnection(connectionString, dbUser, dbPass);
        
        
        } catch(Exception ex) {
			System.out.println("Database connection error. SQLException: " + ex.getMessage());
		} 
    }
	
	public static boolean getPerson(String username, String password){

		try {
			
			
			PreparedStatement stm = conn.prepareStatement("SELECT * FROM person WHERE benutzername=\""+username +"\"" );
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				String pass = res.getString(5);
				System.out.println("password: " + pass);
				if(pass.equals(password)) {
					return true;
				}else {
					return false;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getPrivilege(String username) {
		String rolle = "";
		try {
			PreparedStatement stm = conn.prepareStatement("SELECT rolle FROM person WHERE benutzername=\""+username +"\"" );
			ResultSet res = stm.executeQuery();
			
			if(res.next()) {
				rolle = res.getString(1);
				System.out.println("rolle: " + rolle);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return rolle;
	}
	public int getTestNo() {
		int testNo = -1;
		
		try {
			System.out.println("hey2");
			PreparedStatement stm = conn.prepareStatement("SELECT * FROM results ORDER BY testNo DESC LIMIT 1");
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				System.out.println("hey3");
				testNo = (res.getInt(2));
				System.out.println("testno: " + testNo);
			}
			
		}catch(Exception e) {
			
		}
		
		return testNo+1;
	}
	
	public boolean isRegistered(String deviceName) {
		boolean reg = false;
		
		try {
			System.out.println("reg control");
			PreparedStatement stm = conn.prepareStatement("SELECT * FROM device where deviceName=\'" + deviceName + "\'");
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				System.out.println("theres such device!");
				reg = true;
			}else {
				System.out.println("theres no such device!");
				reg = false;
			}
			
		}catch(Exception e) {
			
		}
		
		
		return reg;
	}
	
	public void insertD() {
		String query = "insert into cabinet.device (deviceName, success) values ('prdeneme2', b'0')";
		
		try {
			PreparedStatement stm = conn.prepareStatement(query);
			stm.execute();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void insertDevice(String deviceName, boolean success) {
		System.out.println("inside insertdevice!! ");
		System.out.println("device: "+ deviceName + " " + success);
		
		
		try{
			
			String query = "INSERT INTO cabinet.device (deviceName, success) VALUES (?,?)";
			PreparedStatement stm = conn.prepareStatement(query);
			
			stm.setString(1, deviceName);
			stm.setBoolean(2, success);
			
			stm.execute();
			
		}catch(Exception e) {
			
		}	
		
	}
	
	
	
	
	public void insertTest(int testNo, int slotNo, String deviceName, boolean success, String username, LocalDate date) {
		
		System.out.println("inside inserttest ");
		System.out.println("tno: " + testNo + " " + slotNo + " " + deviceName + " " + success  + " " + username + " " + date);
	
		Date d = Date.valueOf(date);
		
		
		try{
			
			String query = "INSERT INTO results (testNo, slotNo, deviceID, success, user, date) VALUES (?,?,?,?,?,?)";
			PreparedStatement stm = conn.prepareStatement(query);
			
			stm.setInt(1, testNo);
			stm.setInt(2, slotNo);
			stm.setString(3, deviceName);
			stm.setBoolean(4, success);
			stm.setString(5, username);
			stm.setDate(6, d);
			
			stm.execute();
			
		}catch(Exception e) {
			
		}	
		
	}
	
	
	public ArrayList<String> getConfig() throws Exception{
		try {
			
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM testkonfiguration");
			
			ResultSet result = statement.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
		
			while(result.next()) {
				
				
				int config = (result.getInt(1));
				int starttemp = (result.getInt(2));
				int zieltemp = (result.getInt(3));
				int steigung = (result.getInt(4));
				int zielzeit = (result.getInt(5));
				
				
				array.add("SETTARGET|"+ String.valueOf(zieltemp) + "|" + String.valueOf(zielzeit) + "|3|" + String.valueOf(steigung));
				
				
			}
			return array ;
		}catch(Exception e) {
			
			System.out.println(e);
		}
		
		return null;
		
		}
}
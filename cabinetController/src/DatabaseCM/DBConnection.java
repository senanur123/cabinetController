package DatabaseCM;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
			PreparedStatement stm = conn.prepareStatement("SELECT * FROM cabinet.testtable ORDER BY idtesttable DESC LIMIT 1");
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				System.out.println("hey3");
				testNo = (res.getInt(1));
				System.out.println("testno: " + testNo);
			}
			
		}catch(Exception e) {
			
		}
		
		return testNo+1;
	}
	
	
	public ArrayList<String> getConfig() throws Exception{
		try {
			
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM testkonfiguration");
			
			ResultSet result = statement.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			int i = 0;
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
	/*
	//	INSERT  ---Gerate
	public static void insertData() throws Exception{
		
		Scanner scan = new Scanner(System.in) ;
		System.out.println("Person ismini giriniz:");
		final String nameperson = scan.next() ;
		System.out.println("Soyismini giriniz:") ;
		final String nachname = scan.next();
		System.out.println("Kullanici adini giriniz:") ;
		final String benutzername = scan.next() ;
		System.out.println("Sifre giriniz:") ;
		final String passwort = scan.next();
		System.out.println("Kullanici active/inactive giriniz:") ;
		final String status = scan.next();
		System.out.println("Kullanici rolle giriniz:") ;
		final String rolle = scan.next();
		
		try {
			
			
			PreparedStatement posted = conn.prepareStatement("INSERT INTO person (nameperson, nachname, benutzername, passwort, status, rolle) VALUES ('"+nameperson+"' ,'"+nachname+"' ,'"+benutzername+"' ,'"+passwort+"' ,'"+status+"' , '"+rolle+"')");
			posted.executeUpdate();
		}
		catch(Exception e) {
			
			System.out.println("Veri eklenemedi."+e) ;
		}
		finally {
			
			System.out.println("Veri eklendi.") ;
		}
		
	}
	
	//	DELETE
	public static void deleteData() throws Exception{
		Scanner scan = new Scanner(System.in) ;
		System.out.println("Person ismini giriniz:");
		final String nameperson = scan.next() ;
		
		try {
	
			PreparedStatement posted = conn.prepareStatement("DELETE FROM person WHERE nameperson = ' "+ nameperson +" ' ;");
			posted.setString(1, nameperson);
			posted.executeUpdate();
		}
		catch(Exception e) {
			
			System.out.println("Veri silinemedi."+e) ;
		}
		finally {
			
			System.out.println("Veri silindi.") ;
		}
		
	}
	
	//	UPDATE
	public static void updateData() throws Exception{
		Scanner scan = new Scanner(System.in) ;
		System.out.println("Person ismini giriniz:");
		final String nameperson = scan.next() ;
		
		try {
			PreparedStatement posted = conn.prepareStatement("UPDATE FROM person WHERE nameperson = ' "+ nameperson +" ' ;");
			posted.setString(1, nameperson);
			posted.executeUpdate();
		}
		catch(Exception e) {
			
			System.out.println("Veri silinemedi."+ e) ;
		}
		finally {
			
			System.out.println("Updated.") ;
		}
		
	}
	
	
	
	
	public static boolean registerDevice(String gid, int slotno) {
		
		try {
			
			PreparedStatement statement = conn.prepareStatement("INSERT INTO geraet (geraetname, slotnummer) values(\'"+ gid + "\'," + slotno + ");");
			
			ResultSet result = statement.executeQuery();
			System.out.println("result from insert into: " + result);
			
			return result.next();
			
			
		} catch ( SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public static ArrayList<String> showTableperson() throws Exception{
		try {
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM person");
			
			ResultSet result = statement.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()) {
				System.out.print(result.getString(1)); // id
				System.out.print((" "));
				System.out.print(result.getString(2)); // name
				System.out.print((" "));
				System.out.print(result.getString(3)); // nachname
				System.out.print((" "));
				System.out.print(result.getString(4)); // benutzername
				System.out.print((" "));
				System.out.print(result.getString(5)); // password
				System.out.print((" "));
				System.out.print(result.getString(6)); // status
				System.out.print((" "));
				System.out.print(result.getString(7)); // rolle
				System.out.print(" \n");
			
				
				array.add((String) result.getString("idperson")); // 0
				array.add((String) result.getString("nameperson"));
				array.add((String) result.getString("nachname"));
				array.add((String) result.getString("benutzername")); // 3
				array.add((String) result.getString("passwort")); // 4
				array.add((String) result.getString("status"));
				array.add((String) result.getString("rolle"));
			}
			return array ;
		}catch(Exception e) {
			
			System.out.println(e);
		}
		
		return null ;
		
		}
		
		public static void main(String[] args) throws Exception {
		
			getConnection() ;
			insertData() ;
		//	deleteData();
		//  updateData();
		//	showTableperson();
			
	}
	*/
	

}
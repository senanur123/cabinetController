package DatabaseCM;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class DBConnection {
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		try {
        String dbHost = "cabinet.c2hccmzxgo5q.us-east-2.rds.amazonaws.com";
        String dbPort = "3306";
        String dbUser = "cabinet";
        String dbPass = "cabinet123";

        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/cabinet";
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return conn;
        
        } catch(Exception ex) {
			System.out.println("Database connection error. SQLException: " + ex.getMessage());
		} //+ ex.getSQLState() +ex.getErrorCode()
		return null ;
    }
	
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
			
			Connection conn = getConnection() ;
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
			
			Connection conn = getConnection() ;
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
			Connection conn = getConnection() ;
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
	
	//	SELECT	
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

}
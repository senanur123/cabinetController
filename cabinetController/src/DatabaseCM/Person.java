package DatabaseCM;

public class Person {
	
	private int idperson;
	private String nameperson;
	private String nachname;
	private String benutzername;
	private String passwort;
	private String status;
	private String rolle;
	
	public int getIdperson() {
		return idperson;
	}
	public void setIdperson(int idperson) {
		this.idperson = idperson;
	}
	public String getNameperson() {
		return nameperson;
	}
	public void setNameperson(String nameperson) {
		this.nameperson = nameperson;
	}
	public String getNachname() {
		return nachname; 
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getBenutzername() {
		return benutzername;
	}
	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRolle() {
		return rolle;
	}
	public void setRolle(String rolle) {
		this.rolle = rolle;
	}
		
	public Person(int idperson, String nameperson, String nachname, String benutzername, String passwort, String status, String rolle) {
		
		this.idperson = idperson;
		this.nameperson = nameperson;
		this.nachname = nachname;
		this.benutzername = benutzername;
		this.passwort = passwort;
		this.status = status;
		this.rolle = rolle;		
	}
	
}

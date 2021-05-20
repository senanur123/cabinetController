package DatabaseCM;

public class Tests {
	
	private int idtesttable;
	private String uhrzeit;
	private String datum;
	private int testnummer;
	private double starttemp;
	private double zieltemp;
	// ArrayList 
	
	public int getTestnummer() {
		return testnummer;
	}
	public void setTestnummer(int testnummer) {
		this.testnummer = testnummer;
	}
	public double getStarttemp() {
		return starttemp;
	}
	public void setStarttemp(double starttemp) {
		this.starttemp = starttemp;
	}
	public double getZieltemp() {
		return zieltemp;
	}
	public void setZieltemp(double zieltemp) {
		this.zieltemp = zieltemp;
	}
	
	public int getIdtesttable() {
		return idtesttable;
	}
	public void setIdtesttable(int idtesttable) {
		this.idtesttable = idtesttable;
	}
	public String getUhrzeit() {
		return uhrzeit;
	}
	public void setUhrzeit(String uhrzeit) {
		this.uhrzeit = uhrzeit;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	
	public Tests(int idtesttable, String uhrzeit, String datum) {
		
		this.idtesttable= idtesttable;
		this.uhrzeit = uhrzeit;
		this.datum = datum;
}

}

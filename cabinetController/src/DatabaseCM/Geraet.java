package DatabaseCM;

public class Geraet {
	
	public String geraetid;
	public int slotno;
	public boolean failed;
	
	public Geraet(int slotno, String gid) {
		this.slotno = slotno;
		this.geraetid = gid;
	}
	
	public Geraet(String gid, boolean failed) {
		this.geraetid = gid;
		this.failed = failed;
	}
	
	public String getGeraetid() {
		return geraetid;
	}
	public void setGeraetid(String geraetid) {
		this.geraetid = geraetid;
	}
	
	public int getSlotno() {
		return slotno;
	}
	public void setSlotno(int slotno) {
		this.slotno = slotno;
	}

}

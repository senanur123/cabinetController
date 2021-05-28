package DatabaseCM;

public class Geraet {
	
	public String geraetid;
	public int slotno;
	
	public Geraet(int slotno, String gid) {
		this.slotno = slotno;
		this.geraetid = gid;
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

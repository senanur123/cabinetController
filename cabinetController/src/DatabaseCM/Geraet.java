package DatabaseCM;

public class Geraet {
	
	private String geraetid;
	private int slotno;
	private boolean failed;
	
	public Geraet(int slotno, String gid) {
		this.slotno = slotno;
		this.geraetid = gid;
		this.failed = true;
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
	
	public boolean getFailed() {
		return this.failed;
	}
	public void setFailed(boolean failed) {
		this.failed = failed;
	}

}

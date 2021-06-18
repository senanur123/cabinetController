package DatabaseCM;

public class Device{
	
	public String deviceid;
	public int slotno;
	public boolean success;
	
	public Device(int slotno, String gid) {
		this.slotno = slotno;
		this.deviceid = gid;
		this.success = false;
	}
	
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String geraetid) {
		this.deviceid = geraetid;
	}
	
	public int getSlotno() {
		return slotno;
	}
	public void setSlotno(int slotno) {
		this.slotno = slotno;
	}
	
	public boolean getSuccess() {
		return this.success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

}

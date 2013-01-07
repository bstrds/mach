
public class Instance {
	
	private byte[] vect;
	private byte value;
	
	public byte getVal() {
		return value;
	}
	
	public byte[] getVec() {
		return vect;
	}
	
	public Instance() {
		vect = new byte[10];
		value = 0;
	}
	
	public void fill(String s) {
		String[] temp;
		temp = s.split(",");
		for(int i=0; i<10; i++) {
			vect[i] = Byte.parseByte(temp[i]);
		}
		value = Byte.parseByte(temp[10]);
	}
	
	public String toString() {
		String result ="";
		
		for(int i=0; i<10; i++) {
			result += vect[i]+",";
		}
		result += value;
		return result;
	}
}


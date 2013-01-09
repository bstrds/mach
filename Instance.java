
public class Instance {
	
	private String[] vect;
	private String value;
	
	public String getVal() {
		return value;
	}
	
	public String[] getVec() {
		return vect;
	}
	
	public Instance() {
		vect = null;
		value = null;
	}
	
	public void fill(String s) {
		String[] temp;
		temp = s.split(",");
		vect = new String[temp.length-1];
		for(int i=0; i<(temp.length-1); i++) {
			vect[i] = temp[i];
		}
		value = temp[temp.length-1];
	}
	
	public String toString() {
		String result ="";
		
		for(int i=0; i<vect.length; i++) {
			result += vect[i]+",";
		}
		result += value;
		return result;
	}
}


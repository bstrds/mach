
public class Instance {
	
	private double[] vect;
	private String value;
	
	public String getVal() {
		return value;
	}
	
	public double[] getVec() {
		return vect;
	}
	
	public Instance() {
		vect = new double[4];
	}
	
	public void fill(String s) {
		String[] temp;
		temp = s.split(",");
		for(int i=0; i<4; i++) {
			vect[i] = Double.parseDouble(temp[i]);
		}
		value = temp[4];
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


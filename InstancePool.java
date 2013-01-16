import java.util.ArrayList;

public class InstancePool {
	
	private ArrayList<Instance> instl;
	private int numOfInsts;
	
	public InstancePool() {
		instl = new ArrayList<Instance>();
		numOfInsts = 0;
	}
	
	public int getNum() {
		return numOfInsts;
	}
	
	public Instance getInst() {
		return instl.get(0);
	}
	
	public ArrayList<Instance> getList() {
		return instl;
	}
	
	public void fill(String s) {
		Instance temp = new Instance();
		temp.fill(s);
		instl.add(temp);
		numOfInsts++;
	}
	
	public String toString() {
		String result = "";
		for(Instance it : instl) {
			result += it+"\n";
		}
		return result;
	}
}

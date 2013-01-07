import java.io.*;

public class Main {
	
	public static void main(String[] args) {
		
		InstancePool ip = new InstancePool();
		
		try 
		{
			FileInputStream fstream = new FileInputStream("poker-hand-training-true.data");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			
			while((line = br.readLine()) != null) {
				ip.fill(line);
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("error");
		}
		
		//int le = ip.getNum();
		//System.out.println(ip);
		
		Bayes b = new Bayes();
		b.train(ip);
		System.out.println(b);
		double[][] cops = b.getCops();
		for(int i=0; i<13; i++) {
			System.out.println("token "+(i+1));
			for(int j=0; j<10; j++) {
				System.out.println("probability of category "+j+" = "+cops[i][j]);
			}
		}
		
		InstancePool testPool = new InstancePool();
		
		try 
		{
			FileInputStream fstream = new FileInputStream("poker-hand-testing.data");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			
			while((line = br.readLine()) != null) {
				testPool.fill(line);
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("error");
		}
		
		int c = 0;
		
		System.out.println(testPool.getNum());
		
		for(Instance inst : testPool.getList()) {
			if(b.classify(inst)==inst.getVal()) {
				c++;
			}
		}
		
		System.out.println("Success ratio : "+(double)c/testPool.getNum());

	}
}

import java.io.*;

public class Main {
	
	public static void main(String[] args) {
		
		InstancePool ip = new InstancePool();
		
		try 
		{
			FileInputStream fstream = new FileInputStream("irisnew");
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
		System.out.println(ip);
		
		
	}
}

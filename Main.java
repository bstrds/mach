import java.io.File;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		InstancePool tr_ip = new InstancePool();
		InstancePool te_ip = new InstancePool();
		
		Scanner tr_sc, te_sc;
		tr_sc = te_sc = null;
		
		try 
		{	
			File train = new File("/home/bstrds/workspace/MachLrn/src/houses.data");
			File test = new File("/home/bstrds/workspace/MachLrn/src/houses.data");
			
			tr_sc = new Scanner(train);
			te_sc = new Scanner(test);
			
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		while(tr_sc.hasNextLine()) {
			tr_ip.fill(tr_sc.nextLine());
		}
		
		while(te_sc.hasNextLine()) {
			te_ip.fill(te_sc.nextLine());
		}
		
		System.out.println(tr_ip);
		//System.out.println(tr_ip.getNum());
		
		Reg a = new Reg(tr_ip);
		
		a.grad();
		
		/*Bayes b = new Bayes();
		
		b.train(tr_ip);
		
		String s;
		int counter=0;
		for(Instance inst : te_ip.getList()) {
			s = b.classify(inst);
			if(s.equals(inst.getVal())) {
				counter++;
			}
			System.out.println(s+"::"+inst.getVal());
		}
		System.out.println("Success ratio = "+(double)counter/te_ip.getNum());*/
	}
}

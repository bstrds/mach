import java.io.File;
import java.util.Scanner;

public class mach_learning {
	
	public static void main(String[] args) {
		
		if(args.length==0 || args.length > 4 || (!args[0].equals("-bayes") && (!args[0].equals("-lin_reg")))) {
			
			System.out.println("\nUsage: java mach_learning [switch] [training-set] [testing-set/gradient-descent type]\n\n" +
					"Available switches : {-bayes, -lin_reg --> types : {batch,stoch}}\n\n" +
					"You have to provide a training set and a testing set for naive bayes to work, \n" +
					"but you only need a training set and no testing set for linear regression.\n\n" +
					"ex: java mach_learning -bayes /home/uname/train.data /home/uname/test.data\n" +
					"    java mach_learning -lin_reg /home/uname/train.data batch\n" +
				    "    java mach_learning -lin_reg /home/uname/train.data stoch\n\n" +
					"Datasets *have* to be files with comma-separated values for each example, and\n" +
					"one example per line\n");
			return;
		}
		
		Scanner tr_sc, te_sc;
		tr_sc = te_sc = null;
		
		if(args[0].equals("-bayes")) {
			
			try 
			{	
				File train = new File(args[1]);
				File test = new File(args[2]);
				
				tr_sc = new Scanner(train);
				te_sc = new Scanner(test);
				
			} 
			catch(Exception e) 
			{
				System.out.println("You entered an invalid file name and/or path.");
				return;
			}
			
			InstancePool tr_ip = new InstancePool();
			InstancePool te_ip = new InstancePool();
			
			while(tr_sc.hasNextLine()) {
				tr_ip.fill(tr_sc.nextLine());
			}
			
			while(te_sc.hasNextLine()) {
				te_ip.fill(te_sc.nextLine());
			}
			
			Bayes b = new Bayes();
			
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
			
			System.out.println("Success ratio = "+(double)counter/te_ip.getNum());
			
		} else {
			
			try 
			{	
				File train = new File(args[1]);
				tr_sc = new Scanner(train);
			} 
			catch(Exception e) 
			{
				System.out.println("You entered an invalid file name and/or path.");
				return;
			}
			
			InstancePool tr_ip = new InstancePool();
			
			while(tr_sc.hasNextLine()) {
				tr_ip.fill(tr_sc.nextLine());
			}
			
			Reg a;
			
			if(args.length > 3) {
				if(args[3].equals("--colors")) {
					a = new Reg(tr_ip, -1);
				} else if(args[3].equals("--color")) {
					a = new Reg(tr_ip, 1);
				} else {
					a = new Reg(tr_ip, 0);
				}
			} else {
				a = new Reg(tr_ip, 0);
			}

			if(args[2].equals("stoch"))
				a.stoch_grad();
			else if(args[2].equals("batch"))
				a.batch_grad();
			else
				System.out.println("Invalid gradient descent type. :(");
		}
		
		/* uncomment this if you want to print
		 * the dataset as it's saved in the 
		 * instancePool class */
		//System.out.print(tr_ip);
		
		
		
		
		
		
		
	}
}

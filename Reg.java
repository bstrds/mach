
public class Reg {
	
	public static final double S_CONST = 0.0001;
	public static final double B_CONST = 0.000001;

	int feats_num;
	int examps_num;
	double[] weights;
	InstancePool ip;
	int[] x;
	String output;
	int colors;
	
	public Reg(InstancePool ip, int colors) {
		
		this.ip = ip;
		this.colors = colors;
		
		feats_num = ip.getInst().getVec().length + 1;
		examps_num = ip.getNum();
		
		weights = new double[feats_num];
		x = new int[feats_num];
		
		x[0] = 1;
		
		for(int i=0; i<weights.length; i++) {
			weights[i] = Math.random()*500;
		}
	}
	
	public void stoch_grad() {
		
		double sum;
		double e;

		do {

			e = 0;
			
			for(int j=0; j<examps_num; j++) {
				
				
				String[] temp = ip.getList().get(j).getVec();
				for(int c = 0; c<temp.length; c++) {
					x[c+1] = Integer.parseInt(temp[c]);
				}
				String temp2 = ip.getList().get(j).getVal();
				int y = Integer.parseInt(temp2);
				
				for(int c=0; c<x.length; c++) {
					e += weights[c]*x[c];
				}
				
				e = e-y;
				e = e*e;
				e = e/2;
				
				for(int i=0; i<weights.length; i++) {
					
					sum = 0;
					
					for(int c=0; c<x.length; c++) {
						sum += weights[c]*x[c];
					}
					
					sum = sum - y;
					sum = sum * x[i];
					weights[i] = weights[i] - S_CONST * sum;
				}
				
				output = "";
				
				if(colors==-1) {
					int yaw = (int)(Math.random()*7)+30;
					output = "\u001B[1m\u001B["+yaw+"m";
				} else if(colors==1) {
					output = "\u001B[1m\u001B[35m";
				}
				
				
				for(int i=0; i<weights.length; i++) {
					output+="w"+i+" = "+weights[i]+"\t";
				}
				
				output += "\u001B[0m";
				 
				System.out.print("\r"+output);
				
				//System.out.println("\n"+e);
			}
			
		} while(e>0.0002);
		for(int i=0; i<weights.length; i++) {
			System.out.println("w"+i+" = "+weights[i]);
		}
		System.out.println("Error : "+e);
	}
	
	public void batch_grad() {

		double sum = 0;
		double old;
		
		do {
			
			old = J();
			
			for(int i = 0; i<weights.length; i++) {
				
				sum = 0;
				
				for(int j=0; j<ip.getNum(); j++) {
					
					String[] temp = ip.getList().get(j).getVec();
					for(int c = 0; c<temp.length; c++) {
						x[c+1] = Integer.parseInt(temp[c]);
					}
				
					String temp2 = ip.getList().get(j).getVal();
					int y = Integer.parseInt(temp2);
					
					double tempsum = 0;
					
					for(int c=0; c<x.length; c++) {
						tempsum += weights[c]*x[c];
					}
					
					sum += (tempsum - y)*x[i];	
				}
				
				weights[i] = (weights[i] - B_CONST * sum); 
			}
			
			output = "";
				
			if(colors==-1) {
				int yaw = (int)(Math.random()*7)+30;
				output = "\u001B[1m\u001B["+yaw+"m";
			} else if(colors==1) {
				output = "\u001B[1m\u001B[32m";
			}
			
			for(int i=0; i<weights.length; i++) {
				output += "w"+i+" = "+weights[i]+"\t";
			}
			
			output += "\u001B[0m";
			
			System.out.print("\r"+output);
			
			
		} while(J()!=old) ;
		for(int i=0; i<weights.length; i++) {
			System.out.println("w"+i+" = "+weights[i]+" ");
		}
		System.out.println("Error : "+old);
	}
	
	public double J() {
		
		double sum = 0;
		
		for(int i=1; i<=ip.getNum(); i++) {
			String[] temp = ip.getList().get(i-1).getVec();
			for(int c = 1; c<temp.length; c++) {
				x[c] = Integer.parseInt(temp[c]);
			}
			String temp2 = ip.getList().get(i-1).getVal();
			int y = Integer.parseInt(temp2);
			
			double tempsum = 0;
			
			for(int c=0; c<x.length; c++) {
				tempsum += weights[c]*x[c];
			}
			
			sum += (tempsum-y)*(tempsum-y);
		}
		sum = (double)sum/2;
		//System.out.println(sum);
		return sum;
	}
	
	
}

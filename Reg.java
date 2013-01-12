
public class Reg {
	int num;
	double[] weights;
	InstancePool ip;
	int[] x;
	
	public Reg(InstancePool ip) {
		
		this.ip = ip;
		
		num = ip.getInst().getVec().length + 1;
		weights = new double[num];
		
		x = new int[num];
		
		x[0] = 1;
		
		for(int i=0; i<weights.length; i++) {
			weights[i] = 250;//Math.random()*250;
		}
		/*weights[0] = 500;
		weights[1] = 130;
		weights[2] = 10;
		weights[3] = -60;*/
	}
	
	public void stoch_grad() {
		
		double sum;
		double e;
		
		do {

			e = 0;
			
			for(int j=0; j<num; j++) {
				
				sum = 0;
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
					
					for(int c=0; c<x.length; c++) {
						sum += weights[c]*x[c];
					}
					
					sum = sum - y;
					sum = sum * x[i];
					weights[i] = weights[i] - 0.0000001 * sum;
				}
				
				for(int i=0; i<weights.length; i++) {
					System.out.print("w"+i+" = "+weights[i]+" ");
				}
				System.out.println("\n"+e);
			}
			
		} while(e>0.00000000000000000002);
		for(int i=0; i<weights.length; i++) {
			System.out.println("w"+i+" = "+weights[i]+" ");
		}
	}
	
	public void batch_grad() {
		/*for(int i=0; i< weights.length; i++) {
			System.out.println(weights[i]);
		}
		System.exit(0);*/
		double sum = 0;
		double old;
		
		boolean first = true;
		do {
			
			if(first) {
				first = false;
				old = Double.MAX_VALUE;
			}
			else
				old = J();
			
			for(int i = 0; i<weights.length; i++) {
				
				sum = 0;
				
				for(int j=0; j<ip.getNum(); j++) {
					
					String[] temp = ip.getList().get(j).getVec();
					for(int c = 0; c<temp.length; c++) {
						x[c+1] = Integer.parseInt(temp[c]);
						//System.out.println(c+1+" : "+x[c+1]);
					}
					//System.exit(0);
					//int x = Integer.parseInt(temp[0]);
					String temp2 = ip.getList().get(j).getVal();
					int y = Integer.parseInt(temp2);
					//System.out.println(weights[1]+" -- "+weights[0]);
					
					double tempsum = 0;
					
					for(int c=0; c<x.length; c++) {
						tempsum += weights[c]*x[c];
					}
					
					sum += (tempsum - y)*x[i];	
					
					//sum += ((weights[1]*x + weights[0]) - y)*x;
				}
				
				weights[i] = (float)(weights[i] - 0.0000000001*sum); 
			}
			for(int i=0; i<weights.length; i++) {
				System.out.print("w"+i+" = "+weights[i]+" ");
			}
			System.out.println();
		} while(J()!=old && J() < old) ;
		for(int i=0; i<weights.length; i++) {
			System.out.println("w"+i+" = "+weights[i]+" ");
		}
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
			
			//sum += (((weights[1]*x + weights[0]) -y))*(((weights[1]*x + weights[0]) -y));
		}
		sum = (double)sum/2;
		//System.out.println(sum);
		return sum;
	}
	
	
}

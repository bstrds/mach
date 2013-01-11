
public class Reg {
	int num;
	float[] weights;
	InstancePool ip;
	
	public Reg(InstancePool ip) {
		this.ip = ip;
		num = ip.getInst().getVec().length + 1;
		weights = new float[num];
		
		/*for(int i=0; i<weights.length; i++) {
			weights[i] = 0;
		}
		weights[0] = 0;
		weights[1] = 0;*/
	}
	
	public void grad() {
		
		float sum = 0;
		double old;
		do{
			
			old = J();
			for(int i = 0; i<weights.length; i++) {
				
				sum = 0;
				
				for(int j=1; j<ip.getNum(); j++) {
					
					String[] temp = ip.getList().get(j).getVec();
					int x = Integer.parseInt(temp[0]);
					String t2 = ip.getList().get(j).getVal();
					int y = Integer.parseInt(t2);
					//System.out.println(weights[1]+" -- "+weights[0]);
					if(i==0)
						sum += ((weights[1]*x + weights[0]) - y);
					else
						sum += ((weights[1]*x + weights[0]) - y)*x;
				}
				
				weights[i] = (float)(weights[i] - 0.00001*sum); 
			}
			
			
		}while(J() != old) ;
		System.out.println(weights[1]+" -- "+weights[0]);
	}
	
	public double J() {
		double sum = 0;
		
		for(int i=1; i<ip.getNum(); i++) {
			String[] temp = ip.getList().get(i).getVec();
			int x = Integer.parseInt(temp[0]);
			String t2 = ip.getList().get(i).getVal();
			int y = Integer.parseInt(t2);
			
			sum += (((weights[1]*x + weights[0]) -y))*(((weights[1]*x + weights[0]) -y));
		}
		sum = (double)sum/2;
		System.out.println(sum);
		return sum;
	}
	
	
}

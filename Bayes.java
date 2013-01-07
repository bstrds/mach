
public class Bayes {
	
	double[] probs;
	double[][] coprobs;
	double[] scores;
	
	public double[][] getCops() {
		return coprobs;
	}
	
	public double[] getProbs() {
		return probs;
	}
	
	public Bayes() {
		probs = new double[10];
		coprobs = new double[13][10];
		scores = new double[10];
	}
	
	public void train(InstancePool trainingPool) {
		
		int num = trainingPool.getNum();
		
		if(num==0) 
			return;
		
		int[] freqs = new int[10];
		
		int[] tokenCount;
		
		byte[] vect;
		
		for(int i=0; i<10; i++) {		
			
			tokenCount = new int[13];
			
			for(Instance inst : trainingPool.getList()) {
				
				if(inst.getVal()==i) {
				
					vect = inst.getVec();
					
					freqs[inst.getVal()]++;
					
					for(int j=0; j<10; j++) {
						tokenCount[(vect[j]-1)]++;
					}
				}
			}
			
			int otherSum;
			
			for(int k=0; k<13; k++) {
				otherSum = 0;
				for(int c=0; c<13; c++) {
					if(c==k)
						continue;
					otherSum += tokenCount[c]+1; 
				}
				coprobs[k][i] = (double)(tokenCount[k]+1)/otherSum;
			}
			
		}	
		
		for(int i=0; i<10; i++) {
			probs[i] = (double)freqs[i]/num;
		}
	}
	
	public int classify(Instance inst) {
		byte[] vect = inst.getVec();
		
		for(int i=0; i<10; i++) {
			scores[i] = probs[i];
			for(byte j : vect) {
				scores[i] += coprobs[j-1][i];
				//System.out.println(scores[i]);
			}
		}
		
		double max = Double.MIN_VALUE;
		int c = Integer.MIN_VALUE;
		
		//System.out.println(scores[0]+" :: "+max);
		
		for(int i=0; i<10; i++) {
			if(scores[i] > max) {
				//System.out.println("\n\n\n\nYUP!!\n\n\n\n");
				max = scores[i];
				c = i;
			}
		}
		System.out.println(c+" :: "+inst.getVal());
		//System.exit(0);
		return c;
	}
	
	public String toString() {
		String s = "";
		for(int i=0; i<10; i++) {
			s += "probability of result "+i+" : "+probs[i]+"\n";
		}
		return s;
	}
}

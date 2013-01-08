import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Bayes {
	
	double[] probs;
	double[][] coprobs;
	HashMap<String, Integer> tokens;
	HashMap<String, Integer> classes;
	double[] score;
	Set<String> categories;
	
	public double[][] getCops() {
		return coprobs;
	}
	
	public double[] getProbs() {
		return probs;
	}
	
	public Bayes() {
		probs = null;
		coprobs = null;
		score = null;
	}
	
	public void train(InstancePool trainingPool) {
		
		int num = trainingPool.getNum();
		if(num==0) 
			return;
		
		Set<String> vocab = new HashSet<String>();
		this.categories = new HashSet<String>();
		this.classes = new HashMap<String, Integer>();
		this.tokens = new HashMap<String, Integer>();
		
		/*getting the vocabulary, and the classes*/
		for(Instance inst : trainingPool.getList()) {
			for(String s : inst.getVec()) {
				vocab.add(s);
			}
			categories.add(inst.getVal());
		}
		
		/*setting the probs array size according
		 * to the number of classes we have */
		this.probs = new double[categories.size()];
		this.score = new double[categories.size()];

		/*setting the coprobs array size according
		 * to the number of classes we have, and 
		 * the vocabulary size */
		this.coprobs = new double[vocab.size()][categories.size()];
		/*counting docs in each class,
		 * and setting the probability of each
		 * class to occur */
		int i = 0;
		for(String category : categories) {
			
			classes.put(category, i);
			
			ArrayList<String> text = new ArrayList<String>();
			HashMap<String, Integer> appearances = new HashMap<String, Integer>();
			
			for(Instance inst : trainingPool.getList()) {
				if(inst.getVal().equals(category)) {
					probs[i]++;
					for(String token : inst.getVec()) {
						text.add(token);
					}
				}
			}
			
			probs[i] = (double)probs[i]/num;
			System.out.println("Category "+category+" probability is : "+probs[i]);
			
			int count;
			int index = 0;
			for(String token : vocab) {
				
				tokens.put(token, index);
				count = 0;
				for(String word : text) {
					if(token.equals(word))
						count++;
				}
				appearances.put(token, count);
				index++;
			}
			
			int tkn_count = 0;
			for(String token : vocab) {
				System.out.println("Appearances of \""+token+"\" in class "+category+" : "+
						appearances.get(token));
				
				int app_sum = 0;
				
				for(String word : vocab) {
					if(!word.equals(token))
						app_sum += appearances.get(word) + 1;
				}
				
				coprobs[tkn_count][i] = (double)(appearances.get(token)+1)/app_sum;
				System.out.println("co-prob of token "+token+" existing in class "+
						category+" is "+coprobs[tkn_count][i]);
				tkn_count++;
			}
			i++;
		}
		
		
		
		
		
	}
	
	public String classify(Instance inst) {
		
		int i = 0;
		for(String category : categories) {
			
			score[i] = Math.log(probs[i]);
			
			for(String word : inst.getVec()) {
				int t = 0;
				if(tokens.containsKey(word))
					t = tokens.get(word);
				else {
					System.out.println(word);
				}
				int c = classes.get(category);
				score[i] += Math.log(coprobs[t][c]);
			}
			i++;
		}
		
		double max = score[0];
		int arg = -1;
		for(int c=1; c<score.length; c++) {
			if(score[c]>max) {
				max = score[c];
				arg = c;
			}
		}
		
		String result = "";
		for(String cat : categories) {
			if(classes.get(cat) == arg) {
				result = cat;
				break;
			}
		}
		
		return result;
	}
	
	public String toString() {
		String s = "";
		for(int i=0; i<10; i++) {
			s += "probability of result "+i+" : "+probs[i]+"\n";
		}
		return s;
	}
}

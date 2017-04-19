/**
 * 
 */
package formula;

import java.util.List;

import tree.Action;
import tree.node.State;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 19, 2017
 */
public class ProgressiveWidening implements NodeSelection{

	private final double C = 2.44;
	private final double a = 0.666;
	private final double kUCB = Math.sqrt(2.0);
	
	@Override
	public State select(State s) {
		// let nbVisits = nbVisits() + 1;
		s.visits();
		
		// let t = nbVisits(s)
		int t = s.nbVisits();
		
		// let k =  C * t^a 
		int k = (int) Math.ceil( C * Math.pow(t, a));
		
		/*
		 *  Choose an option o(t)(s) € {o1(s), ..., ok(s)}
		 *  maximizing score t (s, o) defined
		 *  as follows:
		 */
		double totalReward = 0.0,
				score = 0.0,
				best = Double.MIN_NORMAL;
		int nb = 0;
	
		Action action = null, ot;
		List<Action> options = s.options(k);
		for ( int i = 0 ; i < k ; i++ ) {
			ot = options.get(i);
			
			score = (totalReward / (nb + 1));
			score += kUCB * Math.sqrt( Math.log10(t) / (nb + 1));
			
			if ( nb == 0 ) { score = Double.MAX_VALUE; }
			
			if (score > best) {
				best = score;
				action = ot;
			}
		}
		
		return s.addChild(action);
	}

}

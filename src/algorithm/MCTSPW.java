/**
 * 
 */
package algorithm;

import formula.ProgressiveWidening;
import tree.node.State;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 19, 2017
 */
public class MCTSPW extends MCTS{

	public MCTSPW() {
		super( new ProgressiveWidening() );
	}
	
	@Override
	public State selection(State node) {
		return node;
	}

	@Override
	public State expansion(State node) {
		return formula.select(node);
	}

	@Override
	public State simulation(State node) {
		State simulation = node;

		while( !simulation.isTerminal() ) {
			simulation = expansion(simulation);
		}
		return simulation;
	}

	@Override
	public State backPropagation(State node, double reward) {
		while( !node.isRoot() ) {
			node.setReward(reward);
			node = node.parent();		
		}
		return node;
	}

}

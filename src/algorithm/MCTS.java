/**
 * 
 */
package algorithm;

import formula.NodeSelection;
import tree.node.State;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 19, 2017
 */
public abstract class MCTS {

	protected NodeSelection formula;
	
	public MCTS(NodeSelection nodeSelection) {
		formula = nodeSelection;
	}
	
	public State execute(State node) {
		State selected = selection(node);

		selected = simulation(selected);

		selected = backPropagation(selected, selected.reward());
		
		return selected;
	}
	
	public abstract State selection(State node);
	public abstract State expansion(State node);
	public abstract State simulation(State node);
	public abstract State backPropagation(State node, double reward);
}

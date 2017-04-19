/**
 * 
 */
package tree.node;

import java.util.LinkedList;
import java.util.List;

import game.trap.TrapAction;
import tree.Action;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 19, 2017
 */
public abstract class State {

	private State parent = null;
	private Action action = null;

	protected List<State> childs = null; 

	protected int simulations;
	protected double reward,
			score;
	
	public State() {
		simulations = 0;
		reward = 0.0;
		score = 0.0;
		childs = new LinkedList<State>();
	}
	
	public State(State p, Action a) {
		parent = p;
		
		score = p.score;
		simulations = 0;
		reward = 0.0;
		childs = new LinkedList<State>();
		
		apply(a);
	}
	
	public State parent() { return parent; }
	
	public State child(int k) { 
		return childs.get(k);
	}
	
	public boolean isRoot() { return parent == null; }
	
	public int nbChilds() { return childs.size(); }
	public int nbVisits() { return simulations; }
	
	public void visits() { simulations++; }
	public void setSimulation(int sim) { simulations = sim; }
	public void setReward(double rew) { reward = rew; }
	public int simulations() { return simulations; }

	public abstract void apply(Action a);
	public abstract double reward();
	public abstract boolean isTerminal();
	public abstract State addChild(Action action);
	public abstract List<Action> options(int l);
}

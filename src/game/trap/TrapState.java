/**
 * 
 */
package game.trap;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import tree.Action;
import tree.node.State;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 19, 2017
 */
public class TrapState extends State{
	
	private final int[][] world = {{100,70}, 
			{170,0}, 
			{1000,100}};
	
	private final double[] interval = {0.0, 1.0};
	
	private int limit = 2;
	
	private double position;
	
	public TrapState( ) {
		position = 0.0;
	}
	
	public TrapState(TrapState ts, Action ta) {
		super(ts, ta);
		position = ts.position;
		limit = ts.limit;
	}
	
	// copy constructor
	public TrapState(TrapState state) {
		
	}
	
	@Override
	public double reward() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void apply(Action a) {
		double length = (double) a.value();
		if ( limit > 0 ) {
			position += length;
			limit--;
			
			
			for (int[] i : world) {
				if (position <= i[0]) {
					this.score += i[1];
					break;
				}
			}
		}
	}

	@Override
	public boolean isTerminal() {
		return this.limit == 0 ? true : false;
	}

	@Override
	public State addChild(Action action) {
		
		State child = new TrapState(this, action);
		childs.add(child);
		
		return child;
	}

	@Override
	public List<Action> options(int l) {
		List<Action> options = new LinkedList<Action>();
		Random rand = new Random();
		
		while (l > 0) {
			options.add(new TrapAction( interval[0] + 
					rand.nextDouble() * interval[1]));
			l--;
		}
		return options;
	}

}

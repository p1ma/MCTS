/**
 * 
 */
package game.trap;

import tree.Action;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 19, 2017
 */
public class TrapAction extends Action{

	private double step;
	
	public TrapAction(double s) {
		step = s;
	}
	
	@Override
	public Object value() {
		return step;
	}

}

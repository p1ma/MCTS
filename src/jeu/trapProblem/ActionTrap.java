/**
 * 
 */
package jeu.trapProblem;

import arbre.Action;
import arbre.Noeud;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Feb 16, 2017
 */
public class ActionTrap implements Action{

	private double pas;

	public ActionTrap(double ls) {
		this.pas = ls;
	}
	
	public ActionTrap(Action act) {
		pas = (double) act.getRepresentation();
	}

	@Override
	public Object getRepresentation() {
		return this.pas;
	}

	@Override
	public boolean estGagnante(Noeud noeud) {
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		ActionTrap trap = (ActionTrap)obj;
		return (trap.pas == pas);
	}

	@Override
	public String toString() {
		return "pas = " + pas;
	}

	@Override
	public void setValeurAction(Object o) {
		double r = (double) o;
		pas = r;
	}

}

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

	public double retournerPas() {
		return this.pas;
	}

	public boolean estGagnante(Noeud noeud) {
		/*
		 * Le jeu n'a pas de critère d'arret
		 * telle qu'une victoire ou non
		 * return (noeud.getEtat().getNbCoups() == 0);
		 */
		return false;
	}

	public void ajouterBruit(double x) {
		this.pas += x;
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

}

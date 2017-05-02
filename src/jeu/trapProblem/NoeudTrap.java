/**
 * 
 */
package jeu.trapProblem;

import java.util.List;
import java.util.Random;

import arbre.Action;
import arbre.Etat;
import arbre.Noeud;
import arbre.NoeudContinue;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Feb 16, 2017
 */
public class NoeudTrap extends NoeudContinue {
	
	public static double R = 0.5;
	
	public NoeudTrap(Etat etat) {
		super(new EtatTrap(etat));
	}
	
	public NoeudTrap(Noeud p, Etat e, Action a) {
		super(p, e, a);
	}
	
	public boolean resteAction() {
		return true;
	}

	@Override
	public Noeud appliquer(Action action) {
		return new NoeudTrap(this, new EtatTrap(getEtat()), action);
	}

	@Override
	public Noeud ajouterEnfant(Action action) {
		double length = (double)action.getRepresentation();
		length += R * (new Random()).nextDouble();
		
		action.setRepresentation(length);
		
		Noeud enfant = new NoeudTrap(this, new EtatTrap(getEtat()), action);
		this.enfants.add( enfant );
		return enfant;
	}

	@Override
	public List<Action> actionsPossible(int k) {
		return etat.coups_possibles(k);
	}	
}

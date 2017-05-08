/**
 * 
 */
package jeu.trapProblem;

import java.util.List;
import java.util.Random;

import arbre.Action;
import arbre.Etat;
import arbre.Noeud;
import arbre.NoeudContinu;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Feb 16, 2017
 */
public class NoeudTrap extends NoeudContinu {
	
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
	public NoeudContinu appliquer(Action action) {
		return new NoeudTrap(this, new EtatTrap(getEtat()), action);
	}

	@Override
	public NoeudContinu ajouterEnfant(Action action) {
		NoeudContinu enfant = new NoeudTrap(this, new EtatTrap(getEtat()), action);
		this.enfants.add( enfant );
		return enfant;
	}
	
	@Override
	public NoeudContinu copy() {
		return new NoeudTrap(this, new EtatTrap(etat), new ActionTrap(action));
	}

	@Override
	public List<Action> actionsPossible(int k) {
		return etat.coups_possibles(k);
	}	
	
	@Override
	public void bruitage() {
		double length = (double)action.getRepresentation();
		double bruit = R * (new Random()).nextDouble();
		
		length += bruit;
		action.setValeurAction(length);
		etat.mettreAJour(bruit);
	}
}

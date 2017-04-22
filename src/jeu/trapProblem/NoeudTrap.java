/**
 * 
 */
package jeu.trapProblem;

import java.util.List;

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
	
	public NoeudTrap(Etat etat) {
		super(new EtatTrap(etat));
	}
	
	public NoeudTrap(Noeud p, Etat e, Action a) {
		super(p, e, a);
		etat.jouerAction(a);
	}
	
	public boolean resteAction() {
		return (this.etat.getNbCoups() != 0);
	}

	@Override
	public Noeud appliquer(Action action) {
		return new NoeudTrap(this, getEtat(), action);
	}

	@Override
	public Noeud ajouterEnfant(Action action) {
		Noeud enfant = new NoeudTrap(this, getEtat(), action);
		this.enfants.add( enfant );
		return enfant;
	}

	@Override
	public List<Action> echantillonner(int k) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

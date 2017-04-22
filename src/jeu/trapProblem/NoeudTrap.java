/**
 * 
 */
package jeu.trapProblem;

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
	
	
}

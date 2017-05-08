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
	
	/**
	 * Variable utilisé pour la génération du bruit
	 * bruit = R x Y , avec Y aléatoire dans ]0, 1[
	 */
	public static double R = 0.15;
	
	/** 
	 * Constructeur de NoeudTrap
	 * @param etat, l'Etat du Noeud
	 */
	public NoeudTrap(Etat etat) {
		super(new EtatTrap(etat));
	}
	
	/** 
	 * Constructeur de NoeudTrap
	 * @param e, l'Etat du Noeud
	 * @param p, le Noeud parent
	 * @param a, l'Action du Noeud
	 */
	public NoeudTrap(Noeud p, Etat e, Action a) {
		super(p, e, a);
	}
	
	/**
	 * Indique s'il reste des Actions au Noeud,
	 * dans notre cas : oui tout le temps
	 */
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
		
		Etat et = enfant.getEtat();
		Noeud pare = enfant.predecesseur();
		et.setScore(pare.resultat());
		et.jouerAction(action);
		
		this.enfants.add( enfant );
		return enfant;
	}
	
	@Override
	public NoeudContinu ajouterEnfantBruite(Action action) {
		NoeudContinu enfant = new NoeudTrap(this, new EtatTrap(getEtat()), action);
		
		Etat et = enfant.getEtat();
		Noeud pare = enfant.predecesseur();
		et.setScore(pare.resultat());
		et.mettreAJour(action);
		this.enfants.add( enfant );
		return enfant;
	}

	@Override
	public List<Action> actionsPossible(int k) {
		return etat.coups_possibles(k);
	}	
	
	/**
	 * Bruite le Noeud this
	 */
	@Override
	public NoeudContinu bruite() {
		// On calcul le bruit a ajouter
		double Y = (new Random()).nextDouble();
		double bruit = R * Y;
		
		bruits.add(bruit);
		
		// On créé l'action
		ActionTrap actionBruit = new ActionTrap(bruit);
		
		// On ajoute l'enfant
		return appliquer(actionBruit);
	}
}

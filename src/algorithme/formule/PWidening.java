/**
 * 
 */
package algorithme.formule;

import java.util.LinkedList;
import java.util.List;

import algorithme.adaptateur.AdaptateurContinue;
import arbre.Action;
import arbre.Noeud;
import jeu.trapProblem.NoeudTrap;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Feb 15, 2017
 */
public class PWidening implements FormuleSelection{

	private final double C = 1.5;
	private double alpha = 0.8; // ]O,1[

	public Noeud selectionner(Noeud noeud) {
		noeud.visiter(0.0); // nbVisits + 1
		int t = noeud.retournerNbSimulation();
		int k = (int)(C * Math.pow(t, alpha));
		/*
		 * On va maintenant échantillonner
		 * le noeud avec les k prochaines Actions possibles
		 */
		Noeud adapt = new AdaptateurContinue(noeud, k);
		List<Action> actions = new LinkedList<Action>(adapt.actionsPossible());
		List<Action> mem = new LinkedList<Action>(actions);

		Noeud enfant = null;
		int best = 0;
		double min = Double.NEGATIVE_INFINITY;
		double bValeur = 0.0;
		Action ol = null;
		for(int i = 0 ; i < k ; i++) {
			enfant = new NoeudTrap(noeud, actions.get(i));
			int nb = 0;

			for ( int l = 0 ; l < t ; l++ ) {
				if (l >= k ) {
					System.out.println("problem : " + l + " avec k = " + k);
				}
					ol = actions.get(l);
					if ( ol.equals(actions.get(i)) ) {
						nb++;
					}
			}

			if ( nb == 0 ) {
				bValeur = Integer.MAX_VALUE;
				best = i;
				break;
			} else {
				bValeur = ( enfant.resultat() / (nb + 1));
				bValeur += k * Math.sqrt( Math.log( t ) / (nb + 1));
			}

			if ( bValeur > min ) {
				min = bValeur;
				best = i;
			}
		}
		/*System.out.println("noeud enfant " + noeud.retournerNbEnfant());
		System.out.println("adapt enfant " + adapt.retournerNbEnfant());*/
		return noeud.ajouterEnfant( mem.get(best) );
	}
}

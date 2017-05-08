/**
 * 
 */
package algorithme.formule;

import java.util.List;

import arbre.Action;
import arbre.Noeud;
import arbre.NoeudContinue;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Feb 15, 2017
 */
public class PWidening implements FormuleSelection{

	public static final double C = 3; // > 0
	public static final double alpha = 0.4; // ]O,1[

	@Override
	public Noeud selectionner(Noeud noeud) {
		return select((NoeudContinue) noeud);
	}

	public Noeud select(NoeudContinue noeud) {
		noeud.visiter(); // nbVisits + 1
		int t = noeud.nbSimulation();
		int k = (int)Math.ceil((C * Math.pow(t, alpha)));		
		/*
		 * On va maintenant échantillonner
		 * le noeud avec les k prochaines Actions possibles
		 */
		List<Action> actions = noeud.actionsPossible(k);
		Noeud enfant = null;
		int best = 0;
		double min = Double.NEGATIVE_INFINITY;
		double bValeur = 0.0, totalReward;
		
		for(int i = 0 ; i < k ; i++) {
			enfant = noeud.recuperer(actions.get(i));

			int nb = enfant.nbSimulation();

			if ( nb == 0 ) {
				// on ajoute et on retourne l'enfant
				return noeud.ajouterEnfant( actions.get(i) );
			} else {
				// equivalent UCB
				totalReward = enfant.nbRecompense();
				
				bValeur = ( totalReward / (nb + 1));
				bValeur += k * Math.sqrt( Math.log( t ) / (nb + 1));
			}

			if ( bValeur > min ) {
				min = bValeur;
				best = i;
			}
		}
		// on retourne le meilleur enfant selon les criteres UCB
		return noeud.retournerEnfant(best);
	}
}

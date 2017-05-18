/**
 * 
 */
package algorithme.formule;

import java.util.List;

import arbre.Action;
import arbre.Noeud;
import arbre.NoeudContinu;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Feb 15, 2017
 */
public class PWidening implements FormuleSelection{

	public static double C = 5; // > 0
	public static double alpha = 0.7; // ]O,1[
	public static final double kucb = Math.sqrt(2.0);

	@Override
	public Noeud selectionner(Noeud noeud) {
		return select((NoeudContinu) noeud);
	}

	public Noeud select(NoeudContinu s) {
		s.visiter(); // nbVisits + 1
		int t = s.nbSimulation();
		int k = (int)Math.ceil((C * Math.pow(t, alpha)));		
		/*
		 * On va maintenant échantillonner
		 * le noeud avec les k prochaines Actions possibles
		 */
		List<Action> actions = s.actionsPossible(k);
		NoeudContinu enfant = null;
		int best = 0;
		double min = Double.NEGATIVE_INFINITY;
		double score = .0, totalReward = .0;

		for(int i = 0 ; i < k ; i++) {
			enfant = s.recuperer(actions.get(i));

			int nb = enfant.nbSimulation();

			if ( nb == 0 ) {
				// on ajoute et on retourne l'enfant
				return s.ajouterEnfant( actions.get(i) );
			} else {
				// equivalent UCB
				totalReward = s.nbRecompense() + enfant.nbRecompense();
				
				score = ( totalReward / (nb + 1));
				score += kucb * Math.sqrt( Math.log( t ) / (nb + 1));
				
				if ( score > min ) {
					min = score;
					best = i;
				}
			}
		}
		// On recupere le meilleur enfant selon les criteres UCB
		enfant = s.retournerEnfant(best);

		// On recupere la valeur du bruit
		Action bruit = enfant.bruite().getAction();
		
		// On recupere ou non l'enfant bruité
		NoeudContinu enfantBruite = enfant.contientAction(bruit);
		if ( enfantBruite == null) {
			enfantBruite = enfant.ajouterEnfantBruite(bruit);
		} 
		// On retourne un noeud bruité
		return enfantBruite;
		
		// On retourne le noeud non bruité
		//return enfant;
	}
	
	public String toString() {
		return "Progressive widening";
	}
}

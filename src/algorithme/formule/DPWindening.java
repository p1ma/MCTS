/**
 * 
 */
package algorithme.formule;

import java.util.List;
import java.util.Random;

import arbre.Action;
import arbre.Noeud;
import arbre.NoeudContinue;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 30, 2017
 */
public class DPWindening implements FormuleSelection {

	public static final double C = 3; // > 0
	public static final double alpha = 0.4; // ]O,1[
	public final Random random = new Random();

	@Override
	public Noeud selectionner(Noeud noeud) {
		return select((NoeudContinue) noeud);
	}

	public Noeud select(NoeudContinue noeud) {
		noeud.visiter(); // nbVisits + 1
		int t = noeud.retournerNbSimulation();
		int k = (int)Math.ceil((C * Math.pow(t, alpha)));		
		/*
		 * On va maintenant échantillonner
		 * le noeud avec les k prochaines Actions possibles
		 */
		List<Action> actions = noeud.actionsPossible(k);
		NoeudContinue enfant = null;
		int best = 0;
		double min = Double.NEGATIVE_INFINITY;
		double score = 0.0, totalReward;
		int nb = 0;
		for(int i = 0 ; i < k ; i++) {
			enfant = noeud.recuperer(actions.get(i));

			nb = enfant.retournerNbSimulation();

			if ( nb == 0 ) {
				totalReward = Double.POSITIVE_INFINITY;
				best = i;
				min = totalReward;
				
				enfant.visiter();
				break;
			} else {
				// equivalent UCT
				totalReward = enfant.resultat() + noeud.resultat();

				score = ( totalReward / (nb + 1));
				score += k * Math.sqrt( Math.log( t ) / (nb + 1));
			}
			if ( score > min ) {
				min = score;
				best = i;
			}
		}
		
		nb = enfant.retournerNbSimulation();
		int kprim = (int)Math.ceil((C * Math.pow(nb, alpha)));

		// pas sur
		if (kprim > enfant.retournerNbEnfant()){

			NoeudContinue nouvelEnfant = enfant.copy();
			nouvelEnfant.bruitage();

			if ( !noeud.contientEnfant( nouvelEnfant ) ) {
				noeud.ajouterEnfant( nouvelEnfant.getAction() );
			} else {
				//enfant.visiter();
			}
			return noeud.retournerEnfant(best);
		} else {		
			return noeud.retournerEnfant(random.nextInt(nb));
		}
	}
}

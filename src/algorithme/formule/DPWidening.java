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
public class DPWidening implements FormuleSelection {

	public static final double C = 3; // > 0
	public static final double alpha = 0.4; // ]O,1[
	public final Random random = new Random();

	@Override
	public Noeud selectionner(Noeud noeud) {
		return select((NoeudContinue) noeud);
	}

	public Noeud select(NoeudContinue s) {
		s.visiter(); // nbVisits + 1
		int t = s.nbSimulation();
		int k = (int)Math.ceil((C * Math.pow(t, alpha)));		
		/*
		 * On va maintenant échantillonner
		 * le noeud avec les k prochaines Actions possibles
		 */
		List<Action> actions = s.actionsPossible(k);
		NoeudContinue enfant = null;
		int best = 0;
		double min = Double.NEGATIVE_INFINITY;
		double score = 0.0, totalReward;
		int nb = 0;
		for (int i = 0 ; i < k ; i++) {
			enfant = s.recuperer(actions.get(i));

			nb = enfant.nbSimulation();

			if ( nb == 0 ) {	
				enfant.visiter();
				best = i;
				break;
			} else {
				// equivalent UCT
				totalReward = enfant.nbRecompense();

				score = ( totalReward / (nb + 1));
				score += k * Math.sqrt( Math.log( t ) / (nb + 1));
			}
			if ( score > min ) {
				min = score;
				best = i;
			}
		}
		// progressive widening on the random part then
		
		nb = enfant.nbSimulation();
		int kprim = (int)Math.ceil((C * Math.pow(nb, alpha)));

		
		if ( kprim > enfant.nbEnfant() ){
			enfant.bruitage();
			//NoeudContinue bruite = enfant.bruitage();
			
			if ( !enfant.contientEnfant( enfant ) ) {
				return enfant.ajouterEnfant( enfant.getAction() );
			}
			return s.retournerEnfant(best);
		} else {		
			return enfant.retournerEnfant(
					random.nextInt(enfant.nbEnfant())
					);
		}
	}
	
	public String toString() {
		return "Double progressive widening";
	}
}
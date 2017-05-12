/**
 * 
 */
package algorithme.formule;

import java.util.List;
import java.util.Random;

import arbre.Action;
import arbre.Noeud;
import arbre.NoeudContinu;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 30, 2017
 */
public class DPWidening implements FormuleSelection {

	public static final double C = 3; // > 0
	public static final double alpha = 0.4; // ]O,1[
	public static final double kucb = Math.sqrt(2.0);

	public final Random random = new Random();

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
		double score = 0.0, totalReward;
		int nb = 0;

		for (int i = 0 ; i < k ; i++) {
			enfant = s.recuperer(actions.get(i));

			nb = enfant.nbSimulation();

			if ( nb == 0 ) {	
				enfant = s.ajouterEnfant( actions.get(i) );
				best = i;
				break;
			} else {
				// equivalent UCB
				totalReward = enfant.nbRecompense() + enfant.resultat();

				score = ( totalReward / (nb + 1));
				score += kucb * Math.sqrt( Math.log( t ) / (nb + 1));
				/*System.out.println();
				System.out.println("pos enfant num " + i + " = " + (double)enfant.getEtat().getPosition());
				System.out.println("#enfant enfant num " + i + " = " + enfant.nbEnfant());
				for(int j = 0 ; j < enfant.nbEnfant() ; j++) {
					System.out.println("\tpos enfant d'enfant num " + i + " = " + (double)enfant.retournerEnfant(j).getEtat().getPosition());
				}
				System.out.println("totalReward enfant num " + i + " = " + totalReward);
				System.out.println("score enfant num " + i + " = " + score);
				System.out.println();*/
			}
			if ( score > min ) {
				min = score;
				best = i;
			}
		}
		// progressive widening on the random part then
		enfant = s.retournerEnfant(best);
		enfant.visiter();
		
		nb = enfant.nbSimulation();
		
		int kprim = (int)Math.ceil((C * Math.pow(nb, alpha)));

		if ( kprim > enfant.nbEnfant() ){

			// On recupere un Noeud bruité
			NoeudContinu bruite = enfant.bruite();
			if ( !enfant.contientEnfant( bruite ) ) {
				return enfant.ajouterEnfantBruite( bruite.getAction() );
			} else {
				return enfant.retournerEnfant(bruite);
			}
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

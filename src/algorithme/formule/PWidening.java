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

	private final double C = 1.0; // > 0
	private final double alpha = 0.5; // ]O,1[

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
		Noeud enfant = null;
		int best = 0;
		double min = Double.NEGATIVE_INFINITY;
		double bValeur = 0.0, totalReward;
		Action ol = null;
		for(int i = 0 ; i < k ; i++) {
			enfant = noeud.appliquer( actions.get(i) );
			int nb = 0;
			int l = 0;
			
			while( l < t-1 ) {
				ol = actions.get(l);
				if ( ol.equals(actions.get(i)) ) {
					nb++;
				}
				l++;
			}

			if ( nb == 0 ) {
				/*System.out.println("\nt=" + t + ", k=" + k + " on ajoute");
				noeud.afficherStatistiques();
				System.out.println("AVEC action : " + actions.get(i));*/
				// on s'arrete car on aura un score infini ici
				return noeud.ajouterEnfant( actions.get(i) );
			} else {
				// equivalent UCT
				totalReward = enfant.resultat() + noeud.resultat();

				bValeur = ( totalReward / (nb + 1));
				bValeur += k * Math.sqrt( Math.log( t ) / (nb + 1));
			}

			if ( bValeur > min ) {
				min = bValeur;
				best = i;
			}
		}
		/*System.out.println("\nt=" + t + ", k=" + k + " on ajoute rien");
		noeud.afficherStatistiques();
		System.out.println("AVEC action : " + noeud.retournerEnfant(best).getAction());*/
		return noeud.retournerEnfant(best);
	}
}

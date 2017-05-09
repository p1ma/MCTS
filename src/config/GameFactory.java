package config;

import algorithme.MCTS;
import algorithme.formule.DPWidening;
import algorithme.formule.FormuleSelection;
import algorithme.formule.PWidening;
import arbre.Etat;
import arbre.Noeud;
import dao.StatistiqueDAO;

public abstract class GameFactory {

	protected final int TEMPS = 1; // 50 ms

	public abstract Etat getEtat(int joueur);
	public abstract Etat getEtat();
	public abstract MCTS getMCTS();
	public abstract Noeud getNoeud(Etat etat);
	public abstract void jouer(FormuleSelection strategie,
			FormuleSelection selectionFinale,
			boolean output);

	public void mcts(Etat etat, 
			FormuleSelection strategie,
			FormuleSelection selectionFinale,
			MCTS mcts,
			boolean output) {
		long tic, toc;

		// Creer l'arbre de recherche
		Noeud racine = getNoeud(etat);
		Noeud enfant = null;

		// On affecte la bonne formule de selection à l'algorithme
		mcts.setFormuleSelection(strategie);

		// On initialise le nombre d'itération à 0.
		int iter = 0;

		// temps de départ
		tic = System.currentTimeMillis();

		// boucle MCTS
		do {
			/*
		    	L'algo se decompose en 4 étapes :
		    	- Selection à partir de l'etat du meileur fils
		    	- Developpement d'un Noeud fils choisit aléatoirement (et non déjà développé)
		    	- Simulation de la fin de la partie avec une marche aléatoire
		    	- Mise à jour des valeurs des Noeuds dans l'arbre, on remonte la valeur de récompense
		    	du Noeud terminal à la racine.
			 */
			racine = mcts.executer(racine);
			toc = System.currentTimeMillis();
			iter++;
		} while (toc < (tic + TEMPS));
		/* 
		 * Affiche le nombre d'itération et le nom de la formule de selection,
		 * ainsi que le nombre d'enfant développé pendant la boucle ci-dessus
		 */
		if (output) {
			System.out.println("Nombre d'itérations : " + iter);
			System.out.println( selectionFinale + " sur " + racine.nbEnfant() + " noeuds");
		}

		// On selectionne le meilleur enfant en utilisant les propriétés de 'selectionFinale'
		enfant = selectionFinale.selectionner(racine);
		
		// On affiche (ou non) l'Action choisit
		if (output) {
			System.out.println("Action choisi " + enfant.getAction());
		}

		// On joue l'Action
		etat.jouerAction(enfant.getAction());

		// On sauvegarde les informations
		sauvegarder(strategie, iter, racine, enfant);
	}

	private void sauvegarder(FormuleSelection strategie, int iter, Noeud racine, Noeud enfant) {
		/*
		 * sale mais efficace
		 */
		if (strategie instanceof DPWidening) {
			StatistiqueDAO.getInstance().ecrireDPW(TEMPS, 
					PWidening.C, 
					PWidening.alpha, 
					iter,
					racine,
					enfant);
		}

		if (strategie instanceof PWidening) {
			StatistiqueDAO.getInstance().ecrireSPW(TEMPS, 
					PWidening.C, 
					PWidening.alpha, 
					iter,
					racine,
					enfant);
		}
	}
}

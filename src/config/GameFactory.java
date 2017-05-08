package config;

import algorithme.MCTS;
import algorithme.formule.DPWidening;
import algorithme.formule.FormuleSelection;
import algorithme.formule.PWidening;
import arbre.Etat;
import arbre.Noeud;
import dao.StatistiqueDAO;

public abstract class GameFactory {
	
	protected final int TEMPS = 50; // 50 ms
	
	public abstract Etat getEtat(int joueur);
	public abstract Etat getEtat();
	public abstract MCTS getMCTS();
	public abstract Noeud getNoeud(Etat etat);
	public abstract void jouer(FormuleSelection strategie,
			FormuleSelection selectionFinale);
	
	public void mcts(Etat etat, 
			FormuleSelection strategie,
			FormuleSelection selectionFinale,
			MCTS mcts) {
		long tic, toc;
		
		// Creer l'arbre de recherche
		Noeud racine = getNoeud(etat);
		Noeud enfant = null;
		mcts.setFormuleSelection(strategie);

		// pre rempli déjà l'arbre

		// S'il y  a plusieurs fils alors on execute l'algo MCTS UCT
		int iter = 0;
		tic = System.currentTimeMillis();
		do {
			/*
		    	L'algo se decompose en 4 étapes :
		    	- Selection à partir de l'etat du meileur fils
		    	- Developpement d'un Noeud fils choisit aléatoirement (et non déjà développé)
		    	- Simulation de la fin de la partie avec une marche aléatoire
		    	- Mise à jours des valeurs des Noeuds dans l'arbre, on remonte la valeur de récompense
		    	du Noeud terminal à la racine.
			 */
			racine = mcts.executer(racine);
			toc = System.currentTimeMillis();
			iter++;
		} while (toc < (tic + TEMPS));
		System.out.println("Nombre d'itérations : " + iter);
		System.out.println( selectionFinale + " sur " + racine.nbEnfant() + " noeuds");
		enfant = selectionFinale.selectionner(racine);
		System.out.println(enfant.getAction());
		etat.jouerAction(enfant.getAction());
		
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

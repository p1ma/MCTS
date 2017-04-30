package main;

import algorithme.MCTS;
import algorithme.MCTSPW;
import algorithme.formule.FormuleSelection;
import algorithme.formule.PWidening;
import arbre.Etat;
import arbre.Noeud;
import config.Configuration;
import config.GameFactory;
import config.TrapFactory;
import dao.StatistiqueDAO;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Jan 26, 2017
 */
public class Main {

	private final static GameFactory GAME = new TrapFactory();
	private final static long TEMPS = Configuration.getInstance().getTemps();

	public static void main(String[] args) {
		for(int i = 0 ; i < 50 ; i++) {
			jouer(args);
		}
	}

	public static void jouer(String[] args) {
		// on lance le jeu
		GAME.jouer(TEMPS, new PWidening());		
	}

	public static void mcts(Etat etat, long temps, FormuleSelection strategie,  FormuleSelection st) {
		long tic, toc;
		// Creer l'arbre de recherche
		Noeud racine = GAME.getNoeud(etat);

		MCTS mcts = new MCTSPW( strategie ); // On execute l'algorithme

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
		} while (toc < (tic + temps));

		System.out.println("");
		System.out.println("Itérations effectuées : " + iter);
		//racine.afficherStatistiques();
		/* 
		 * fin de l'algorithme		
		 * On choisit la bonne strategie demandée par l'utilisateur
		 */
		strategie = st;

		//System.out.println("\nSelection...");
		Noeud enfant = strategie.selectionner(racine);
		//System.out.println("Node selected !");
		//System.out.println("Ce Noeud a " + racine.retournerNbSimulation() + " simulations");
		etat.jouerAction(enfant.getAction());
		
		StatistiqueDAO.getInstance().ecrire(temps, 
				PWidening.C, 
				PWidening.alpha, 
				iter,
				racine,
				enfant);
	}

}
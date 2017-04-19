/**
 * 
 */
package main;

import algorithm.MCTS;
import algorithm.MCTSPW;
import game.trap.TrapState;
import tree.node.State;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 19, 2017
 */
public class Main {

	// 500 ms
	private final static long TEMPS = 500;
	
	public static void main(String[] args) {
		execute( new TrapState() );
	}
	
	private static void execute(State s) {
		State root = s;
		MCTS mcts = new MCTSPW( );
		int iter = 0;
		long toc,
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
			root = mcts.execute( root );
			toc = System.currentTimeMillis();
			iter++;
		} while (toc < (tic + TEMPS));
		
		System.out.println(iter + " iterations done !");
	}

}

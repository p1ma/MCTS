package config;


import algorithme.MCTS;
import algorithme.MCTSPW;
import algorithme.formule.FormuleSelection;
import arbre.Etat;
import arbre.Etat.FinDePartie;
import arbre.Noeud;
import jeu.trapProblem.EtatTrap;
import jeu.trapProblem.NoeudTrap;

public class TrapFactory extends GameFactory {

	@Override
	public Etat getEtat(int joueur) {
		return new EtatTrap();
	}

	@Override
	public Etat getEtat() {
		return new EtatTrap();
	}

	@Override
	public MCTS getMCTS() {
		return new MCTSPW();
	}

	@Override
	public Noeud getNoeud(Etat etat) {
		return new NoeudTrap(etat);
	}

	@Override
	public void jouer(
			FormuleSelection strategie,
			FormuleSelection selectionFinale,
			boolean output) {
		// La partie n'est pas terminée
		FinDePartie fin = FinDePartie.NON;

		// initialisation
		Etat etat = null;
		MCTS mcts = null;

		// affectation
		etat = getEtat();
		mcts = getMCTS();


		if (output) { 
			// affichage du temps de réflexion accordé à l'algorithme
			System.out.println("Temps de réflexion de l'ordinateur : " + TEMPS + " msecs");

			// affichage du jeu à l'état initial
			etat.afficherJeu();
		}

		// boucle de décision
		do {
			System.out.println("Position : " + (double)etat.getPosition());
			// execution de MCTS
			mcts(etat, strategie, selectionFinale, mcts, output);
			System.exit(1);
			// affichage de l'état du jeu (ou non si output = false)
			if (output) { etat.afficherJeu(); }

			// on verifie si on le jeu est terminé
			fin = etat.testFin();
		} while (fin == FinDePartie.NON);

		// Le jeu est fini
		if (output) {
			System.out.println("Fin de partie.");
		}
	}
}

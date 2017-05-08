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
			FormuleSelection selectionFinale) {
		// La partie n'est pas terminée
		FinDePartie fin = FinDePartie.NON;

		// initialisation
		Etat etat = null;
		MCTS mcts = null;

		// affectation
		etat = getEtat();
		mcts = getMCTS();

		System.out.println("Temps de réflexion de l'ordinateur : " + (TEMPS / 1000.0) + "s");

		// affichage du jeu à l'état initial
		etat.afficherJeu();

		// boucle de décision
		do {
			mcts(etat, strategie, selectionFinale, mcts);

			etat.afficherJeu();
			fin = etat.testFin();
		} while (fin == FinDePartie.NON);
		System.out.println("Fin de partie.");
	}
}

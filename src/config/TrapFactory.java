package config;


import algorithme.formule.FormuleSelection;
import arbre.Etat;
import arbre.Etat.FinDePartie;
import arbre.Noeud;
import jeu.trapProblem.EtatTrap;
import jeu.trapProblem.NoeudTrap;
import main.Main;

public class TrapFactory implements GameFactory {

	public Etat getEtat(int joueur) {
		return new EtatTrap();
	}

	public Noeud getNoeud(Etat etat) {
		Noeud noeud = new NoeudTrap(etat) ;
		return noeud;
	}

	public void jouer(long temps, FormuleSelection strategie) {
		FinDePartie fin = FinDePartie.NON;

		// initialisation
		Etat etat = null;

		int joueur = Etat.HUMAIN;
		etat = getEtat(joueur);

		System.out.println("Temps de réflexion de l'ordinateur : " + (temps / 1000.0) + "s");


		// boucle de jeu
		etat.afficherJeu();
		do {
			
			Main.mcts(etat, temps, strategie);
			etat.afficherJeu();

			fin = etat.testFin();
		} while (fin == FinDePartie.NON);
		System.out.println("Fin de partie.");
	}

}

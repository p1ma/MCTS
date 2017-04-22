/**
 * 
 */
package algorithme;

import algorithme.formule.FormuleSelection;
import arbre.Noeud;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 22, 2017
 */
public class MCTSPW extends MCTS {

	/**
	 * Constructs a MCTSPW.java with the given parameter(s)
	 * @param fs
	 */
	public MCTSPW(FormuleSelection fs) {
		super(fs);
	}

	@Override
	public Noeud executer(Noeud noeud) {
		/* 1. On simule la fin de partie avec une démarche aléatoire */
		Noeud terminal = simuler(noeud);

		/* 2. On mets a jour le meilleur enfant */
		Noeud meilleurChoix = mettreAJour(noeud, terminal.resultat());

		// On retourne ensuite le noeud initial
		return meilleurChoix;
	}
	
	/* 2. */
	private Noeud developper(Noeud noeud) {
		return formule.selectionner(noeud);
	}

	/* 3. */
	private Noeud simuler(Noeud noeud) {
		Noeud simulation = noeud;
		
		while( !simulation.estTerminal() ) {
			simulation = developper(simulation);
		}
		return simulation;
	}

	/* 4. */
	private Noeud mettreAJour(Noeud noeud, double recompense) {
		
		while( !noeud.estRacine() ) {
			noeud.setStatistique(noeud.retournerNbSimulation(), 
					noeud.retournerRecompense() + recompense);
			noeud = noeud.predecesseur();
		}
		return noeud;
	}
}

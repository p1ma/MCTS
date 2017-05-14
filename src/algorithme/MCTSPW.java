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
	
	public MCTSPW() {
		super();
	}

	@Override
	public Noeud executer(Noeud noeud) {

		/* 1. On simule la fin de partie avec une démarche aléatoire */
		noeud = simuler(noeud);

		/* 2. On mets a jour le meilleur enfant */
		noeud = mettreAJour(noeud, noeud.resultat());

		/* On retourne ensuite le noeud initial */
		return noeud;
	}
	
	/* 2. */
	private Noeud developper(Noeud noeud) {
		return formule.selectionner(noeud);
	}

	/* 1. */
	private Noeud simuler(Noeud noeud) {
		Noeud simulation = noeud;
		
		while( !simulation.estTerminal() ) { 		
			simulation = developper(simulation);
		}
		simulation.visiter();
		return simulation;
	}

	/* 3. */
	private Noeud mettreAJour(Noeud noeud, double recompense) {

		while( !noeud.estRacine() ) {
			// On augmente le reward des noeuds precedents
			noeud.visiter( recompense );
			
			// On recupere le noeud parent
			noeud = noeud.predecesseur();
		}
		return noeud;
	}
}

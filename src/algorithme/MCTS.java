/**
 * 
 */
package algorithme;

import algorithme.formule.FormuleSelection;
import arbre.Noeud;
import arbre.NoeudContinue;
import arbre.NoeudDiscret;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 22, 2017
 */
public abstract class MCTS {

	protected FormuleSelection formule;
	
	public MCTS(FormuleSelection fs) {
		formule = fs;
	}

	public abstract Noeud executer(NoeudContinue noeud) throws Exception;
	public abstract Noeud executer(NoeudDiscret noeud) throws Exception;
}

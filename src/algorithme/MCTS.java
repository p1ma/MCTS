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
public abstract class MCTS {

	protected FormuleSelection formule;
	
	public MCTS(FormuleSelection fs) {
		formule = fs;
	}

	public abstract Noeud executer(Noeud noeud);
}

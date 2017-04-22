/**
 * 
 */
package algorithme.formule;

import arbre.Noeud;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Apr 22, 2017
 */
public class Maxi implements FormuleSelection {

	public Noeud selectionner(Noeud noeud) {
		int k = 0;
		double maxi = Double.MIN_VALUE;
		int indice = 0;
		double value = 0;
		int enfants = noeud.retournerNbEnfant();
		System.out.println("MAXI sur " + enfants + " noeuds");
		while(k < enfants ) {
			value = noeud.retournerEnfant(k).resultat();
			if (value > maxi) {
				maxi = value;
				indice = k;
			}
			
			if (value == 100.0) {
				return noeud.retournerEnfant(k);
			}
			k++;
		}
		return noeud.retournerEnfant(indice);
	}
}

package algorithme.formule;

import arbre.Noeud;

public class Robuste implements FormuleSelection {

	public Noeud selectionner(Noeud noeud) {
		int k = 0;
		int maxi = Integer.MIN_VALUE;
		int indice = 0;
		int value = 0;
		int enfants = noeud.nbEnfant();
		
		System.out.println("(ROBUSTE) sur " + enfants + " noeuds");
		while(k < enfants ) {
			value = noeud.retournerEnfant(k).nbSimulation();
			System.out.println("Noeud num " 
			+ k 
			+ " - position " + (double)noeud.retournerEnfant(k).getEtat().getPosition()
			+ " possède " + value + " simulations");
			if (value > maxi) {
				maxi = value;
				indice = k;
			}
			k++;
		}
		return noeud.retournerEnfant(indice);
	}
	
	public String toString() {
		return "Robuste";
	}

}

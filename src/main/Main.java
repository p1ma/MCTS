package main;

import algorithme.formule.DPWidening;
import algorithme.formule.PWidening;
import algorithme.formule.Robuste;
import config.TrapFactory;

/**
 * @author JUNGES Pierre-Marie - M1 Informatique 2016/2017
 *
 * Jan 26, 2017
 */
public class Main {

	public static void main(String[] args) {
		// si on desire des affichages sur la sortie std
		boolean output = true;
		
		for(int i = 0 ; i < 1 ; i++) {
			// continu avec PWidening
			/*new TrapFactory().jouer(new PWidening(), 
					new Robuste(), output);*/

			// continu avec DPWidening
			new TrapFactory().jouer(new DPWidening(), 
					 new Robuste(), output);

		}
	}
}
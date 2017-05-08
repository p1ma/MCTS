package main;

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
		for(int i = 0 ; i < 1 ; i++) {
			// continu avec PWidening
			new TrapFactory().jouer(new PWidening(), 
					new Robuste());

			// continu avec DPWidening
			/*new TrapFactory().jouer(new DPWidening(), 
					 new Robuste());*/
			
			//discret avec P4
			/*new Puissance4Factory().jouer(new Uct(),
			 new Robuste()); */
		}
	}
}
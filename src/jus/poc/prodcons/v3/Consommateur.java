package jus.poc.prodcons.v3;

import java.util.Random;

public class Consommateur extends Thread {
	
	Random r;
	int m_delay;
	ProdConsBuffer m_buffer;
	
	public Consommateur(ProdConsBuffer buffer, int consTime) {
		
		r = new Random();
		m_delay = consTime;
		m_buffer = buffer;		
	}
	
	@Override
    public void run() {
		int rDelay = 0;
		
		/* Tant que l'on a pas produit tous nos messages */
		while (true) {
			try {
				/* Calcul de l'attente et attente*/
				rDelay = (int) r.nextGaussian() + m_delay;
				Thread.sleep(rDelay);
				/* On commute pour laisser les autres threads s'executer */
				yield();
				
				/* Lecture ! */
				Message m = m_buffer.get();
				
				System.out.println("Le consommateur (" + this.getId() + ") a lu le message : " + m.hashCode());
				
				
				
			} catch (InterruptedException e) { e.printStackTrace(); }
			
			
		}
		
	}




}

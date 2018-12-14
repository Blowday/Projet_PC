package jus.poc.prodcons.v4;

import java.util.Random;

public class Consommateur extends Thread {
	
	Random r;
	int m_delay;
	ProdConsBuffer m_buffer;
	int m_N;
	
	public Consommateur(ProdConsBuffer buffer, int consTime, int N) {
		
		r = new Random();
		m_delay = consTime;
		m_buffer = buffer;
		m_N = N;
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
				
				int N = (int) r.nextGaussian() + m_N;
				
				m_buffer.lectureLock();
				
				for(int i=0; i<N; i++) {
					/* Lecture ! */
					Message m = m_buffer.get();
					System.out.println("Le consommateur (" + this.getId() + ") a lu un exemplaire du message : " + m.hashCode());
				}
				
				m_buffer.lectureUnlock();
				
				
			} catch (InterruptedException e) { e.printStackTrace(); }
			
			
		}
		
	}




}

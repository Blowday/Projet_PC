package jus.poc.prodcons.v4;
import java.util.Random;

public class Producteur extends Thread {
	
	Random r;
	int m_msg;
	int m_delay;
	int m_Navg;
	ProdConsBuffer m_buffer;
	int m_nbC;
	
	
	public Producteur(ProdConsBuffer buffer, int Mavg, int prodTime, int Navg, int nbC) {
		
		r = new Random();
		m_msg = (int) r.nextGaussian() + Mavg;
		m_delay = prodTime;
		m_buffer = buffer;
		m_Navg = Navg;
		m_nbC = nbC;
	}
	
	@Override
    public void run() {
		/* Messages produits */
		int nbMessages = 0;
		int rDelay = 0;
		
		/* Tant que l'on a pas produit tous nos messages */
		while (nbMessages <= m_msg) {
			try {
				/* Calcul de l'attente et attente*/
				rDelay = (int) r.nextGaussian() + m_delay;
				Thread.sleep(rDelay);
				/* On commute pour laisser les autres threads s'executer */
				yield();
				
				int N;
				do {
					N = (int) r.nextGaussian() + m_Navg;
				}while(N >= m_nbC || N <= 0);
				
				/* Production time ! */
				Message m = new Message(this.getId(), N);
				
				System.out.println("Le producteur (" + this.getId() + ") a produit le message : " + m.hashCode()
				+" en " + N + " exemplaires");
				
				/* On place le message dans le buffer */
				m_buffer.put(m);
				
				/* On met à jour notre compteur */
				nbMessages++;
				
			} catch (InterruptedException e) { e.printStackTrace(); }
			
			
		}
		
		
		
		
		
	}

}

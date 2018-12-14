package jus.poc.prodcons.v1;
import java.util.Random;

public class Producteur extends Thread {
	
	Random r;
	int m_msg;
	int m_delay;
	ProdConsBuffer m_buffer;
	
	public Producteur(ProdConsBuffer buffer, int avg, int prodTime) {
		
		r = new Random();
		m_msg = (int) r.nextGaussian() + avg;
		m_delay = prodTime;
		m_buffer = buffer;		
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
				
				/* Production time ! */
				Message m = new Message(this.getId());
				
				System.out.println("Le producteur (" + this.getId() + ") a produit le message : " + m.hashCode());
				
				/* On place le message dans le buffer */
				m_buffer.put(m);
				
				/* On met à jour notre compteur */
				nbMessages++;
				
			} catch (InterruptedException e) { e.printStackTrace(); }
			
			
		}
		
		
		
		
		
	}

}

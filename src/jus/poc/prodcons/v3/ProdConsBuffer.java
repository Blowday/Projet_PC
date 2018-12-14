package jus.poc.prodcons.v3;

import java.util.Arrays;

/* Notre implémentations de IProdConsBuffer utilisant la solution directe
 */
public class ProdConsBuffer implements IProdConsBuffer {
	
	/* Le buffer sera FIFO vers la gauche, CàD que pour lire on prendra depuis la gauche,
	 * et on écrira vers la droite. Lors de la lecture, on décalera les éléments vers la gauche.
	 */
	private Message[] m_buffer;
	private int m_size;
	
	public ProdConsBuffer(int size) {
		m_size = size;
		m_buffer = new Message[m_size];
		Arrays.fill(m_buffer, null);
	}
	
	@Override
	public synchronized void put(Message m) throws InterruptedException {
		/* Si le buffer est plein, on attend */
		while(nmsg() == m_size) {
			wait();
		}
		
		/* On déplace le curseur i au bon endroit dans le buffer 
		 * et on ajouter le message donné. */
		int i=nmsg();
		m_buffer[i] = m;
		
		//System.out.println("Un message a été déposé sur le buffer !");
		System.out.println(this);
		
		/* On préviens les threads en attente */
		notifyAll();
		while(m.restant() != 0) {
			wait();
		}
	}

	@Override
	public synchronized Message get() throws InterruptedException {
		/* Si le buffer est vide, on attend */
		while (nmsg() == 0) {
			wait();
		}
		
		/* On lit le premier message */
		Message m = m_buffer[0];
		
		/* Si c'était le dernier exemplaire de ce message, on le retire et 
		 * on décale tous les autres vers la gauche
		 */
		if(m.decrementN() == true) {
			for(int i=1; i<m_size; i++) {
				m_buffer[i-1] = m_buffer[i];
			}
			m_buffer[m_size-1] = null;
		}
		
		//System.out.println("Un message a été lu depuis le buffer !");
		System.out.println(this);
		
		/* On préviens les threads en attente */
		notifyAll();
		
		while(m.restant() != 0) {
			wait();
		}
		
		return m;
		
	}

	@Override
	public int nmsg() {
		int i=0;
		while(i<m_size && m_buffer[i]!= null) {
			i++;
		}
		return i;
	}
	
	/* Fonction d'écriture du contenu du buffer */
	@Override
    public String toString() {
        String message = ("\n ---------Buffer updated---------\n");
        message       += ("Buffer contains " + nmsg() + " message(s)\n");
        message       += ("Buffer content : { ");
        for (int i = 0; i < m_size; i++) {
            message += (m_buffer[i] + " ");
        }
        message       += ("}\n --------------------------------\n");
        return message;
    }

}

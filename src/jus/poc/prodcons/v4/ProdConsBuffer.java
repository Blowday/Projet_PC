package jus.poc.prodcons.v4;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

/* Notre implémentations de IProdConsBuffer utilisant la solution directe
 */
public class ProdConsBuffer implements IProdConsBuffer {
	
	/* Le buffer sera FIFO vers la gauche, CàD que pour lire on prendra depuis la gauche,
	 * et on écrira vers la droite. Lors de la lecture, on décalera les éléments vers la gauche.
	 */
	private Message[] m_buffer;
	private int m_size;
	private Semaphore m_prodSem;
    private Semaphore m_consSem;
    private Semaphore m_lectureSem;
	
	public ProdConsBuffer(int size) {
		m_size = size;
		m_buffer = new Message[m_size];
		Arrays.fill(m_buffer, null);
		m_prodSem = new Semaphore(m_size);
		m_consSem = new Semaphore(0);
		m_lectureSem = new Semaphore(1);
	}
	
	public void lectureLock() {
		try {
			m_lectureSem.acquire();
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public void lectureUnlock() {
		m_lectureSem.release();
	}
	
	@Override
	public void put(Message m) throws InterruptedException {
		/* On demande un "permis" si le buffer n'est pas plein */
		m_prodSem.acquire();
		
		/* On déplace le curseur i au bon endroit dans le buffer 
		 * et on ajouter le message donné. */
		int i=nmsg();
		m_buffer[i] = m;
		
		//System.out.println("Un message a été déposé sur le buffer !");
		System.out.println(this);
		
		/* On rend un permis aux consommateurs */
		m_consSem.release();
	}

	@Override
	public synchronized Message get() throws InterruptedException {
		/* On demande un permis s'il y a quelque chose à lire */
		m_consSem.acquire();
		
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
			
			/* On libère un permis aux producteurs */
			m_prodSem.release();
		}else {
			/* Sinon, on libère un autre consommateur pour consommer  (car le message est toujours lisible,
			 * il reste des exemplaires) */
			m_consSem.release();
		}
		
		//System.out.println("Un message a été lu depuis le buffer !");
		System.out.println(this);
		
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

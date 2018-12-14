package jus.poc.prodcons.v3;

/* Classe message à implémenter
 * Choisir un message utile pour aider à débuguer
 */
public class Message {
	
	long m_id;
	int m_N;
	public Message(long id, int N) {
		m_id = id;
		m_N = N;
	}
	
	public boolean decrementN() {
		m_N--;
		return m_N == 0;
	}
	
	public int restant() {
		return m_N;
	}
	
	@Override
    public String toString() {
        return "[ " + m_id + " - Message : " + this.hashCode() + " reste : " + m_N + " exemplaires ]";
    }
	
}

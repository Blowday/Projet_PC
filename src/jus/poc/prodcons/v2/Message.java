package jus.poc.prodcons.v2;

/* Classe message à implémenter
 * Choisir un message utile pour aider à débuguer
 */
public class Message {
	
	long m_id;
	public Message(long id) {
		m_id = id;
	}
	
	@Override
    public String toString() {
        return "[ " + m_id + " - Message : " + this.hashCode() + " ]";
    }
	
}

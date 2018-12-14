package jus.poc.prodcons.v4;

/* Interface contenant le "squelette" que doit respecter nos implémentations.
 * On doit donc au minimum implémenter ces trois fonctions en respectants leurs spécifications.
 */
public interface IProdConsBuffer {

	/**
	* put m in the prodcons buffer
	**/
	public void put(Message m) throws InterruptedException;
	
	/**
	* retrieve a message from the prodcons buffer, following a fifo order
	**/
	public Message get() throws InterruptedException;
	
	/**
	* returns the number of messages currently available in the prodcons buffer
	**/
	public int nmsg();
	
}

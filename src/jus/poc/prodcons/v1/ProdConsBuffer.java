package jus.poc.prodcons.v1;

/* Notre implémentations de IProdConsBuffer utilisant la solution directe
 */
public class ProdConsBuffer implements IProdConsBuffer {
	
	/* Buffer implémenté avec un tableau. A voir comment
	 * On échange des Messages (que je dois implémenter)
	 * Utiliser wait/notify de java dans cette version
	 * 
	 */
	
	
	@Override
	public void put(Message m) throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public Message get() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nmsg() {
		// TODO Auto-generated method stub
		return 0;
	}

}

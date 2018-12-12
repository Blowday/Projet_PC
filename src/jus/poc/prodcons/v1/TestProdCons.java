package jus.poc.prodcons.v1;

import java.io.IOException;
import java.util.Properties;

/* Classe de test, qui va se servir d'implémentations de ProdConsBuffer
 * pour les tester. Elle connait les paramètres d'executions. classe "main".
 */
public class TestProdCons {

	public static void main(String[] args) throws IOException {
		
		Properties properties = new Properties();
		properties.loadFromXML(
				TestProdCons.class.getClassLoader().getResourceAsStream("options.xml"));
		
		int nbP = Integer.parseInt(properties.getProperty("nbP"));
		int nbC = Integer.parseInt(properties.getProperty("nbC"));
		int BufSz = Integer.parseInt(properties.getProperty("BufSz"));
		int ProdTime = Integer.parseInt(properties.getProperty("ProdTime"));
		int ConsTime = Integer.parseInt(properties.getProperty("ConsTime"));
		int Mavg = Integer.parseInt(properties.getProperty("Mavg"));
		
		System.out.println(nbP + "  " + nbC);
		
		
	}

}

package jus.poc.prodcons.v3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
		int Navg = Integer.parseInt(properties.getProperty("Navg"));
		
		/* Création du buffer */
		ProdConsBuffer m_buffer = new ProdConsBuffer(BufSz);
		
		/* Je crée deux listes, une pour stocker les producteurs et les consommateurs
		 * ensembles, afin de pouvoir les mélanger et les executer de manière aléatoire.
		 * L'autre servira à stocker seulement les producteurs afin de pouvoir vérifier
		 * s'ils ont terminés de produire.
		 */
		ArrayList<Thread> prodConsEnsemble = new ArrayList<>();
		ArrayList<Thread> producteurs = new ArrayList<>();
		
		// On crée nbP Producteurs
		for(int i=0; i<nbP; i++) {
			Producteur p = new Producteur(m_buffer, Mavg, ProdTime, Navg, nbC);
			prodConsEnsemble.add(p);
			producteurs.add(p);
		}
		
		// On crée nbC Consommateurs
		for(int i=0; i<nbC; i++) {
			Consommateur c = new Consommateur(m_buffer, ConsTime);
			/* Les consommateurs sont des deamons, cad qu'ils n'empêchent pas le programme
			 * de se terminer, (ils sont dans des boucles while infinie).
			 */
			c.setDaemon(true);
			prodConsEnsemble.add(c);
		}
		
		// On mélange la liste des producteurs et des consommateurs ensembles
        Collections.shuffle(prodConsEnsemble);
        
        // On commence la mesure juste avant le départ de la simulation
        long tempsDebut = System.currentTimeMillis();

        // On lance leurs executions, de manière aléatoire donc
        prodConsEnsemble.forEach(pc -> pc.start());
		
		// On attend que les producteurs aient fini de produire
		producteurs.forEach(p -> {
            try { p.join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        });

		// Puis que les consommateurs aient fini de consommer
        while(m_buffer.nmsg() > 0);
        
        // fin de la mesure et affichage du résultat
        long tempsFin = System.currentTimeMillis();
        long tempsSimu = tempsFin - tempsDebut;
        
        // On attend juste un peu pour que les consommateurs aient fini de traiter le dernier message
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("\nSimulation réalisée en : " + tempsSimu + " millisecondes avec la version 3.");
		
		// fin du programme
		
	}

}

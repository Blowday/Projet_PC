package jus.poc.prodcons.v1;

import java.io.IOException;
import java.util.Properties;

public class TestProdCons {

	public static void main(String[] args) throws IOException {
		
		Properties properties = new Properties();
		properties.loadFromXML(
				TestProdCons.class.getClassLoader().getResourceAsStream("options.xml"));
		
		int nbP = Integer.parseInt(properties.getProperty("nbP"));
		int nbC = Integer.parseInt(properties.getProperty("nbC"));
		
		System.out.println(nbP + "  " + nbC);
	}

}

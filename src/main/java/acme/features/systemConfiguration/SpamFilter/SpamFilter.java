package acme.features.systemConfiguration.SpamFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpamFilter {

	private static List<String> strongSpamTerms = new ArrayList<>();
	private static List<String> weakSpamTerms = new ArrayList<>();
	private static List<String> strongCompoundTerms = new ArrayList<>();
	private static List<String> weakCompoundTerms = new ArrayList<>();
	private static double weakThreshold;
	private static double strongThreshold;
	
	public static void initFilter(final String strongTerms, final String weakTerms, final double strongThreshold, final double weakThreshold) {
		SpamFilter.strongThreshold = strongThreshold;
		SpamFilter.weakThreshold = weakThreshold;
		
		for(final String spamTerm : strongTerms.split(";")) {
			if(spamTerm.contains(" ")) {
				SpamFilter.strongCompoundTerms.add(spamTerm);
			} else {
				SpamFilter.strongSpamTerms.add(spamTerm);
			}
		}
		
		for(final String spamTerm : weakTerms.split(";")) {
			if(spamTerm.contains(" ")) {
				SpamFilter.weakCompoundTerms.add(spamTerm);
			} else {
				SpamFilter.weakSpamTerms.add(spamTerm);
			}
		}
	}

	public static boolean isSpam (final String cadenaConEspacios) {

		String cadena = SpamFilter.unionEspacios(cadenaConEspacios);
		
		final boolean weakSpam = SpamFilter.comprobacionPalabras(cadena, SpamFilter.weakSpamTerms) + SpamFilter.comprobacionPalabrasCompuestas(cadena, SpamFilter.weakCompoundTerms) > SpamFilter.weakThreshold;
		final boolean strongSpam = SpamFilter.comprobacionPalabras(cadena, SpamFilter.strongSpamTerms) + SpamFilter.comprobacionPalabrasCompuestas(cadena, SpamFilter.strongCompoundTerms) > SpamFilter.strongThreshold;
		return  weakSpam || strongSpam;
	}
	
	private static double comprobacionPalabras(final String cadena, final List<String> spamTerms) {
		final int LonCadena = cadena.split(" ").length;
		int acum = 0;
		for (String termino : cadena.split(" ")) {
			termino = termino.toLowerCase();
			termino = termino.trim();
			if(spamTerms.contains(termino)) {
				acum++;
			}
		}
		return ((double) acum/LonCadena)*100;
	}
	
	private static double comprobacionPalabrasCompuestas(final String cadena, final List<String> spamTerms) {
		final int LonCadena = cadena.split(" ").length;
		int acum = 0;
	    final List<String> terminos = Arrays.asList(cadena.split(" "));
		for (int i=0; i<LonCadena-1; i++) {
			String termino = terminos.get(i) + " " + terminos.get(i+1);
			termino = termino.toLowerCase();
			if(spamTerms.contains(termino)) {
				acum++;
			}
		}
		return ((double) acum/LonCadena)*100;
	}



	private static String unionEspacios(String cadena) {
		String espacio = " ";
		String concatenacion = "";
		Integer LonCadena = cadena.length();
		if(((Character) cadena.charAt(0)).compareTo((Character)espacio.charAt(0))==0) {
			for(int i = 1; i<LonCadena; i++) {
				Character caracter = cadena.charAt(i);
				if(caracter.compareTo(espacio.charAt(0))!=0 || (!(caracter.compareTo(cadena.charAt(i-1))==0) && i!=0)) {
					concatenacion = concatenacion.concat(caracter.toString());
				}
			}
		}else {
			for(int i = 0; i<LonCadena; i++) {
				Character caracter = cadena.charAt(i);
				if(caracter.compareTo(espacio.charAt(0))!=0 || (!(caracter.compareTo(cadena.charAt(i-1))==0) && i!=0)) {
					concatenacion = concatenacion.concat(caracter.toString());
				}
			}
		}
		return concatenacion;
	}
}

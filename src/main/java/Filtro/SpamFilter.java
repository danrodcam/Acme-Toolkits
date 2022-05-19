package Filtro;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpamFilter {

	private static List<String> strongSpamTerms = new ArrayList<>();
	private static List<String> weakSpamTerms = new ArrayList<>();
	private static double weakThreshold;
	private static double strongThreshold;
	
	public static void initFilter(final String strongTerms, final String weakTerms, final double strongThreshold, final double weakThreshold) {
		SpamFilter.strongThreshold = strongThreshold;
		SpamFilter.weakThreshold = weakThreshold;
		
		for(final String spamTerm : strongTerms.split(";")) {
			SpamFilter.strongSpamTerms.add(spamTerm);
		}
		
		for(final String spamTerm : weakTerms.split(";")) {
			SpamFilter.weakSpamTerms.add(spamTerm);
		}
	}
	
	private static String regexCompilator(final String wordList) {
		final StringBuilder builder = new StringBuilder();
		builder.append("(");
		final String[] spamWords = wordList.split(",");

		for (int i = 0; i < spamWords.length; i++) {

			if (i != spamWords.length - 1) { //si no es la ultima palabra, se pone un | 
				final String word = spamWords[i].trim();
				for (int j = 0; j < word.length(); j++) {
					if (word.charAt(j) == ' ') {
						builder.append(word.charAt(j));
					} else {
						builder.append(word.charAt(j) + "+");
					}
				}
				builder.append("|");
			} else { //si es la última palabra, no se pone un |
				final String word = spamWords[i].trim();
				for (int j = 0; j < word.length(); j++) {
					if (word.charAt(j) == ' ') {
						builder.append(word.charAt(j));
					} else {
						builder.append(word.charAt(j) + "+");
					}
				}
			}
		}
		builder.append(")");

		return builder.toString().trim();
	}
	
	

	public static boolean isSpam (final String cadena) {
		
		final boolean weakSpam = SpamFilter.comprobacionPalabras(cadena, SpamFilter.weakSpamTerms) > SpamFilter.weakThreshold;
		final boolean strongSpam = SpamFilter.comprobacionPalabras(cadena, SpamFilter.strongSpamTerms) > SpamFilter.strongThreshold;
		return  weakSpam || strongSpam;
	}
	
	private static double comprobacionPalabras(final String cadena, final List<String> spamTerms) {
		Double similarity = 0.0;
		
		String fullMessage = String.join(" ", cadena).toLowerCase();
		fullMessage = fullMessage.replaceAll("\\R", " ");
		fullMessage = fullMessage.replaceAll("( )+", " ");
		
		double numSpamWords = 0.0;
		for(int i=0; i<spamTerms.size(); i++) {
		
			final String wordList = spamTerms.get(i).toLowerCase();
			final Pattern p = Pattern.compile(regexCompilator(wordList));
			final Matcher m = p.matcher(fullMessage);
			while (m.find()) {
				if(wordList.contains(" ")) {
					numSpamWords = numSpamWords+2;
				}
				else {
				numSpamWords++;
				}
			}
		}
		if (numSpamWords != 0) {
			final int numTotalWords = fullMessage.trim().split("\\s+").length;
			similarity = numSpamWords / numTotalWords;
		}
		else {
			similarity = 0.0;
		}
		return similarity;
	}
	
	public static void main (String args[]) {
		String strong = "sex;hard core;viagra;cialis;sexo";
		String weak = "sexy;nigeria;you've won;one million;has ganado;un millon";
		String cadena = "hoy he conseguido one million de euros y los he cogido y me los he gastado en putas"
				+ "y despues me he ido a echar una ps4 con mis colegas y unos porros";
		String cadena1 = "one      "
				+ "million";
		SpamFilter.initFilter(strong, weak, 0.1, 0.25);
		System.out.println(SpamFilter.isSpam(cadena));
		System.out.println(SpamFilter.isSpam(cadena));
		System.out.println(SpamFilter.isSpam(cadena));

		
	}
}
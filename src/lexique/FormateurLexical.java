package lexique;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.regex.Pattern;

import phonetiseur.RegExp;

/** A partir d'un lexique composé seulement de l'écriture des lexemes, elle formate le lexique pour qu'il y figure également la prononciation et le contexte phonétique pertinent.*/

class FormateurLexical {
	
	public static void formaterFichier(String nameFic) throws IOException {
		
		LinkedList<String> lignes = new LinkedList<String>();
		// fichier dans lequel on ecrit
		PrintWriter writer = new PrintWriter(Dossier.PATH+"Categories/" + nameFic + ".cat",
				"UTF-8");
		// chargement des fichiers
		File fic = new File(Dossier.PATH+"NonFormates/" + nameFic);
		FileInputStream is = new FileInputStream(fic);
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String readLine = "";
		while ((readLine = br.readLine()) != null) {
			lignes.add(readLine);
		}
		isr.close();
		// lecture et analyse de mots en lignes completes
		LinkedList<ChaineLexicale> lignesCompletes = new LinkedList<ChaineLexicale>();
		Pattern regExpDescriptionCompletes = Pattern.compile("\\["
				+ RegExp.regExPhone + "+\\]	\\_\\[" + RegExp.regExPhone
				+ "+\\]");

		double nbLignes = lignes.size();
		double dizaines = nbLignes / 100;
		int i = 0;
		int countPourcent = 1;
		for (String l : lignes) {
			try {
				if (i > dizaines * countPourcent) {
					System.out.println("	" + (countPourcent / dizaines)*100 + "%");
					countPourcent++;
				}
				i++;
				int indexOfTab = l.indexOf('\t');
				boolean plusQuUnSeulMot = indexOfTab != -1;
				// si le mot est déjà formatté on le laisse
				if (plusQuUnSeulMot
						&& regExpDescriptionCompletes.matcher(
								l.substring(indexOfTab + 1)).matches()) {
					ChaineLexicale mot = new ChaineLexicale(l);
					lignesCompletes.add(mot);
					writer.println(mot.toLexicalString());
					continue;
				}
				/*
				 * eventuellement on rentre un mot avec son contexte sans sa
				 * prononciation??? //Pattern regExpDescriptionContextextuelle =
				 * Pattern.compile("\\_\\[" + RegExp.regExPhone + "+\\]"); else
				 * if(plusQuUnSeulMot &&
				 * regExpDescriptionContextextuelle.matcher
				 * (l.substring(indexOfTab + 1)).matches()) { String ecriture =
				 * l.substring(0, indexOfTab); String contexte =
				 * l.substring(indexOfTab+2, l.length()-1); }
				 */
				// sinon on le formate
				else {
					LinkedList<ChaineLexicale> mots = ChaineLexicale
							.newChaineLexicale(l);
					for (ChaineLexicale mot : mots) {
						lignesCompletes.add(mot);
						writer.println(mot.toLexicalString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
				System.out.println("probleme à la ligne : " +i+" : "+ l.toString());
			}
		}
		System.out.println("100%");
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		formaterFichier("N_fs");
	}

}

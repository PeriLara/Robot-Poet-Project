package phonetiseur;

import java.util.LinkedList;
import java.util.regex.Matcher;
import lexique.ChaineLexicale;



/** Contient une unique fonction statique qui permet de découper en syllabes une chaine lexicale */
//exemple à gérer : la syllabation avec enchainement de 5 consonnes : [eks|trwa] ("extroyer")
public class Syllabeur {
	
	/**
	 * Découpe en syllabes une chaine lexicale
	 * @param param
	 * @return Une linkedList contenant chaque syllabe
	 */
	public static LinkedList<Syllabe> syllaber(ChaineLexicale param) {
		LinkedList<Syllabe> retour = new LinkedList<Syllabe>();
		
		String 	str = param.getPrononciation().replaceAll("[ ]", "");
		
		int 	i	= 0; 

		Matcher voyelle = RegExp.regExVoyelle.matcher(str);
		Matcher groupeCons = RegExp.regExGroupeNonVocalique.matcher(str);
		boolean foundVoyelle = voyelle.find();
		boolean foundConsonne = groupeCons.find();
		int		v = -1;
		int		c = -1;
		while(foundVoyelle&&foundConsonne){
				v	= voyelle.start();
				c 	= groupeCons.start();
				if(v<c){ //cas voyelle
					try{
						int index = v+1;
						char suiv = str.charAt(index);
						if(RegExp.classeVoyelles.indexOf(suiv)!=-1){//une voyelle la suit	
							String syllabe = str.substring(i, v+1);
							i = v;
							retour.add(new Syllabe(syllabe));
						}
					} catch (Exception e) {
					}
					finally{
						foundVoyelle = voyelle.find();
					}
				}
				else{//cas consonne
					try{
					String 	group = groupeCons.group();
					Matcher triCons = RegExp.regExTriConsonne.matcher(group);
					Matcher biCons = RegExp.regExBiConsonne.matcher(group);
					boolean foundTriCons = triCons.find();
					boolean foundBiCons = biCons.find();
					int 	groupLength = group.length();
					switch(groupLength){
					
					case 1 :
							String syllabe = str.substring(i, c);
							i = c;
							retour.add(new Syllabe(syllabe));
							break;
							
					case 2 :
							if(c == 0){//c'est le debut de la chaine
								syllabe = str.substring(i, c);
								i = c;
								retour.add(new Syllabe(syllabe));
							}
							else{
								int k = 1;
								syllabe = str.substring(i, c+k);
								i = c+k;
								retour.add(new Syllabe(syllabe));
								syllabe = str.substring(i, c);
								i = c;
								retour.add(new Syllabe(syllabe));
							}
							break;
		
					case 3 :
							if(c == 0){//c'est le debut de la chaine
								syllabe = str.substring(i, c);
								i = c;
								retour.add(new Syllabe(syllabe));
							}
							else if(foundBiCons){
								Matcher rightBiCons = RegExp.regExBiConsonne.matcher(group.substring(1));
								boolean foundRightBiCons = rightBiCons.find();
								if(foundRightBiCons){//c|cc
									int k = 1;
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
									}
								else{//cc|c
									int k = 2;
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
									}
							}
							else{//c|c@|c
								syllabe = str.substring(i, c+0);
								i = c+0;
								retour.add(new Syllabe(syllabe));
								syllabe = str.substring(i, c+1);
								i = c+1;
								retour.add(new Syllabe(syllabe+'@'));
								syllabe = str.substring(i, c+2);
								i = c+2;
								retour.add(new Syllabe(syllabe));
							}
							break;		
							
					case 4 :
						if(foundTriCons){//ccc|c ou c|ccc ?
							Matcher rightTriCons = RegExp.regExBiConsonne.matcher(group.substring(1));
							boolean foundRightTriCons = rightTriCons.find();
							if(foundRightTriCons){//c|ccc
								int k = 1;																
								syllabe = str.substring(i, c+k);
								i = c+k;
								retour.add(new Syllabe(syllabe));								
							}
							else{//ccc|c
								int k = 0;syllabe = str.substring(i, c+k);
								i = c+k;
								retour.add(new Syllabe(syllabe));
								}
						}
						else if(foundBiCons){//cc|cc ou c|c@|cc ou cc|c@|c  ou c|cc@|c  ?
							Matcher rightBiCons = RegExp.regExBiConsonne.matcher(group.substring(2));
							boolean foundRightBiCons = rightBiCons.find();
							if(foundRightBiCons){//cc|cc ou c|c@|cc
								if(biCons.start()==0){// cc|cc
									int k = 2;syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
								}
								else{// c|c@|cc
									int k = 2;
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe+'@'));
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
								}
							}
							else{//cc|c@|c  ou c|cc@|c
								if(biCons.start()==0){//cc|c@|c
									int k = 2;
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe+'@'));
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
								}
								else{//c|cc@|c
									int k = 3;
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe+'@'));
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
								}
							}			
						}
						else{//c|c@|c@|c
							syllabe = str.substring(i, c+0);
							i = c+0;
							retour.add(new Syllabe(syllabe));
							syllabe = str.substring(i, c+1);
							i = c+1;
							retour.add(new Syllabe(syllabe+'@'));
							syllabe = str.substring(i, c+2);
							i = c+2;
							retour.add(new Syllabe(syllabe+'@'));
							syllabe = str.substring(i, c+3);
							i = c+3;
							retour.add(new Syllabe(syllabe));
						}
					break;
						
					case 5 :
						if(foundTriCons){//cc|ccc ou c|c@|ccc
							Matcher rightTriCons = RegExp.regExTriConsonne.matcher(group.substring(2));
							boolean foundRightTriCons = rightTriCons.find();
							if(foundRightTriCons){//cc|ccc ou c|c@|ccc
								if(biCons.start()==0){//cc|ccc
									int k = 2;
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
									break;
								}
								else{//c|c@|ccc
									int k = 2;
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe+'@'));
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
									break;
								}
							}
							Matcher middleTriCons = RegExp.regExTriConsonne.matcher(group.substring(1));
							boolean foundMiddleTriCons = middleTriCons.find();
							if(foundMiddleTriCons){// c|ccc@|c
								int k = 4;
								syllabe = str.substring(i, c+k);
								i = c+k;
								retour.add(new Syllabe(syllabe+'@'));
								syllabe = str.substring(i, c+k);
								i = c+k;
								retour.add(new Syllabe(syllabe));
								break;
							}
							Matcher leftTriCons = RegExp.regExTriConsonne.matcher(group);
							boolean foundLeftTriCons = leftTriCons.find();
							if(foundLeftTriCons){// ccc|cc ou ccc|c|c
								if(biCons.start()==0){//cc|ccc
									int k = 2;
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
									break;
								}
								else{//c|c@|ccc
									int k = 2;
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe+'@'));
									syllabe = str.substring(i, c+k);
									i = c+k;
									retour.add(new Syllabe(syllabe));
									break;
								}
							}
							
						}/*
						else if(foundBiCons){//(cc|cc|c ou cc|c|cc ou cc|c|c|c) ou (c|cc|cc ou (c|cc|c|c)) ou (cc|cc|c ou c|c|cc|c) ou (c|c|c|cc)
							Matcher rightBiCons = RegExp.regExBiConsonne.matcher(group.substring(1));
							boolean foundRightBiCons = rightBiCons.find();
							if(foundRightBiCons){// c|cc
								int k = 1;
								String 	groupA = group.substring(0, k);
								
								String 	groupB = group.substring(k);
								
							}
							else{// cc|c
								int k = 2;
								String 	groupA = group.substring(0, k);
								
								String 	groupB = group.substring(k);
								
							}
						}*/
						else{
							int k;
							for(k = 0; k < group.length()-1; k++){
								syllabe = str.substring(i, c+k);
								i = c+k;
								retour.add(new Syllabe(syllabe+'@'));
							}
							break;
						}
						break;
					default :
							throw new Exception("chaine de plus de 5 consonnes !");
						}
					} catch (Exception e) {
					}finally{
						foundConsonne = groupeCons.find();
					}
			}
		}//derniere syllabe
		if(foundVoyelle&&!foundConsonne){//est une voyelle
			String syllabe = str.substring(i);
			try {
				retour.add(new Syllabe(syllabe));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(!foundVoyelle&&foundConsonne){//est une consonne
			try {
				//et la premiere "syllabe"
				if(retour.isEmpty()){
					int k 	= groupeCons.start();
					if(k > 0){
						String syllabe = str.substring(0,k);
						retour.add(new Syllabe(syllabe));
						syllabe = str.substring(k);
						retour.add(new Syllabe(syllabe+'@'));
					}
					else{//une seule consonne
						String syllabe = str.substring(k);
						retour.add(new Syllabe(syllabe+'@'));
					}
					
				}
				else{
					String syllabe = str.substring(i);
					retour.add(new Syllabe(syllabe));
				} 
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return retour;
	}

	//@SuppressWarnings("unused")
	/*public static void main(String[] args) {

		try {
			LinkedList<String> list = new LinkedList<String>();

			list.add("des ours");
			list.add("des chiens");
			list.add("ces ours");
			list.add("ces chiens");
			list.add("un ours");
			list.add("un chiens");
			list.add("une ours");
			list.add("une chiens");
			list.add("étonnée ours");
			list.add("étonnée chiens");

			list.add("des ours");
			list.add("des chiens");
			list.add("ces ours");
			list.add("ces chiens");
			list.add("un ours");
			list.add("un chiens");
			list.add("une ours");
			list.add("une chiens");
			list.add("étonnée ours");
			list.add("étonnée chiens");

			list.add("des ours");
			list.add("des chiens");
			list.add("ces ours");
			list.add("ces chiens");
			list.add("un ours");
			list.add("un chiens");
			list.add("une ours");
			list.add("une chiens");
			list.add("étonnée ours");
			list.add("étonnée chiens");

			list.add("des ours");
			list.add("des chiens");
			list.add("ces ours");
			list.add("ces chiens");
			list.add("un ours");
			list.add("un chiens");
			list.add("une ours");
			list.add("une chiens");
			list.add("étonnée ours");
			list.add("étonnée chiens");

			list.add("des ours");
			list.add("des chiens");
			list.add("ces ours");
			list.add("ces chiens");
			list.add("un ours");
			list.add("un chiens");
			list.add("une ours");
			list.add("une chiens");
			list.add("étonnée ours");
			list.add("étonnée chiens");

			list.add("des ours");
			list.add("des chiens");
			list.add("ces ours");
			list.add("ces chiens");
			list.add("un ours");
			list.add("un chiens");
			list.add("une ours");
			list.add("une chiens");
			list.add("étonnée ours");
			list.add("étonnée chiens");

			list.add("des ours");
			list.add("des chiens");
			list.add("ces ours");
			list.add("ces chiens");
			list.add("un ours");
			list.add("un chiens");
			list.add("une ours");
			list.add("une chiens");
			list.add("étonnée ours");
			list.add("étonnée chiens");

			list.add("des ours");
			list.add("des chiens");
			list.add("ces ours");
			list.add("ces chiens");
			list.add("un ours");
			list.add("un chiens");
			list.add("une ours");
			list.add("une chiens");
			list.add("étonnée ours");
			list.add("étonnée chiens");

			list.add("des ours");
			list.add("des chiens");
			list.add("ces ours");
			list.add("ces chiens");
			list.add("un ours");
			list.add("un chiens");
			list.add("une ours");
			list.add("une chiens");
			list.add("étonnée ours");
			list.add("étonnée chiens");

			list.add("des ours");
			list.add("des chiens");
			list.add("ces ours");
			list.add("ces chiens");
			list.add("un ours");
			list.add("un chiens");
			list.add("une ours");
			list.add("une chiens");
			list.add("étonnée ours");
			list.add("étonnée chiens");

			long startTime = System.nanoTime();
			int i = 0;

			for (String s : list) {
				String s2 = SynthetiseurVocal.synthese(s);
				// System.out.println("s" + i + " : " + s2);
				i++;
			}

			long stopTime = System.nanoTime();
			double elapsedTime = stopTime - startTime;
			double elapsedSecondes = elapsedTime / 1000000000;
			double meanSecond = elapsedSecondes / 100;
			System.out.println("100 éléments analysés en " + elapsedSecondes
					+ " secondes");
			System.out.println("=> moyenne de " + meanSecond
					+ " secondes par éléments");

			// trois stratèges exterieurs s'expliquent, excités
			// treize-trente-trois et demi spécialités blueus en une demi-heure
			// dans un triangle d'une joliesse cristique

			//String str = "trois stratèges extroyés extérieurs s'expliquent, excités treize-trente-trois et demi spécialités blueus en une demi-heure dans un triangle d'une joliesse cristico ";
			String str = "sa chartre scoute tatoue tes pourpres flirteuses" ;
			System.out.println();
			System.out.println(str);
			System.out.println(Syllabeur.syllaber(new ChaineLexicale(str,RegExp.regExPhone)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}*/

}

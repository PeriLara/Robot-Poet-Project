package lexique;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Permet de phonétiser une chaîne littérale grâce à espeaks. Pour le moment cette phonétisation n'est permise que sur un environnement linux.
 */
public class SynthetiseurVocal {

	private static String formater(String str) {
		str = str.substring(1);
		str = str.replaceAll("[!',\\-\\_\\;:]", "");
		str = str.replaceAll("z2", "z"); // espeaks note ainsi les liaisons
		str = str.replaceAll("2", "1");
		str = str.replaceAll("n\\^", "N"); // espeaks note ainsi 'gn' de cognitif
		str = str.replaceAll("A~", "1"); //an
		str = str.replaceAll("E~", "2"); //un
		str = str.replaceAll("O~", "3"); //on
		str = str.replaceAll("W~", "4"); //ein
		str = str.replaceAll("yi", "5i"); //(h)uile
		str = str.replaceAll("\\|", "6"); //(h)aricot
		str = str.replaceAll("W", "7"); //c(oe)ur
		str = str.replaceAll("Y", "@");
		str = str.replaceAll("I", "i");
		str = str.replaceAll("#", "a");
		str = str.replaceAll("D", "t");
		str = str.replaceAll("T", "t");
		//reintegrer le 2~1, le W~jO, le Y~@ et le I~i comme voyelles !!
		//------- le T~ot
		return str;
	}

	/**
	 * Permet de phonétiser une chaîne littérale grâce à espeaks. Pour le moment cette phonétisation n'est permise que sur un environnement linux.
	 * @param param la chaine à phonétiser
	 * @return la chaine littérale correspondant à la description phonétique standard
	 * @throws IOException espeaks a echoué
	 */
	// renvoie les lignes de la sortie standard pour espeaks, ne distingue que
	// Linux vs autres et n'echappe pas les characteres de potentielles
	// injections malicieuses de code
	public static String synthese(final String param) throws IOException {

		String os = System.getProperty("os.name");
		// System.out.println(os);

		Runtime runtime = Runtime.getRuntime();
		final Process process;

		if (os.equals("Linux")) {
			String[] args1 = { "/bin/sh", "-c",
					"espeak -v fr-fr -x -q \"" + param + "\"" };
			process = runtime.exec(args1);
		} else {
			String[] args1 = { "cmd.exe", "/C",
					"espeak.exe -v fr-fr -x -q \"" + param + "\"" };
			process = runtime.exec(args1);
		}
		// attention au "&" sur windows et au ";" sur linux qui peuvent
		// permettre un hack et l'execution d'autres lignes de commandes
		// String hackParam = "\" & dir";

		class Espeaks extends Thread {
			StringBuilder sb = new StringBuilder(param.length() * 2);

			public String retour() {
				return sb.toString();
			}

			// Consommation de la sortie standard de l'application externe dans
			// un Thread separe
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(process.getInputStream()));
					String line = "";
					try {
						while ((line = reader.readLine()) != null) {
							sb.append(line);
						}
					} finally {
						reader.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		String r = "";
		Espeaks t = new Espeaks();

		try {
			t.start();

			while ((r = t.retour()).length() == 0) {
				Thread.sleep(1);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return formater(r);
	}

	static void main(String[] args) {
		try {
			// String s =
			// SynthetiseurVocal.synthese("Les premiers automates nous font sourire aujourd'hui et les premiers ordinateurs également, mais un peu moins. ");

			String s = SynthetiseurVocal.synthese("peur ");
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

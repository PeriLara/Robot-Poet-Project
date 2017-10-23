package phonetiseur;

import java.util.regex.Pattern;

/**Classe contenant un ensemble d'expressions réguilières qui nous sont utiles dans tous le programme. */

public class RegExp {

	public static final String classeConsonne = "bpmtdnNfvszSZkgrl6";
	public static final String classeGlide = "wj5";
	public static final String classeVoyelles = "@aeiouyEO12347";

	public static final Pattern regExConsonne = Pattern.compile("[" + classeConsonne + "]");
	public static final Pattern regExGlide = Pattern.compile("[" + classeGlide + "]");
	public static final Pattern regExVoyelle = Pattern.compile("[" + classeVoyelles + "]");

	//methodologie : regarder si un mot existait commençant par [] et suivie de la lettre (eventuellement sur des critères du parlé (exemple "j'peux" -> [Sp@])
	//               ensuite les deux versions de voisement accordent leurs classes pour avoir la même
	public static final Pattern regExBiConsonne = Pattern.compile("[bptdfvszSZkg]r|"
																	+ "[bpfvszSZkg]l"
																	+ "|[sSzZ][pb]"
																	+ "|[psSbzZ][td]"
																	+ "|[SZ][fv]"
																	+ "|[pkbg][sz]"
																	+ "|[ptbd][SZ]"
																	+ "|[sSzZ][kg]"
																	+ "|[bpmtdnNfvszSZkgrl6][wj5]");
	//biconsonne suivie d'une glide ou d'une semi-consonne (l ou r en fonction de la classe)
	public static final Pattern regExTriConsonne = Pattern.compile("[bptdfvszSZkg]r[wj5]"
																	+ "|[bpfvszSZkg]l[wj5]"
																	+ "|[sS]p[wj5rl]"
																	+ "|[zZ]b[wj5rl]"
																	+ "|[psS]t[wj5r]"
																	+ "|[bzZ]d[wj5r]"
																	+ "|[S]f[wj5lr]"
																	+ "|[Z]v[wj5lr]"
																	+ "|[pk]s[wj5lr]"
																	+ "|[bg]z[wj5lr]"
																	+ "|[pt]S[wj5lr]"
																	+ "|[bd]Z[wj5lr]"
																	+ "|[sSr]k[wj5lr]"
																	+ "|[zZr]g[wj5lr]");
	
	
	public static final Pattern regExNonVoyelle = Pattern.compile("[" + classeConsonne + classeGlide + "]");
	public static final Pattern regExGroupeNonVocalique = Pattern.compile("[" + classeConsonne + classeGlide + "]+");
	
	//pour les liaisons ces classes sont distinctives en tant que contexte pour la liaison
	public static final Pattern regExLaisonVocalique = Pattern.compile("[" + classeVoyelles + "5]");
	public static final Pattern regExNonLiaisonConsonnantique = Pattern.compile("[" + classeConsonne + "wj]");
	public static final Pattern regExPhone = Pattern.compile("[" +classeConsonne+classeGlide+ classeVoyelles + "]");
	public static final Pattern regExKleen = Pattern.compile(".");

}

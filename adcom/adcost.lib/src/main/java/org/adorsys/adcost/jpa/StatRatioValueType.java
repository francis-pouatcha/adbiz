package org.adorsys.adcost.jpa;

public enum StatRatioValueType {
	/*
	 * Les ratios statistiques définis sous forme de constantes sont valables
	 * à partir de la période comptable de définition et pour toutes les
	 * périodes comptables ultérieures de l’exercice.
	 * 
	 * Par exemple : Le ratio statistique Salariés est défini comme une
	 * constante. Dans la période 1 de l’exercice, on enregistre 10 salariés
	 * sur le centre de coûts 6600. Le système enregistre ensuite,
	 * automatiquement, 10 salariés dans les périodes 2 à 12. Dans la
	 * période 6, l’effectif est porté à 15. Par conséquent, dans la
	 * période 6, on enregistre 15 salariés sur le centre de coûts. Le
	 * système enregistre automatiquement 15 salariés dans les périodes 6 à
	 * 12.
	 */
	FIX_VALUE,
	/*
	 * Les ratios définis en tant que valeurs totales sont valables uniquement
	 * dans la période comptable dans laquelle ils sont saisis.
	 * 
	 * Par exemple : On défini le ratio statistique unités téléphoniques en
	 * tant que valeur totale. Dans la période 1 de l’exercice, on enregistre 1
	 * 000 unités téléphoniques sur le centre de coûts 6500. Le système
	 * enregistre 1 000 unités téléphoniques dans la période 01 uniquement.
	 */
	TOTAL_VALUE
}

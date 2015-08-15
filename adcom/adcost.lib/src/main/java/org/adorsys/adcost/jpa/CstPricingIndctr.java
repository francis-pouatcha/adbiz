package org.adorsys.adcost.jpa;

/**
 * L'indicateur de prix définit la méthode de calcul du prix d’un processus de
 * gestion ou d’un type d’activité pour un centre d'activité. Ils existent
 * deux champs : la champ Indicateur de Prix définit la manière dont le
 * système calcule les prix pour les valeurs réelles. Dans le cas des
 * imputations réelles, on peut saisir un indicateur de prix différent de
 * celui du plan.
 * 
 * @author francis
 *
 */
public enum CstPricingIndctr {
	/*
	 * 001 Prix Automatique
	 * 
	 * Le système détermine automatiquement le prix du type d’activité en
	 * fonction de l’activité planifiée et des coûts planifiés requis par le
	 * centre.
	 * 
	 * - Prix fixe : coûts planifiés fixes * activité planifiés
	 * 
	 * - Prix variable : coûts planifiés variables * activité planifiés
	 * 
	 * - Prix de cession global : prix variable + prix fixe
	 */
	OO1_AUTOMATIC,
	/*
	 * 002 Prix Fixe par Capacité
	 * 
	 * Le système détermine la part variable du prix en fonction de
	 * l’activité planifiés. La part fixe repose quant à elle sur la
	 * capacité.
	 * 
	 * Cette méthode se traduit d’habitude par une sous-absorption pour le
	 * centre de coûts. En effet, le centre de coût n’est pas déchargé
	 * intégralement des coûts encourus pour l’offre de la capacité.
	 * 
	 * - Prix fixe : coûts planifiés fixes * capacité
	 * 
	 * - Prix variable : coûts planifiés variables * activité planifiés
	 * 
	 * - Prix de cession global : prix variable + prix fixe
	 * 
	 * Si on a défini les valeurs 001 ou 002, le système détermine de
	 * nouveaux prix lorsqu'on exécute le calcul du prix planifié.
	 */
	OO2_FIX_BY_CAPACITY,
	/*
	 * 003 Prix Manuel
	 * 
	 * On définit le prix du type d’activité manuellement.
	 */
	OO3_MANUAL,

	/*
	 * 004 Prix Itératif
	 * 
	 * Les prix sont calculés itérativement par le système.
	 */
	OO4_ITERATIVE,

	/*
	 * 005 Prix Réel
	 * 
	 * Le système détermine le prix de cession réel en fonction de
	 * l’activité réelle si on exécute le calcul du prix réel.
	 */
	OO5_REAL_ACTIVITY,

	/*
	 * 006 Variable au Activité Réelle et Fixe par Capacité
	 * 
	 * Le système détermine la partie variable du prix en fonction de
	 * l’activité réelle. La partie fixe repose quant à elle sur la
	 * capacité.
	 * 
	 * Si on a défini les valeurs 005 ou 006, le système détermine de
	 * nouveaux prix lorsqu'on exécute le calcul du prix réel. Le nouveau
	 * calcul de l’activité réelle aux prix réels est uniquement exécuté si
	 * on a sélectionné le nouveau calcul au cours de la gestion des versions.
	 */
	OO6_VAR_OO5ANDOO2,

	/*
	 * 007 Prix de cession réel manuel
	 * 
	 * On paramètre le prix du type d’activité ou du processus de gestion
	 * manuellement. À l’aide de ce champ prix, on peut paramétrer
	 * manuellement un prix indépendant du planifié et différent de celui du
	 * planifié.
	 */
	OO7_REAL_MANUAL;
}

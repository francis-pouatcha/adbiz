package org.adorsys.adcost.jpa;

public enum CstActvtyTypeCtgry {
	/*
	 * L’activité réelle est saisie et imputée directement. Imputation
	 * d’activités mesurables, valorisation = Quantité réelle x Prix
	 * planifié
	 */
	O1_QTY_CNSMD,
	/*
	 * Les quantités réelles résultent du total des activités consommées,
	 * calculé à l’aide des document de référence des centres consommateurs
	 * (bon de commande, fiche de décharge)
	 * 
	 * Calcul indirect de la quantité d’activité à l’aide des documents de
	 * référence de centres consommateurs.
	 */
	O2_QTY_DEDCTD_FRM_OUTPUT,
	/*
	 * Les activités réelles sont imputées à l’aide de l’imputation
	 * indirecte d’activités.
	 * 
	 * Imputation automatique des quantités d’activité à l’aide des documents
	 * de référence de centres consommateurs.
	 */
	O3_INDRCT_IMPTTNT,
	/*
	 * Les types d’activités ne sont pas imputés
	 * 
	 * Ce type d’activité est uniquement utilisé pour enregistrer des
	 * quantités (pas de valeur) dans les centres fournisseurs.
	 */
	O4_NO_IMPTTNT,
	/*
	 * Imputation d’activités réel=plan. Une imputation réel=plan a lieu
	 * quand on suppose que la quantité planifiée est aussi celle qui a été
	 * réellement consommée. Ceci implique que la valeur réelle=plan x taux
	 * d’exploitation du centre
	 * 
	 * Imputation d’activités réel=plan. Une imputation réel=plan a lieu
	 * quand on suppose que la quantité planifiée est aussi celle qui a été
	 * réellement consommée. Ceci implique que la valeur réelle=plan x taux
	 * d’exploitation du centre Imputation réelle des activités à l’aide de
	 * l’imputation d’activités réel=plan. Cette catégorie de type
	 * d’activité est uniquement utilisée pour les imputations réelles. La
	 * planification peut être exécutée à l’aide des catégories de type
	 * d’activité 1, 2 ou 3. La catégorie 1 est la plus utilisée.
	 */
	O5_CSMD_EQ_PLANED;
}

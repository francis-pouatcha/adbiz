package org.adorsys.adcost.jpa;

public enum CstBearerType {
	/*
	 * Porteur primaire (01)
	 * 
	 * Cette catégorie de porteurs peut être chargée avec toutes les
	 * écritures primaires venant de la CGFI.
	 */
	BRP01_PRIMARY,
	/*
	 * Porteur abonné / Méthode de coûts proportionnelles (03)
	 * 
	 * Cette catégorie permet d'abonner un porteur a la distribution d'une
	 * charge qui n'est comptabilisé dans la CGFI qu'au moment ou le coup réel
	 * est induis. La comptabilisation peut avoir lieu avant, pendant ou après
	 * l'imputation. Si par exemple le loyer se paye annuellement, le montant du
	 * loyer du centre peut être imputé chaque moi comme charge
	 * indépendamment du moment ou le loyer est saisie dans la CGFI.
	 * 
	 * Pour évité une dissociation entre la CAGE et la CGFI, il est conseillé
	 * de passer ces opérations dans la CGFI en utilisant les comptes de
	 * régularisation.
	 */
	BRP02_PROP_COSTS,
	/*
	 * Porteur abonné / Méthode des coûts réel=plan (04)
	 * 
	 * Cette catégorie est utilisée dans la méthode d’abonnement réel=plan
	 * dans la CAGE. On peut imputer directement le coût de la CGFI pour
	 * indiquer le moment où les coûts réels sont induits.
	 */
	BRP03_REAL_EQ_PLAN,
	/*
	 * Porteur de produit (11)
	 * 
	 * Cette catégorie de porteurs permet de comptabiliser les produits.
	 * 
	 * Avant de définir un porteur de produit, il faut vérifier s’il ne s’agit
	 * pas d’une écriture correspondant à un avoir et dont les coûts doivent
	 * être pris en compte. Si tel est le cas, nous devons utiliser la
	 * catégorie de porteur (01 - porteur primaires) et non la catégorie 11
	 * (porteur de produit). Ces avoirs apparaissent comme des coûts négatifs
	 * et sont traités de la même manière que toutes les autres porteurs de
	 * la catégorie 01.
	 */
	BRP11_PRODUCT_BEARER,

	/*
	 * Réduction sur ventes (12)
	 * 
	 * Les porteurs de cette catégorie sont utilisés pour comptabiliser les
	 * réductions sur ventes. Les réductions sur ventes (remises, ajustements,
	 * corrections) sont des écritures de correction ou de déduction de
	 * produits, telles que les remises et les ristournes. Certains produits ne
	 * sont pas classifiés comme réductions sur ventes; il s’agit, par
	 * exemple, des coûts de transport qui sont calculés séparément sur la
	 * facture ou des majorations pour les petites quantités ou les commandes
	 * internes spéciales. Ces rubriques sont définies comme des porteurs de
	 * produit.
	 * 
	 * Les options d’imputation pour les porteurs de coût de cette catégorie
	 * sont identiques à celles prévues pour la catégorie de porteurs (11 -
	 * porteurs de produit). Les valeurs des centres de coûts sont affichées
	 * comme statistiques uniquement (tout comme pour les produits).
	 */
	BRP12_SALES_DISCOUNT,
	/*
	 * Imputation externe (22)
	 * 
	 * Les porteurs de cette catégorie sont utilisées pour imputer les coûts
	 * de commandes, de projets à des objets externes à la CAGE.
	 * 
	 * Les objets externes a la CAGE peuvent être, des immobilisations (Classe
	 * 2), des articles et matière premières (Classe 3) ou des comptes
	 * généraux de la CGFI. L'application crée toujours une pièce comptable
	 * lorsque nous effectuons une imputation à des objets externes.
	 * 
	 * On ne peut pas utiliser cette catégorie de porteur pour effectuer des
	 * imputations à des objets de la CAGE (tels que les centres de coûts, les
	 * commandes ou les projets). On doit utiliser les porteurs secondaire de
	 * catégorie (21) pour ces imputations internes. Contrairement à
	 * l’imputation à des objets externes à la CAGE, aucune pièce comptable
	 * n’est générée par cette application pour l’imputation à des objets
	 * internes de la CAGE, étant donné que le flux de valeurs survient
	 * exclusivement dans la CAGE.
	 */
	BRP22_EXTERNAL_IMPTN,
	/*
	 * Porteurs pour Comptes de Bilan de la Comptabilité Générale (90)
	 * 
	 * Les porteurs de cette catégorie sont générées automatiquement lorsque
	 * nous créons, dans la CAGE, des porteurs qui disposent de comptes
	 * collectifs d’immobilisation, c’est-à-dire de comptes de bilan spéciaux,
	 * comme comptes généraux correspondants en CGFI. Pour cela, ces
	 * catégories ne sont pas modifiables dans la base de données de la CAGE.
	 * 
	 * La CGFI n’a pas besoin de connaitre les des imputations de la catégorie
	 * 90. Toutefois, si on saisie une imputation (90), celle-ci est uniquement
	 * mise à jour statistiquement, même pour les objets réels de la CAGE.
	 * 
	 * Les porteurs de la catégorie (90) permettent de gérer les coûts et
	 * budget d'une commande ou d'un projet pendant l’acquisition des immeubles
	 * pouvant être directement intégrés à l’actif.
	 */
	BRP90_EXTERNAL_ACCTNG,

	/*
	 * Imputation interne (21)
	 * 
	 * Cette catégorie de porteur sert à imputer les coûts de commande ou de
	 * projet à des objets internes à la CAGE. Les objets internes à la CAGE
	 * sont par exemple, les commandes, les centres de produits, les centres de
	 * coûts et les projets.
	 * 
	 * On ne peut pas utiliser cette catégorie de porteur pour imputer à des
	 * objets externes à la CAGE (tels que des immeubles, des articles ou autre
	 * comptes de la CGFI). Nous devons utiliser les porteurs primaires de
	 * catégorie 22 pour les imputations externes.
	 */
	BRS21_INTERNAL_IMPTN,
	/*
	 * Analyse du Résultat des Projets ou des Commandes (31)
	 * 
	 * Cette catégorie de porteur sert à sauvegarder les données d’analyse de
	 * résultat de commandes ou de projets.
	 */
	BRS31_PROJECT_PnL_ANALYSIS,
	/*
	 * Taux de Majoration (41)
	 * 
	 * Cette catégorie de porteur s’utilise pour affecter les frais généraux
	 * de centres de coûts aux commandes à l’aide des taux de majoration.
	 */
	BRS41_OVERHEAD_BY_RATE,
	/*
	 * Répartition globale (42)
	 * 
	 * Cette catégorie de porteur est utilisée pour imputer les coûts à
	 * l’aide de la méthode de répartition globale.
	 */
	BRS42_OVERHEAD_BY_KEY,
	/*
	 * Imputation d’activités ou de processus (43)
	 * 
	 * Cette catégorie de porteur est utilisée lors de l’imputation interne
	 * des/aux activités et pour le Activity- Based Costing.
	 */
	BRS43_ACTVITY_BASED_COSTING,
	/*
	 * Entrées de commandes : produits (50)
	 * 
	 * Cette catégorie de porteur est utilisée pour les produits des commandes
	 * clients pour les commandes acquises dans la période actuelle.
	 */
	BRS50_ORDER_BCKLG_OP_INCOME,
	/*
	 * Entrées de commandes : autres produits (51)
	 * 
	 * Cette catégorie de porteur est utilisée pour les autres produits, tels
	 * que les intérêts sur commandes clients pour les commande acquises dans
	 * la période actuelle.
	 */
	BRS51_ORDER_BCKLG_NOP_INCOME,
	/*
	 * Entrées Commandes : coûts (52)
	 * 
	 * Cette catégorie de porteur s’utilise pour les coûts d'acquisition des
	 * commandes clients pour les commandes acquises dans période actuelle.
	 */
	BRS52_ORDER_ACQ_CHARGES,
	/*
	 * Valeurs acquises (61)
	 * 
	 * Cette catégorie de porteur est utilisée pour les valeurs acquises
	 * issues de l’analyse des progrès dans la gestion de projets.
	 */
	BRS61_INCOME_ADJUSTMENT
}
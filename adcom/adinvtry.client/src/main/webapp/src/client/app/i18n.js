/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('adinvtry')
        .config(function ($translateProvider) {

            $translateProvider.translations('en', {
                LANG_FR: 'French',
                LANG_EN: 'English',
                logout: 'logout',
                "reload.title":'Reload',
                "show_conflict.title":'Show conflict',

                "InvInvtry_acsngDt_description.text":"The recording date of this inventory item.",
                "InvInvtry_acsngDt_description.title":"Inventory Date",
                "InvInvtry_acsngUser_description.text":"Accessing User",
                "InvInvtry_acsngUser_description.title":"Accessing User",
                "InvInvtry_acsngUserFullName_description.title":"Full Name",
                "InvInvtry_descptn_Size_validation.text":"The description must have less than 256 characters",
                "InvInvtry_descptn_Size_validation.title":"The description must have less than 256 characters",
                "InvInvtry_descptn_description.text":"Description of this inventory.",
                "InvInvtry_descptn_description.title":"Note",
                "InvInvtry_description.text":"An inventory",
                "InvInvtry_description.title":"Inventory",
                "InvInvtry_gapPurchAmtHT_description.text":"The amount of this inventory.",
                "InvInvtry_gapPurchAmtHT_description.title":"Gap Purchase Amount",
                "InvInvtry_gapSaleAmtHT_description.text":"The amount of this inventory.",
                "InvInvtry_gapSaleAmtHT_description.title":"Gap Sale Amount",
                "InvInvtry_invtryDt_description.text":"The date of this inventory.",
                "InvInvtry_invtryDt_description.title":"Inventory Date",
                "InvInvtry_invtryNbr_description.text":"The inventory number.",
                "InvInvtry_invtryNbr_description.title":"Inventory Number",
                "InvInvtry_invtryStatus_description.text":"The status of this inventory.",
                "InvInvtry_invtryStatus_description.title":"Status",
                "InvInvtry_invInvtryType_description.text":"The Mode of this inventory",
                "InvInvtry_invInvtryType_description.title":"Inventory Mode",
                "InvInvtry_section_description.text":"The Section",
                "InvInvtry_section_description.title":"Section",
                "InvInvtry_startRange_description.text":"Product name Start with",
                "InvInvtry_startRange_description.title":"a-",
                "InvInvtry_endRange_description.text":"Product name End with",
                "InvInvtry_endRange_description.title":"-z",
                "InvInvtry_invtryDtFrom_description.title":"Inventory After",
                "InvInvtry_invtryDtTo_description.title":"Inventory Before",
                "InvInvtry_acsngDtFrom_description.title":"Assessment Before",
                "InvInvtry_acsngDtTo_description.title":"Assessment After",
                "InvInvtry_close_description.title":"Close",
                "InvInvtry_correctStock_description.title":"Correct Stock",
                "InvInvtry_join_description.title":"Combine",
                "InvInvtry_compare_description.title":"Compare",
                "InvInvtry_invtryGroup_description.title":"Group",
                "InvInvtry_mainInvtry_description.title":"Main Inventory",

                "InvInvtryItem_acsngDt_description.text":"The recording date of this inventory item.",
                "InvInvtryItem_acsngDt_description.title":"Inventory Date",
                "InvInvtryItem_acsngUser_description.text":"The user performing the inventory",
                "InvInvtryItem_acsngUser_description.title":"Counted By",
                "InvInvtryItem_artPic_description.text":"The article code",
                "InvInvtryItem_artPic_description.title":"Article Code",
                "InvInvtryItem_asseccedQty_description.text":"Actual quantity of products physically counted.",
                "InvInvtryItem_asseccedQty_description.title":"Assessed Quantity",
                "InvInvtryItem_description.text":"An inventory item",
                "InvInvtryItem_description.title":"Inventory Item",
                "InvInvtryItem_expectedQty_description.text":"The quantity of this article expected to be in stock.",
                "InvInvtryItem_expectedQty_description.title":"Quantity in Stock",
                "InvInvtryItem_gapTotalPpPT_description.text":"The total purchase price pre tax.",
                "InvInvtryItem_gapTotalPpPT_description.title":"Total Purchase Price Pre Tax",
                "InvInvtryItem_gapTotalSpPT_description.text":"The total price.",
                "InvInvtryItem_gapTotalSpPT_description.title":"Total Sale Price Pre Tax",
                "InvInvtryItem_gap_description.text":"Deviation of access and expected quantity.",
                "InvInvtryItem_gap_description.title":"Gap",
                "InvInvtryItem_invtryNbr_description.text":"The inventory number.",
                "InvInvtryItem_invtryNbr_description.title":"Inventory Number",
                "InvInvtryItem_lotPic_description.text":"The identifier of this lot. Might be printet as a bar code and put on each article of this lot.",
                "InvInvtryItem_lotPic_description.title":"Lot Code",
                "InvInvtryItem_orgUnit_description.text":"The organization unit",
                "InvInvtryItem_orgUnit_description.title":"Organization Unit",
                "InvInvtryItem_pppuCur_description.text":"The currency of the purchase price",
                "InvInvtryItem_pppuCur_description.title":"Currency of the Purchase Price",
                "InvInvtryItem_pppuPT_description.text":"The purchase price per unit pre tax",
                "InvInvtryItem_pppuPT_description.title":"Purchase Price per Unit Pre Tax",
                "InvInvtryItem_section_description.text":"The Section",
                "InvInvtryItem_section_description.title":"Section Code",
                "InvInvtryItem_sectionName_description.title":"Section Name",
                "InvInvtryItem_sppuCur_description.text":"The currency of the sales price",
                "InvInvtryItem_sppuCur_description.title":"Currency of the Sales Price",
                "InvInvtryItem_sppuPT_description.text":"The sales price per unit pre tax",
                "InvInvtryItem_sppuPT_description.title":"Sales Price per Unit Pre Tax",
                "InvInvtryItem_artName_description.text":"Article name",
                "InvInvtryItem_artName_description.title":"Article Name",
                "InvInvtryItem_artNameStart_description.title":"a-z",
                "InvInvtryItem_artNameEnd_description.title":"a-z",
                "InvInvtryItem_expirDt_description.title":"Expiration Date",
                "InvInvtry_post_description.title":"Post",

                "InvInvntrStatus_CLOSED_description.text":"Closed document",
                "InvInvntrStatus_CLOSED_description.title":"Closed",
                "InvInvntrStatus_ONGOING_description.text":"Ongoing document",
                "InvInvntrStatus_ONGOING_description.title":"Ongoing",
                "InvInvntrStatus_RESUMED_description.text":"Resumed document",
                "InvInvntrStatus_RESUMED_description.title":"Resumed",
                "InvInvntrStatus_SUSPENDED_description.text":"Suspended document",
                "InvInvntrStatus_SUSPENDED_description.title":"Suspended",
                "InvInvntrStatus_description.text":"The state of an inventory",
                "InvInvntrStatus_description.title":"Inventory Status",
                "InvInvntrStatus_POSTED_description.text":"Posted",
                "InvInvntrStatus_POSTED_description.title":"Posted",
                "InvInvntrStatus_INITIALIZING_description.text":"Initializing",
                "InvInvntrStatus_INITIALIZING_description.title":"Initializing",
                "InvInvntrStatus_MERGED_description.text":"Merged",
                "InvInvntrStatus_MERGED_description.title":"Merged",

                "InvInvtryType_description.title":"Inventory Type",
                "InvInvtryType_BY_SECTION_description.title":"Inventory By Session",
                "InvInvtryType_ALPHABETICAL_ORDER_RANGE_description.title":"Inventory By Alphabetical Order Range",
                "InvInvtryType_FREE_INV_description.title":"Inventory By Product",
                "InvInvtryType_description.text":"The type of inventory",
                "InvInvtryType_BY_SECTION_description.text":"Inventory by session",
                "InvInvtryType_ALPHABETICAL_ORDER_RANGE_description.text":"Inventory by alphabetical order range",
                "InvInvtryType_FREE_INV_description.text":"Inventory by product",

                "InvInvtry_NoSectionFound_description.title":"No Section Found",
                "InvInvtry_NoArticleFound_description":"No Article Found",
                "InvInvtry_NoUserFound_description":"No User Found",
                "InvInvtry_gapPurchAmtHTFrom_description.title":"Gap Purch. Amount above",
                "InvInvtry_gapPurchAmtHTTo_description.title":"Gap Purch. Amount below",

                "InvInvtry_alreadyMergedTo_description.alert":" is merged to ",
                "InvInvtry_postedCanNotBeMerged_description.alert":" is already posted and can not be merged.",

                "InvInvtry_longPdctJsonArr_exception.title":"The much products loadings error, please chose, very close product ranges.",
                "InvInvtry_longPdctJsonArr_exception.text":"The much products loadings error, please chose, very close product ranges."



            });
// francais --------------------------------------------------------------------------------
            $translateProvider.translations('fr', {
                LANG_FR: 'Francais',
                LANG_EN: 'Englais',
                logout: 'deconnection',

                "reload.title":'Recharger',
                "show_conflict.title":'Montrer les conflits',

                "InvInvtry_acsngDt_description.text":"La date de saisie de cette ligne inventaire.",
                "InvInvtry_acsngDt_description.title":"Date Inventaire",
                "InvInvtry_acsngUser_description.text":"Exécuteur de l'inventaire",
                "InvInvtry_acsngUser_description.title":"Exécuteur",
                "InvInvtry_acsngUserFullName_description.title":"Nom Complet",
                "InvInvtry_descptn_Size_validation.text":"La description doit avoir moins de 256 caract\u00E8res",
                "InvInvtry_descptn_Size_validation.title":"La description doit avoir moins de 256 caract\u00E8res",
                "InvInvtry_descptn_description.text":"Description de cet inventaire.",
                "InvInvtry_descptn_description.title":"Note",
                "InvInvtry_description.text":"Un inventaire.",
                "InvInvtry_description.title":"Inventaire",
                "InvInvtry_gapPurchAmtHT_description.text":"Le menquant en prix d'achat.",
                "InvInvtry_gapPurchAmtHT_description.title":"Menquant en Prix d'Achat",
                "InvInvtry_gapSaleAmtHT_description.text":"Le menquant en prix de vente..",
                "InvInvtry_gapSaleAmtHT_description.title":"Menquant en Prix de Vente",
                "InvInvtry_invtryDt_description.text":"La date de cet inventaire.",
                "InvInvtry_invtryDt_description.title":"Date de cet Inventaire",
                "InvInvtry_invtryNbr_description.text":"Le numéro de l'inventaire.",
                "InvInvtry_invtryNbr_description.title":"Inventaire Numéro",
                "InvInvtry_invtryStatus_description.text":"Le statut de cet inventaire.",
                "InvInvtry_invtryStatus_description.title":"Statut",
                "InvInvtry_invInvtryType_description.text":"Mode de l'inventaire",
                "InvInvtry_invInvtryType_description.title":"Mode d'inventaire",
                "InvInvtry_section_description.text":"Le rayon",
                "InvInvtry_section_description.title":"Rayon",
                "InvInvtry_startRange_description.text":"Nom de produit Commencant par",
                "InvInvtry_startRange_description.title":"a-",
                "InvInvtry_endRange_description.text":"Nom de produit se terminant par",
                "InvInvtry_endRange_description.title":"-z",
                "InvInvtry_invtryDtFrom_description.title":"Inventaire après le",
                "InvInvtry_invtryDtTo_description.title":"Inventaire avant le",
                "InvInvtry_acsngDtFrom_description.title":"Décompte après le",
                "InvInvtry_acsngDtTo_description.title":"Décompte avant le",
                "InvInvtry_close_description.title":"Clôturer",
                "InvInvtry_correctStock_description.title":"Corriger le Stock",
                "InvInvtry_join_description.title":"Combiner",
                "InvInvtry_compare_description.title":"Comparer",
                "InvInvtry_invtryGroup_description.title":"Groupe",
                "InvInvtry_post_description.title":"Affecter",
                "InvInvtry_mainInvtry_description.title":"Inventaire Principale",

                "InvInvtryItem_acsngDt_description.text":"La date de saisie de cette ligne inventaire.",
                "InvInvtryItem_acsngDt_description.title":"Date Inventaire",
                "InvInvtryItem_acsngUser_description.text":"Utilisateur realisant cet inventaire",
                "InvInvtryItem_acsngUser_description.title":"Compté Par",
                "InvInvtryItem_artPic_description.text":"Code article",
                "InvInvtryItem_artPic_description.title":"Code Article",
                "InvInvtryItem_asseccedQty_description.text":"Quantité réelle de produits de la ligne comptés physiquement.",
                "InvInvtryItem_asseccedQty_description.title":"Quantité Comptée",
                "InvInvtryItem_description.text":"Une ligne inventaire.",
                "InvInvtryItem_description.title":"Ligne Inventaire",
                "InvInvtryItem_expectedQty_description.text":"Quantité de produits supposé en le stock.",
                "InvInvtryItem_expectedQty_description.title":"Quantité en Stock",
                "InvInvtryItem_gapTotalPpPT_description.text":"Le prix total achat hors tax",
                "InvInvtryItem_gapTotalPpPT_description.title":"Prix d Achat Total Hors Taxe",
                "InvInvtryItem_gapTotalSpPT_description.text":"Le prix total.",
                "InvInvtryItem_gapTotalSpPT_description.title":"Prix de Vente Total Hors Taxe",
                "InvInvtryItem_gap_description.text":"\u00C9cart de stock de la ligne d inventaire.",
                "InvInvtryItem_gap_description.title":"\u00C9cart",
                "InvInvtryItem_invtryNbr_description.text":"Le numéro d inventaire.",
                "InvInvtryItem_invtryNbr_description.title":"Numéro d Inventaire",
                "InvInvtryItem_lotPic_description.text":"Le code identifiant ce lot. Pourait etre imprimé comme code bar et posé su chaque produit de ce lot.",
                "InvInvtryItem_lotPic_description.title":"Identifiant du Lot",
                "InvInvtryItem_orgUnit_description.text":"Unite organisationelle",
                "InvInvtryItem_orgUnit_description.title":"Agency",
                "InvInvtryItem_pppuCur_description.text":"La dévise du prix d achat",
                "InvInvtryItem_pppuCur_description.title":"Dévise du Prix d Achat",
                "InvInvtryItem_pppuPT_description.text":"Prix achat unitaire hors taxe",
                "InvInvtryItem_pppuPT_description.title":"Prix Achat Unitaire Hors Taxe",
                "InvInvtryItem_section_description.text":"Le rayon",
                "InvInvtryItem_section_description.title":"Code Rayon",
                "InvInvtryItem_sectionName_description.title":"Nom Rayon",
                "InvInvtryItem_sppuCur_description.text":"La dévise du prix de vente.",
                "InvInvtryItem_sppuCur_description.title":"Dévise du Prix de Vente",
                "InvInvtryItem_sppuPT_description.text":"Prix de vente unitaire hors tax",
                "InvInvtryItem_sppuPT_description.title":"Prix de Vente Unitaire Hors Tax",
                "InvInvtryItem_artName_description.text":"Nom article",
                "InvInvtryItem_artName_description.title":"Nom Article",
                "InvInvtryItem_artNameStart_description.title":"a-",
                "InvInvtryItem_artNameEnd_description.title":"-z",
                "InvInvtryItem_expirDt_description.title":"Date Expiration",

                "InvInvntrStatus_CLOSED_description.text":"Document cloturé",
                "InvInvntrStatus_CLOSED_description.title":"Cloturé",
                "InvInvntrStatus_ONGOING_description.text":"Document en cours",
                "InvInvntrStatus_ONGOING_description.title":"En Cours",
                "InvInvntrStatus_RESUMED_description.text":"Document repris",
                "InvInvntrStatus_RESUMED_description.title":"Repris",
                "InvInvntrStatus_SUSPENDED_description.text":"Document suspendu",
                "InvInvntrStatus_SUSPENDED_description.title":"Suspendu",
                "InvInvntrStatus_description.text":"Status de l'Inventaire",
                "InvInvntrStatus_description.title":"Status Inventaire",
                "InvInvntrStatus_POSTED_description.text":"Affecté",
                "InvInvntrStatus_POSTED_description.title":"Affecté",
                "InvInvntrStatus_INITIALIZING_description.text":"En Préparation",
                "InvInvntrStatus_INITIALIZING_description.title":"En Préparation",
                "InvInvntrStatus_MERGED_description.text":"Fusioné",
                "InvInvntrStatus_MERGED_description.title":"Fusioné",

                "InvInvtryType_description.title":"Type d'Inventaire",
                "InvInvtryType_BY_SECTION_description.title":"Inventaire Par Rayon",
                "InvInvtryType_ALPHABETICAL_ORDER_RANGE_description.title":"Inventaire Par Ordre Alphabetique",
                "InvInvtryType_FREE_INV_description.title":"Inventaire Par Produit",
                "InvInvtryType_description.text":"Le type d'inventaire",
                "InvInvtryType_BY_SECTION_description.text":"Inventaire par rayon",
                "InvInvtryType_ALPHABETICAL_ORDER_RANGE_description.text":"Inventaire par plage alphabétique",
                "InvInvtryType_FREE_INV_description.text":"Inventaire par produit",

                "InvInvtry_NoSectionFound_description.title":"Pas de Rayon Trouvé",
                "InvInvtry_NoArticleFound_description":"Pas d'Article Trouvé",
                "InvInvtry_NoUserFound_description":"Aucun utilisateur Trouvé",
                "InvInvtry_gapPurchAmtHTFrom_description.title":"Écart en Achat de",
                "InvInvtry_gapPurchAmtHTTo_description.title":"Écart en Achat à",

                "InvInvtry_alreadyMergedTo_description.alert":" déjà integré à ",
                "InvInvtry_postedCanNotBeMerged_description.alert":" déjà Afecté. Ne peut plus etre fusioné.",


                "InvInvtry_longPdctJsonArr_exception.title":"Le nombre de produits a charger est élevé, veuillez definir des marge de nom tres proche.",
                "InvInvtry_longPdctJsonArr_exception.text":"Le nombre de produits a charger est élevé, veuillez definir des marge de nom tres proche."


            });
            $translateProvider.preferredLanguage('en');
        });

})();

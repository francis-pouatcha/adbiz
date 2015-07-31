/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('app.core')
        .config(function ($translateProvider) {

            $translateProvider.translations('en', {
                //--------------Article---------------
                'CatalArticle.sppu': 'Sales Price per Unit',
                'CatalArticle.maxDisctRate': 'Max Discount',
                'CIP':'CIP',
                'CatalArticle.minStockQty': 'Min Quantity Stock',
                'CatalArticle.maxStockQty': 'Max Quantity Stock',
                'CatalArticle.vatRate': 'V.A.T Rate',
                'CatalArticle.active': 'Active',
                'CatalArticle.authorizedSale': 'Authorizedsale',
                'CatalArticle.title':'Articles',
                'CatalArticle.new.title':'New Article',
                'CatalArticle.list.title':'List Article',
                'CatalArticle.delete.title':'Delete Article',
                'CatalArticle.cancel.title':'Cancel',
                'CatalArticle.save.title':'Save Article',
                'CatalArticle.edit.title':'Edit Article',
                'CatalArticle.view.title':'Article View',
                'CatalArticle.back.title':'Back',





                //-------------CatalArt2ProductFamily---------

                'CatalArt2ProductFamily.artPic': 'The Article PIC',
                'CatalArt2ProductFamily.famCode': 'The Family Code',
                'CatalArt2ProductFamily.title':'Product Family',
                'CatalArt2ProductFamily.new.title':'New Product Family',
                'CatalArt2ProductFamily.list.title':'List Product Family',
                'CatalArt2ProductFamily.delete.title':'Delete Product Family',
                'CatalArt2ProductFamily.cancel.title':'Cancel',
                'CatalArt2ProductFamily.save.title':'Save Product Family',
                'CatalArt2ProductFamily.edit.title':'Edit Product Family',
                'CatalArt2ProductFamily.view.title':'View Product Family',
                'CatalArt2ProductFamily.back.title':'Back',



                //------------CatalArtDetailConfig-------------
                'CatalArtDetailConfig.title':'Detail Config',
                'cntnrIdentif': 'The code ',
                'CatalArtDetailConfig.qtyOfDtldInMain': 'The quantity of units ',
                'CatalArtDetailConfig.targetPrprtn': 'The proportion',
                'CatalArtDetailConfig.mngInPptn': 'The quantitative relation',
                'CatalArtDetailConfig.new.title':'New Detail Config ',
                'CatalArtDetailConfig.list.title':'List Detail Config',
                'CatalArtDetailConfig.delete.title':'Delete Detail Config',
                'CatalArtDetailConfig.cancel.title':'Cancel',
                'CatalArtDetailConfig.save.title':'Save Detail Config',
                'CatalArtDetailConfig.edit.title':'Edit Detail Config',
                'CatalArtDetailConfig.view.title':'View Detail Config',
                'CatalArtDetailConfig.back.title':'Back',


                //-------------CatalArtEquivalence------------
                'CatalArtEquivalence.title':'Article Equivalence',
                'CatalArtEquivalence.mainArtIdentif': 'Main Article',
                'CatalArtEquivalence.equivArtIdentif': 'Equivalent Article',
                'CatalArtEquivalence.new.title':'New Article Equivalence',
                'CatalArtEquivalence.list.title':'List Article Equivalence',
                'CatalArtEquivalence.delete.title':'Delete Article Equivalence',
                'CatalArtEquivalence.cancel.title':'Cancel',
                'CatalArtEquivalence.save.title':'Save Article Equivalence',
                'CatalArtEquivalence.edit.title':'Edit Article Equivalence',
                'CatalArtEquivalence.view.title':'View Article Equivalence',
                'CatalArtEquivalence.back.title':'Back',




                //------------CatalArtLangMapping--------------
                'CatalArtFeatMapping.title':'Article Feature Mapping',
                'CatalArtFeatMapping.artName': 'Article Name',
                'CatalArtFeatMapping.shortName': 'Article ShortName',
                'langIso2': 'Language Code',
                'CatalAbstractFeatMapping.purpose': 'Purpose',
                'CatalAbstractFeatMapping.usage': 'Usage Mode',
                'CatalAbstractFeatMapping.warnings': 'Warnings',
                'CatalAbstractFeatMapping.substances': 'Substances',
                'CatalAbstractFeatMapping.new.title':'New Article Feature Mapping',
                'CatalAbstractFeatMapping.list.title':'List Article Feature Mapping',
                'CatalAbstractFeatMapping.delete.title':'Delete Article Feature Mapping',
                'CatalAbstractFeatMapping.cancel.title':'Cancel',
                'CatalAbstractFeatMapping.save.title':'Save Article Feature Mapping',
                'CatalAbstractFeatMapping.edit.title':'Edit Article Feature Mapping',
                'CatalAbstractFeatMapping.view.title':'View Article Feature Mapping',
                'CatalAbstractFeatMapping.back.title':'Back',


                //------------CatalArtManufSupp------------------
                'CatalArtManufSupp.title': 'Manufacturer or Supplier',
                'CatalArtManufSupp.artIdentif': 'Identifier or this Article',
                'CatalArtManufSupp.msIdentif': 'Manufacturer or Supplier Identifier',
                'CatalArtManufSupp.msType': 'Type of this Manufacturer or Supplier',
                'CatalArtManufSupp.warrantyMonths': 'Default Warranty Time in Months',
                'CatalArtManufSupp.returnDays': 'Default Return Time in Days',
                'CatalArtManufSupp.pppu': 'Purchase Price per Unit',
                'CatalArtManufSupp.pppuCurrIso3': 'Currency',
                'CatalArtManufSupp.vatRate': 'VAT Rate',
                'CatalArtManufSupp.new.title':'New  Manufacturer or Supplier',
                'CatalArtManufSupp.list.title':'List Manufacturer or Supplier',
                'CatalArtManufSupp.delete.title':'Delete Manufacturer or Supplier',
                'CatalArtManufSupp.cancel.title':'Cancel',
                'CatalArtManufSupp.save.title':'Save  Manufacturer or Supplier',
                'CatalArtManufSupp.edit.title':'Edit  Manufacturer or Supplier',
                'CatalArtManufSupp.view.title':'View  Manufacturer or Supplier',
                'CatalArtManufSupp.back.title':'Back',


                //-----------CatalPicMapping--------------------
                'CatalPicMapping.title': 'PIC Mapping',
                'CatalPicMapping.artIdentif': 'Identifier or this Article',
                'CatalPicMapping.code': 'Code',
                'CatalPicMapping.codeOrigin': 'Code Origin',
                'CatalPicMapping.addInfo': 'Additional Info',
                'CatalPicMapping.priority': 'Priority',
                'CatalPicMapping.new.title':'New PIC Mapping',
                'CatalPicMapping.list.title':'List PIC Mapping',
                'CatalPicMapping.delete.title':'Delete PIC Mapping',
                'CatalPicMapping.cancel.title':'Cancel',
                'CatalPicMapping.save.title':'Save PIC Mapping',
                'CatalPicMapping.edit.title':'Edit PIC Mapping',
                'CatalPicMapping.view.title':'View PIC Mapping',
                'CatalPicMapping.back.title':'Back',



                //------------CatalProductFamily---------------
                'CatalProductFamily.title': 'Product Family',
                'CatalProductFamily.parentIdentif': 'Identifier or the Parent Family',
                'CatalProductFamily.famPath': 'Family Path',
                'CatalProductFamily.new.title':'New Product Family',
                'CatalProductFamily.list.title':'List Product Family',
                'CatalProductFamily.delete.title':'Delete Product Family',
                'CatalProductFamily.cancel.title':'Cancel',
                'CatalProductFamily.save.title':'Save Product Family',
                'CatalProductFamily.edit.title':'Edit Product Family',
                'CatalProductFamily.view.title':'View Product Family',
                'CatalProductFamily.back.title':'Back',


                //-----------CatalProductFamilyLangMap-----------
                'CatalFamilyFeatMaping.title': '',
                'CatalFamilyFeatMaping.famPath': '',
                'CatalFamilyFeatMaping.new.title':'New ',
                'CatalFamilyFeatMaping.list.title':'List',
                'CatalFamilyFeatMaping.delete.title':'Delete ',
                'CatalFamilyFeatMaping.cancel.title':'Cancel',
                'CatalFamilyFeatMaping.save.title':'Save ',
                'CatalFamilyFeatMaping.edit.title':'Edit ',
                'CatalFamilyFeatMapings.view.title':'View ',
                'CatalFamilyFeatMapings.back.title':'Back',

                LANG_FR: 'French',
                LANG_EN: 'English'
            });

 //------------french----------------

            $translateProvider.translations('fr', {
                //--------------Article---------------
                'CatalArticle.sppu': 'Prix de vente Unitaire',
                'CatalArticle.maxDisctRate': 'Remise Maximale',
                'CIP':'Code CIP',
                'CatalArticle.minStockQty': 'Qte Min en stock',
                'CatalArticle.maxStockQty': 'Qte Max en stock',
                'CatalArticle.vatRate': 'T.V.A',
                'CatalArticle.active': 'Actif',
                'CatalArticle.authorizedSale': 'Vendable',
                'CatalArticle.title':'Articles',
                'CatalArticle.new.title':'Nouvel Article',
                'CatalArticle.list.title':'Liste des Articles',
                'CatalArticle.delete.title':'Supprimer Article',
                'CatalArticle.cancel.title':'Annuler',
                'CatalArticle.save.title':'Enregistrer',
                'CatalArticle.edit.title':'Modifier Article',
                'CatalArticle.view.title':'Voir Article',
                'CatalArticle.back.title':'Retour',





                //-------------CatalArt2ProductFamily---------

                'CatalArt2ProductFamily.artPic': 'Code CIP',
                'CatalArt2ProductFamily.famCode': 'Code Famille',
                'CatalArt2ProductFamily.title':'Famille Prodruit',
                'CatalArt2ProductFamily.new.title':'Nouveau Produit',
                'CatalArt2ProductFamily.list.title':'Liste de Produits',
                'CatalArt2ProductFamily.delete.title':'Supprimer Produit',
                'CatalArt2ProductFamily.cancel.title':'Annuler',
                'CatalArt2ProductFamily.save.title':'Enregistrer Produit',
                'CatalArt2ProductFamily.edit.title':'Modifier Produit',
                'CatalArt2ProductFamily.view.title':'Voir Produit',
                'CatalArt2ProductFamily.back.title':'Retour',



                //------------CatalArtDetailConfig-------------
                'CatalArtDetailConfig.title':'Transformation du produit',
                'cntnrIdentif': 'Code',
                'CatalArtDetailConfig.qtyOfDtldInMain': 'Quantite unitaire',
                'CatalArtDetailConfig.targetPrprtn': 'Produit Cible',
                'CatalArtDetailConfig.mngInPptn': 'relation quantitative',
                'CatalArtDetailConfig.new.title':'Nouvel Transformation du Produit ',
                'CatalArtDetailConfig.list.title':'Liste Transformation du Produit',
                'CatalArtDetailConfig.delete.title':'Supprimer Transformation du Produit',
                'CatalArtDetailConfig.cancel.title':'Annuler',
                'CatalArtDetailConfig.save.title':'Enregistrer Transformation du Produit',
                'CatalArtDetailConfig.edit.title':'Modifier Transformation du Produit',
                'CatalArtDetailConfig.view.title':'Voir Transformation du Produit',
                'CatalArtDetailConfig.back.title':'Retour',



                //-------------CatalArtEquivalence------------
                'CatalArtEquivalence.title':'Articles Equivalents',
                'CatalArtEquivalence.mainArtIdentif': 'Identifiant du Produit Principal',
                'CatalArtEquivalence.equivArtIdentif': 'Identifiant du Produit Equivalent',
                'CatalArtEquivalence.new.title':'Nouvel Article Equivalent',
                'CatalArtEquivalence.list.title':'Liste Article Equivalent',
                'CatalArtEquivalence.delete.title':'Supprimer Article Equivalent',
                'CatalArtEquivalence.cancel.title':'Annuler',
                'CatalArtEquivalence.save.title':'Enregistrer Article Equivalent',
                'CatalArtEquivalence.edit.title':'Modifier Article Equivalent',
                'CatalArtEquivalence.view.title':'Voir Article Equivalent',
                'CatalArtEquivalence.back.title':'Retour',



                //------------CatalArtLangMapping--------------
                'CatalArtFeatMapping.title':'Table de detail de l\'article',
                'CatalArtFeatMapping.artName': 'Nom du Produit',
                'CatalArtFeatMapping.shortName': 'Diminutif Produit',
                'langIso2': 'Code Language',
                'CatalAbstractFeatMapping.purpose': 'Objectif',
                'CatalAbstractFeatMapping.usage': 'Mode d\'emploi ',
                'CatalAbstractFeatMapping.warnings': 'Avertissements',
                'CatalAbstractFeatMapping.substances': 'Substances',
                'CatalAbstractFeatMapping.new.title':'Nouvelle Table de Detail Article',
                'CatalAbstractFeatMapping.list.title':'Liste Table de Detail Article',
                'CatalAbstractFeatMapping.delete.title':'Supprimer Table de Detail Article',
                'CatalAbstractFeatMapping.cancel.title':'Annuler',
                'CatalAbstractFeatMapping.save.title':'Enregistrer Table de Detail Article',
                'CatalAbstractFeatMapping.edit.title':'Modifier Table de Detail Article',
                'CatalAbstractFeatMapping.view.title':'Voir Table de Detail Article',
                'CatalAbstractFeatMapping.back.title':'Retour',



                //------------CatalArtManufSupp------------------
                'CatalArtManufSupp.title': 'Fabricant ou Fournisseur',
                'CatalArtManufSupp.artIdentif': 'Identifiant de cet Article',
                'CatalArtManufSupp.msIdentif': 'Identifiant Fabricant ou Fournisseur',
                'CatalArtManufSupp.msType': 'Type de ce Fabricant ou Fournisseur',
                'CatalArtManufSupp.warrantyMonths': 'Temps Standard de Guarranti en Mois',
                'CatalArtManufSupp.returnDays': 'Temps standard pour Retourner en Jours',
                'CatalArtManufSupp.pppu': 'Prix d Achat Unitaire',
                'CatalArtManufSupp.pppuCurrIso3': 'Devise',
                'CatalArtManufSupp.vatRate': 'T.V.A',
                'CatalArtManufSupp.new.title':'Nouveau  Fabricant ou Fournisseur',
                'CatalArtManufSupp.list.title':'Liste Fabricant ou Fournisseur',
                'CatalArtManufSupp.delete.title':'Supprimer Fabricant ou Fournisseur',
                'CatalArtManufSupp.cancel.title':'Annuler',
                'CatalArtManufSupp.save.title':'Enregistrer  Fabricant ou Fournisseur',
                'CatalArtManufSupp.edit.title':'Modifier  Fabricant ou Fournisseur',
                'CatalArtManufSupp.view.title':'Voir  Fabricant ou Fournisseur',
                'CatalArtManufSupp.back.title':'Retour',



                //-----------CatalPicMapping--------------------
                'CatalPicMapping.title': 'Table PIC',
                'CatalPicMapping.artIdentif': 'Identifiant de cet Article',
                'CatalPicMapping.code': 'Code',
                'CatalPicMapping.codeOrigin': 'Origine du Code',
                'CatalPicMapping.addInfo': 'Information additionelle',
                'CatalPicMapping.priority': 'Priorite',
                'CatalPicMapping.new.title':'Nouvelle Table PIC',
                'CatalPicMapping.list.title':'Liste Table PIC',
                'CatalPicMapping.delete.title':'Supprimer Table PIC',
                'CatalPicMapping.cancel.title':'Annuler',
                'CatalPicMapping.save.title':'Enregistrer Table PIC',
                'CatalPicMapping.edit.title':'Modifier Table PIC',
                'CatalPicMapping.view.title':'Voir Table PIC',
                'CatalPicMapping.back.title':'Retour',




                //------------CatalProductFamily---------------
                'CatalProductFamily.title': 'Famille de produit',
                'CatalProductFamily.parentIdentif': 'Identifiant de la Famille Parent',
                'CatalProductFamily.famPath': 'Chemin de famille',
                'CatalProductFamily.new.title':'Nouvelle Famille de Produit',
                'CatalProductFamily.list.title':'Liste de Famille de Produit',
                'CatalProductFamily.delete.title':'Supprimer Famille de Produit',
                'CatalProductFamily.cancel.title':'Annuler',
                'CatalProductFamily.save.title':'Enregistrer Famille de Produit',
                'CatalProductFamily.edit.title':'Modifier Famille de Produit',
                'CatalProductFamily.view.title':'Voir Famille de Produit',
                'CatalProductFamily.back.title':'Retour',



                //-----------CatalProductFamilyLangMap-----------
                'CatalFamilyFeatMaping.famPath': '',
                'CatalFamilyFeatMaping.new.title':'Nouveau',
                'CatalFamilyFeatMaping.list.title':'Liste',
                'CatalFamilyFeatMaping.delete.title':'Supprimer ',
                'CatalFamilyFeatMaping.cancel.title':'Annuler',
                'CatalFamilyFeatMaping.save.title':'Enregistrer ',
                'CatalFamilyFeatMaping.edit.title':'Modifier ',
                'CatalFamilyFeatMapings.view.title':'Voir ',
                'CatalFamilyFeatMapings.back.title':'Retour',
                LANG_FR: 'Francais',
                LANG_EN: 'Englais'
            });
            $translateProvider.preferredLanguage('en');
        });

})();

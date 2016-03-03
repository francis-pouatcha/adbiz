/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('adstock')
        .config(function ($translateProvider) {

            $translateProvider.translations('en', {

                //------------Common translations-----------//
            	"Action.title": 'Action',
                'app.core.list.title':'List',
                'app.core.create.title':'Create',
                'app.core.new.title':'New',
                'app.core.delete.title':'Delete',
                'app.core.save.title':'Save',
                'app.core.edit.title':'Edit',
                'app.core.back.title':'Back',
                'app.core.view.title':'View',
                'app.core.search.title':'Search',
                'app.core.cancel.title':'Cancel',
                'app.core.clear.title':'Clear',
                'app.core.delete.confirmation.title':'Are you sure to delete this: \n',

                //--------------ArticleLot---------------
                'StockArticlelot.lotPic.title': 'Lot PIC',
                'StockArticlelot.artPic.title': 'PIC',
                'StockArticlelot.section.title': 'Section',
                'StockArticlelot.sectionName.title': 'Section Name',
                'StockArticlelot.name.title': 'Name',
                'StockArticlelot.acsngUser.title':'Creation User',
                'StockArticlelot.expirDt.title':'Expiration Date',
                'StockArticlelot.valueDt.title':'Creation Date',
                'StockArticlelot.supplier.title':'Supplier',
                'StockArticlelot.supplierPic.title':'Supplier Code',
                'StockArticlelot.stkUnitValCur.title':'Unit Currency',
                'StockArticlelot.trgtQty.title':'Lot Qty',
                'StockArticlelot.stkgDt.title':'Stkg. Date',
                'StockArticlelot.deliveredTrgtQty.title': 'Delivered Lot Qty',
                'StockArticlelot.salesPrice.title': 'Sales price',
                'StockArticlelot.buyingPrice.title': "Buying price",

                'StockArticlelot.prchGrossPrcPreTax.title':'Total PP PreTax',
                'StockArticlelot.prchNetPrcPreTax.title':'Total Net PP PreTax',
                'StockArticlelot.prchUnitPrcCur.title':'Currency PP per Unit',
                'StockArticlelot.prchUnitPrcPreTax.title':'PP PreTax per Unit',
                'StockArticlelot.prchUnitPrcTaxIncl.title':'PP TaxIncl. per Unit',
                'StockArticlelot.prchVatPct.title':'PP VAT Percent',
                'StockArticlelot.prchVatAmt.title':'PP VAT Amount',
                'StockArticlelot.prchVatUnitAmt.title':'PP VAT Amount per Unit',

                'StockArticlelot.slsGrossPrcPreTax.title':'Total SP PreTax',
                'StockArticlelot.slsNetPrcPreTax.title':'Total Net SP PreTax',
                'StockArticlelot.slsUnitPrcCur.title':'Currency SP per Unit',
                'StockArticlelot.slsUnitPrcPreTax.title':'SP PreTax per Unit',
                'StockArticlelot.slsUnitPrcTaxIncl.title':'SP TaxIncl. per Unit',
                'StockArticlelot.slsNetPrcTaxIncl.title':'SP Net TaxIncl.',
                'StockArticlelot.slsVatPct.title':'SP VAT Percent',
                'StockArticlelot.slsVatAmt.title':'SP VAT Amount',
                'StockArticlelot.slsVatUnitAmt.title':'SP VAT Amount per Unit',


                'StockArticlelot.title':'Articles Lot',
                'StockArticlelots.title':'Articles Lots',


                //---------------Section------------------//
                'StockSection.code.title':'Section Code',
                'StockSection.name.title': 'Name',
                'StockSection.position.title': 'Position',
                'StockSection.geoCode.title': 'Geo. Code',
                'StockSection.wharehouse.title':'Wharehouse',
                'StockSection.title':'Section',
                'StockSections.title':'Sections',
                'StkSection_parentCode_description.title':'Parent Section Code',
                'StockSection_type.title':'Section Type',


                //--------------StkMvnt----------------//
                'StkMvnt.code.title':'Stock Mvnt Code',
                'StkMvnt.lotPic.title':'Lot PIC',
                'StkMvnt.artPic.title':'PIC',
                'StkMvnt.section.title':'Section',
                'StkMvnt.acsngUser.title':'User',
                'StkMvnt.acsngDt.title':'Date',
                'StkMvnt.mvntType.title':'Mvnt. Type',
                'StkMvnt.beforeQty.title':'Initial Qty',
                'StkMvnt.trgtQty.title':'Moved Qty',
                'StkMvnt.afterQty.title':'Finale Qty',
                'StkMvnt.mvntOrigin.title':'Mvnt. Origin',
                'StkMvnt.mvntDest.title':'Mvnt. Dest.',
                'StkMvnt.mvntOriginIdentif.title':'Mvnt. Origin Identif',
                'StkMvnt.mvntDestIdentif.title':'Mvnt. Dest. Identif',
                'StkMvnt.origDocNbrs.title':'Origin Doc Types and Nbrs',
                'StkMvnt.origProcs.title':'Origin Process',
                'StkMvnt.origProcsNbr.title':'Origin Process Nbr',
                'StkMvnt.title':'Stock Movement',
                'StkMvnts.title':'Stock Movements',
                'StkMvnt.name.title': 'Name',
                "InvInvtryItem_artName_description.title":"Article Name",
                "Entity_out.title":"Out",
                "Entity_in.title":"In",
                "Entity_transfer.title":"Transfer",


                "StkMvtTerminal_CUSTOMER_description.text":"Customer",
                "StkMvtTerminal_CUSTOMER_description.title":"Customer",
                "StkMvtTerminal_SUPPLIER_description.text":"Supplier",
                "StkMvtTerminal_SUPPLIER_description.title":"Supplier",
                "StkMvtTerminal_TRASH_description.text":"Trash",
                "StkMvtTerminal_TRASH_description.title":"Trash",
                "StkMvtTerminal_WAREHOUSE_description.text":"Warehouse.",
                "StkMvtTerminal_WAREHOUSE_description.title":"Warehouse",
                "StkMvtTerminal_description.text":"An origin or the destination of a stock movement",
                "StkMvtTerminal_description.title":"Stock Movement Endpoint",
                "StkMvtTerminal_POS_description.title":"POS",
                "StkMvtTerminal_DONNATION_descriptionn.title":"Donnation",
                "StkMvtTerminal_SAMPLE_description.title":"Sample",
                "StkMvtTerminal_EXPIRING_description.title":"Expiring",
                "StkMvtTerminal_NONE_description.title":"None",

                LANG_FR: 'French',
                LANG_EN: 'English'
            });

            //------------french----------------

            $translateProvider.translations('fr', {

                //------------Common translations-----------//
            	"Action.title": 'Action',
                'app.core.list.title':'Liste',
                'app.core.new.title':'Creer',
                'app.core.create.title':'Creer',
                'app.core.delete.title':'Supprimer',
                'app.core.save.title':'Enregistrer',
                'app.core.edit.title':'Editer',
                'app.core.back.title':'Retour',
                'app.core.view.title':'Voir',
                'app.core.cancel.title':'Annuler',
                'app.core.search.title':'Rechercher',
                'app.core.clear.title':'Effacer',
                'app.core.delete.confirmation.title':'Etes-vous sûr de vouloir supprimer ceci: \n',


                //--------------Article Lot---------------//
                'StockArticlelot.lotPic.title': 'CIP Lot',
                'StockArticlelot.artPic.title': 'CIP',
                'StockArticlelot.section.title': 'Emplacement',
                'StockArticlelot.name.title': 'Designation',
                'StockArticlelot.acsngUser.title':'Utilisateur création',
                'StockArticlelot.expirDt.title':'Date d\'expiration',
                'StockArticlelot.valueDt.title':'Date Création',
                'StockArticlelot.supplier.title':'Fournisseur',
                'StockArticlelot.supplierPic.title':'Code Fournisseur',
                'StockArticlelot.stkUnitValCur.title':'Devise Unite',
                'StockArticlelot.trgtQty.title':'Qté de Lot',
                'StockArticlelot.stkgDt.title':'Date Stkg.',
                'StockArticlelot.deliveredTrgtQty.title': 'Qté de Lot livrée',
                'StockArticlelot.salesPrice.title': 'Prix de vente',
                'StockArticlelot.buyingPrice.title': "Prix d'achat",

                'StockArticlelot.prchGrossPrcPreTax.title':'PA HT Total',
                'StockArticlelot.prchNetPrcPreTax.title':'PA Net HT Total',
                'StockArticlelot.prchUnitPrcCur.title':'Dévise PA Unité',
                'StockArticlelot.prchUnitPrcPreTax.title':'PA HT Unit.',
                'StockArticlelot.prchUnitPrcTaxIncl.title':'PA TTC Unit.',
                'StockArticlelot.prchVatPct.title':'TVA PA en %',
                'StockArticlelot.prchVatAmt.title':'TVA PA en montant',
                'StockArticlelot.prchVatUnitAmt.title':'TVA PA Unit. en montant',

                'StockArticlelot.slsGrossPrcPreTax.title':'PV HT Total',
                'StockArticlelot.slsNetPrcPreTax.title':'PV Net HT Total',
                'StockArticlelot.slsUnitPrcCur.title':'Dévise PV Unité',
                'StockArticlelot.slsUnitPrcPreTax.title':'PV HT Unit.',
                'StockArticlelot.slsUnitPrcTaxIncl.title':'PV TTC Unit.',
                'StockArticlelot.slsNetPrcTaxIncl.title':'PV Net TTC',
                'StockArticlelot.slsVatPct.title':'TVA PV en %',
                'StockArticlelot.slsVatAmt.title':'TVA PV en montant',
                'StockArticlelot.slsVatUnitAmt.title':'TVA PV Unit. en montant',

                'StockArticlelot.title.title':'Lot Articles',
                'StockArticlelots.title':'Lots Articles',


                //-------------Section------------------//
                'StockSection.code.title':'Code Section',
                'StockSection.name.title': 'Nom',
                'StockSection.position.title': 'Position',
                'StockSection.geoCode.title': 'Code Geo.',
                'StockSection.wharehouse.title':'Magasin',
                'StockSection.title':'Section',
                'StockSections.title':'Sections',
                'StkSection_parentCode_description.title':'Parent Code Rayon',
                'StockSection_type.title':'Type Section',

                //--------------StkMvnt----------------//
                'StkMvnt.code.title':'Code Mvnt de Stock',
                'StkMvnt.lotPic.title':'CIP Lot',
                'StkMvnt.artPic.title':'CIP',
                'StkMvnt.section.title':'Section',
                'StockArticlelot.sectionName.title': 'Nom Section',
                'StkMvnt.acsngUser.title':'Utilisateur',
                'StkMvnt.acsngDt.title':'Date',
                'StkMvnt.mvntType.title':'Type Mvnt.',
                'StkMvnt.beforeQty.title':'Qté initiale',
                'StkMvnt.trgtQty.title':'Qté déplacée',
                'StkMvnt.afterQty.title':'Qté finale',
                'StkMvnt.mvntOrigin.title':'Origine Mvnt.',
                'StkMvnt.mvntDest.title':'Dest. Mvnt',
                'StkMvnt.mvntOriginIdentif.title':'Identif Mvnt. Origine',
                'StkMvnt.mvntDestIdentif.title':'Identif Mvnt. Dest.',
                'StkMvnt.origDocNbrs.title':'Types Doc et Num Origine',
                'StkMvnt.origProcs.title':'Process Origine',
                'StkMvnt.origProcsNbr.title':'Num Process Origine',
                'StkMvnt.title':'Mouvement de Stock',
                'StkMvnts.title':'Mouvements de Stock',
                'StkMvnt.name.title': 'Designation',
                "Entity_out.title":"Sortir",
                "Entity_in.title":"Entrer",
                "Entity_transfer.title":"Transferer",

                "StkMvtTerminal_CUSTOMER_description.text":"Client",
            "StkMvtTerminal_CUSTOMER_description.title":"Client",
            "StkMvtTerminal_SUPPLIER_description.text":"Forunisseur",
            "StkMvtTerminal_SUPPLIER_description.title":"Fournisseur",
            "StkMvtTerminal_TRASH_description.text":"Poubelle",
            "StkMvtTerminal_TRASH_description.title":"Poubelle",
            "StkMvtTerminal_WAREHOUSE_description.text":"Magasin.",
            "StkMvtTerminal_WAREHOUSE_description.title":"Magasin",
                "StkMvtTerminal_POS_description.title":"POS",
                "StkMvtTerminal_DONNATION_descriptionn.title":"Donnation",
                "StkMvtTerminal_SAMPLE_description.title":"Simple",
                "StkMvtTerminal_EXPIRING_description.title":"Expirer",
                "StkMvtTerminal_NONE_description.title":"Aucun",
            "StkMvtTerminal_description.text":"Origine ou la destination du movement de stock.",
             "StkMvtTerminal_description.title":"Origine ou Destination du Mouvement Stock",
                "InvInvtryItem_artName_description.title":"Nom Article",


            LANG_FR: 'Francais',
                LANG_EN: 'Englais'
            });
            $translateProvider.preferredLanguage('en');
        });

})();
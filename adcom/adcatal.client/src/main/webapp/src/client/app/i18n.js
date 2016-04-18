/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('adcatal')
        .config(function ($translateProvider) {

            $translateProvider.translations('en', {
                LANG_FR: 'French',
                LANG_EN: 'English',
                logout: 'logout',
                "Action.title": 'Action',
                "Boolean.true": 'yes',
                "Boolean.false": 'no',
                'app.core.create.title':'Create',
                //--------------Article---------------
                'CatalArticle.sppu': 'Sales Price',
                'CatalArticle.artName': 'Name',
                'CatalArticle.maxDisctRate': 'Max Discount',
                'CIP':'Product Code',
                'CatalArticle.minStockQty': 'Minimum Quantity',
                'CatalArticle.maxStockQty': 'Maximum Quantity',
                'CatalArticle.vatRate': 'VAT Rate',
                'CatalArticle.active': 'Active',
                'CatalArticle.authorizedSale': 'Authorized Sale',
                'CatalArticle.title':'Articles',
                'CatalArticle.new.title':'New Article',
                'CatalArticle.list.title':'List Articles',
                'CatalArticle.delete.title':'Delete',
                'CatalArticle.cancel.title':'Cancel',
                'CatalArticle.save.title':'Save',
                'CatalArticle.edit.title':'Edit',
                'CatalArticle.view.title':'View',
                'CatalArticle.back.title':'Back',
                'CatalArticle.prodFmly': 'Family',

                //-------------CatalArt2ProductFamily---------
                'CatalArt2ProductFamily.artPic': 'PIC',
                'CatalArt2ProductFamily.famCode': 'Family Code',
                'CatalArt2ProductFamily.title':'Product Family',
                'CatalArt2ProductFamily.new.title':'New Product Family',
                'CatalArt2ProductFamily.list.title':'List Product Family',
                'CatalArt2ProductFamily.delete.title':'Delete',
                'CatalArt2ProductFamily.cancel.title':'Cancel',
                'CatalArt2ProductFamily.save.title':'Save',
                'CatalArt2ProductFamily.edit.title':'Edit',
                'CatalArt2ProductFamily.view.title':'View',
                'CatalArt2ProductFamily.back.title':'Back',

                //------------CatalArtDetailConfig-------------
                'CatalArtDetailConfig.title':'Article Detail',
                'cntnrIdentif': 'Code',
                'CatalArtDetailConfig.qualifier': 'Qualifier',
                'CatalArtDetailConfig.qtyOfDtldInMain': 'Quantity',
                'CatalArtDetailConfig.sppu': 'Price',
                'CatalArtDetailConfig.mngInPptn': 'Managed in Proportion',
                'CatalArtDetailConfig.artIdentif': 'Article Record',
                'CatalArtDetailConfig.new.title':'New Configuration',
                'CatalArtDetailConfig.list.title':'List Configurations',
                'CatalArtDetailConfig.delete.title':'Delete',
                'CatalArtDetailConfig.cancel.title':'Cancel',
                'CatalArtDetailConfig.save.title':'Save',
                'CatalArtDetailConfig.edit.title':'Edit',
                'CatalArtDetailConfig.view.title':'View',
                'CatalArtDetailConfig.back.title':'Back',

                //-------------CatalArtEquivalence------------
                'CatalArtEquivalence.title':'Article Equivalence',
                'CatalArtEquivalence.mainArtIdentif': 'Main Article',
                'CatalArtEquivalence.equivArtIdentif': 'Equivalent Article',
                'CatalArtEquivalence.usage': 'Usage',
                'CatalArtEquivalence.new.title':'New Article Equivalence',
                'CatalArtEquivalence.list.title':'List Article Equivalence',
                'CatalArtEquivalence.delete.title':'Delete',
                'CatalArtEquivalence.cancel.title':'Cancel',
                'CatalArtEquivalence.save.title':'Save',
                'CatalArtEquivalence.edit.title':'Edit',
                'CatalArtEquivalence.view.title':'View',
                'CatalArtEquivalence.back.title':'Back',
                'CatalArtEquivalence_artEquivCode_description.title':'Code of Equivalent Article',

                //------------CatalArtLangMapping--------------
                'CatalArtFeatMapping.title':'Article Features',
                'CatalArtFeatMapping.artName': 'Name',
                'CatalArtFeatMapping.shortName': 'ShortName',
                'langIso2': 'Language Code',
                'CatalAbstractFeatMapping.purpose': 'Purpose',
                'CatalAbstractFeatMapping.usage': 'Usage Mode',
                'CatalAbstractFeatMapping.warnings': 'Warnings',
                'CatalAbstractFeatMapping.substances': 'Substances',
                'CatalAbstractFeatMapping.new.title':'New Entry',
                'CatalAbstractFeatMapping.list.title':'List Entries',
                'CatalAbstractFeatMapping.delete.title':'Delete',
                'CatalAbstractFeatMapping.cancel.title':'Cancel',
                'CatalAbstractFeatMapping.save.title':'Save',
                'CatalAbstractFeatMapping.edit.title':'Edit',
                'CatalAbstractFeatMapping.view.title':'View',
                'CatalAbstractFeatMapping.back.title':'Back',

                //-----------CatalPicMapping--------------------
                'CatalPicMapping.title': 'Table of Product Code',
                'CatalPicMapping.artIdentif': 'Article Identifier',
                'CatalPicMapping.code': 'Code',
                'CatalPicMapping.codeOrigin': 'Origin of Code',
                'CatalPicMapping.bpName': 'Partner Name',
                'CatalPicMapping.bpIdentif': 'Partner Identifier',
                'CatalPicMapping.warrantyMonths': 'Warranty in Months',
                'CatalPicMapping.returnDays': 'Return Time in Days',
                'CatalPicMapping.unitPrice': 'Price',
                'CatalPicMapping.currIso3': 'Currency',
                'CatalPicMapping.vatRate': 'VAT Rate',
                'CatalPicMapping.maxDisctRate': 'Max Discount',
                'CatalPicMapping.addInfo': 'Additional Info',
                'CatalPicMapping.new.title':'Add Code',
                'CatalPicMapping.list.title':'List Codes',
                'CatalPicMapping.delete.title':'Delete',
                'CatalPicMapping.cancel.title':'Cancel',
                'CatalPicMapping.save.title':'Save',
                'CatalPicMapping.edit.title':'Edit',
                'CatalPicMapping.view.title':'View',
                'CatalPicMapping.back.title':'Back',

                //------------CatalProductFamily---------------
                'CatalProductFamily_identif_description.title':'Family Identifier',
                'CatalProductFamily.title': 'Product Family',
                'CatalProductFamily.parentIdentif': 'Parent Family',
                'CatalProductFamily.famPath': 'Family Path',
                'CatalProductFamily.new.title':'New Family',
                'CatalProductFamily.list.title':'List Families',
                'CatalProductFamily.delete.title':'Delete',
                'CatalProductFamily.cancel.title':'Cancel',
                'CatalProductFamily.save.title':'Save',
                'CatalProductFamily.edit.title':'Edit',
                'CatalProductFamily.view.title':'View',
                'CatalProductFamily.back.title':'Back',
                'CatalProductFamily.artName':'Article Name',

                //-----------CatalProductFamilyLangMap-----------
                'CatalFamilyFeatMaping_description.title': 'Family Features',
                'CatalFamilyFeatMaping.title': 'Family Features',
                'CatalFamilyFeatMaping.famPath': 'Path',
                'CatalFamilyFeatMaping.new.title':'New Entry',
                'CatalFamilyFeatMaping.list.title':'List Entries',
                'CatalFamilyFeatMaping.delete.title':'Delete',
                'CatalFamilyFeatMaping.cancel.title':'Cancel',
                'CatalFamilyFeatMaping.save.title':'Save',
                'CatalFamilyFeatMaping.edit.title':'Edit',
                'CatalFamilyFeatMapings.view.title':'View',
                'CatalFamilyFeatMapings.back.title':'Back',
                'CatalFamilyFeatMaping_familyName_description.title':'Name',
                'CatalFamilyFeatMaping_shortName_description.title':'Shortname',

                //-----CatalCipOrigine ---------------
                'CatalCipOrigine_MAIN_description':'Main',
                'CatalCipOrigine_SUPPLIER_description':'Supplier',
                'CatalCipOrigine_DETAIL_description':'Detail',
                'CatalCipOrigine_MANUFACTURER_description':'Manufacturer',
                'CatalCipOrigine_RESALER_description':'Resaler',
                'CatalCipOrigine_GOVERNMENT_description':'Governement',
                'CatalCipOrigine_BROKER_description':'Broker',
                'CatalCipOrigine_CUSTOMERSERVICE_description':'Customer Service',
                'CatalCipOrigine_CUSTOMER_description':'Customer',
                'CatalCipOrigine_INSURANCE_description':'Insurance',

                //------------Common translations-----------//

                'app.core.search.title':'Search',
                'app.core.clear.title':'Clear',
                'existin_lang':'There is a entity with this language!',
                "Cip_or_name":"Name or CIP article"

            });

            $translateProvider.preferredLanguage('en');
        });

})();

/* global toastr:false, moment:false */
(function() {
    'use strict';

    angular
        .module('app.core')
        .config(function ($translateProvider) {

            $translateProvider.translations('en', {
                LANG_FR: 'French',
                LANG_EN: 'English',
                logout: 'logout',

                "current_language":"en",
                "navbar_loginName":"Login Name",
                "navbar_ouWsIdentif":"Workspace",
                "navbar_user":"User",
                "navbar_logOut":"Logout",
                "navbar_appMenu":"Menu",

                "Entity_leave.title": "leave",
                "Entity_change.title": "Change  ",
                "Entity_activate.title": "Activate ",
                "Entity_desactivate.title": "Deactivate ",
                "Entity_block.title": "Block ",
                "Entity_unblock.title": "unBlock ",
                "Entity_create.title": "Create",
                "Entity_delete.title": "Delete",
                "Entity_save.title": "Save",
                "Entity_saveleave.title":"Save and leave",
                "Entity_search.title": "Search",
                "Entity_update.title": "Update",
                "Entity_edit.title": "Edit",
                "Entity_cancel.title": "Cancel",
                "Entity_Finish.title": "Finish",
                "Entity_reset.title": "Reset",
                "Entity_ok.title": "Validate",
                "Entity_yes.title": "Yes",
                "Entity_no.title": "No",
                "Entity_select.title": "Select",
                "Entity_add.title": "Add",
                "Entity_show.title": "Show",
                "Entity_export.title":"Export",
                "Entity_confirm.title": "Confirm",
                "Window_close.title": "Close",
                "Entity_login.title": "Login",
                "Entity_userName.title": "User Name",
                "Entity_password.title": "Password",
                "Entity_print.title": "Print",
                "Entity_next.title": "Next",
                "Entity_first.title": "First",
                "Entity_last.title": "Last",
                "Entity_reprint.title": "Reprint",
                "Entity_previous.title": "Previous",
                "Entity_back.title": "Back",
                "Entity_validFrom.title":"Valid from",
                "Entity_validTo.title":"Valid to",
                "Entity_From.title":"From",
                "Entity_To.title":"To",
                "Entity_Result.title":"Result",
                "Entity_By.title":"By",
                "Entity_Of.title":"Of",
                "Entity_required.title":"Required",
                "Entity_list.title":"List",
                "Entity_type_to_select.title":"Type to select",
                "Entity_notfound.title":"Nothing found",
                "Entity_info.title":"Info",
                "Entity_merge.title":"Merge",
                "Entity_compare.title":"Compare",
                "Entity_selected.title": "Selected",
                "Entity_recompute.title": "Recompute",
                "Entity_validate.title": "Validate",
                "Entity_reload.title": "Reload",
                "Entity_showAll.title": "Show All",
                "Entity_showConflict.title": "Only Conflicts",
                "Entity_close..title":"Close",

                "BaseCountryName_isoCode_description.title":"Country Code",
                "BaseCountryName_iso2_description.title":"Country Code Iso 2",
                "BaseCountryName_iso3_description.title":"Country Code Iso 3",
                "BaseCountryName_langsIso2_description.title":"Language",
                "BaseCountryName_name_description.title":"Country Name",

                "BaseGender_FEMALE_description.title":"Female",
                "BaseGender_MALE_description.title":"Male",

                "BaseLanguage_fr_description.title":"French",
                "BaseLanguage_en_description.title":"English",

                "workspace_loginName":"Login Name",
                "workspace_ouWsIdentif":"Workspace",
                "workspace_admin":"ADMINISTRATION",
                "workspace_logOut":"Logout",
                "navbar_go_to":"Go to module"

            });
// francais --------------------------------------------------------------------------------
            $translateProvider.translations('fr', {
                LANG_FR: 'Francais',
                LANG_EN: 'Englais',
                logout: 'deconnection',
                "current_language":"fr",

                "navbar_loginName":"Nom Connection",
                "navbar_ouWsIdentif":"Espace de Travaille",
                "navbar_user":"Utilisateur",
                "navbar_logOut":"Deconnexion",
                "navbar_appMenu":"Menu",
                "navbar_go_to":"Go to module",

                "Login_partner_logOut.title":"Deconnecter",
                "Entity_change.title":"Changer  ",
                "Entity_activate.title":"Activer ",
                "Entity_desactivate.title":"Desactiver ",
                "Entity_block.title":"Bloquer ",
                "Entity_unblock.title":"Debloquer ",
                "Entity_create.title":"Créer",
                "Entity_delete.title":"Surpimer",
                "Entity_save.title":"Enregistrer",
                "Entity_saveleave.title":"Enregistrer et Quitter",
                "Entity_search.title":"Rechercher",
                "Entity_export.title":"Exporter",
                "Entity_update.title":"Actualiser",
                "Entity_edit.title":"Modifier",
                "Entity_cancel.title":"Annuler",
                "Entity_reset.title":"Remettre",
                "Entity_ok.title":"Valider",
                "Entity_show.title":"Visualiser",
                "Entity_yes.title":"Oui",
                "Entity_no.title":"Non",
                "Entity_select.title":"Sélectionner",
                "Entity_add.title":"Ajouter",
                "Entity_confirm.title":"Confirmer",
                "Entity_reprint.title":"Réimprimer",
                "Window_close.title":"Fermer",
                "Entity_leave.title":"Quitter",
                "Entity_login.title":"Connexion",
                "Entity_userName.title":"Nom de Connexion",
                "Entity_password.title":"Mot de Passe",
                "Entity_change_password.title":"Changer Mot de Passe",
                "Entity_Old_password.title":"Ancien Mot de Passe",
                "Entity_New_password.title":"Nouveau Mot de Passe",
                "Entity_print.title":"Imprimer",
                "Entity_next.title":"Suivant",
                "Entity_first.title": "Premier",
                "Entity_last.title": "Dernier",
                "Entity_Finish.title":"Terminer",
                "Entity_previous.title":"Précédent",
                "Entity_back.title":"Retour",
                "Entity_validFrom.title":"Valide du",
                "Entity_validTo.title":"Valide au",
                "Entity_Result.title":"Résultat",
                "Entity_By.title":"Par",
                "Entity_Of.title":"Du",
                "Entity_From.title":"Du",
                "Entity_To.title":"Au",
                "Entity_required.title":"Requis",
                "Entity_list.title":"Liste",
                "Entity_type_to_select.title":"Saisir pour sélectioner",
                "Entity_notfound.title":"Pas de Résultat",
                "Entity_info.title":"Info",
                "Entity_merge.title":"Fusionner",
                "Entity_compare.title":"Comparer",
                "Entity_selected.title": "Selectioné",
                "Entity_recompute.title": "Recalculer",
                "Entity_validate.title": "Valider",
                "Entity_reload.title": "Recharger",
                "Entity_showAll.title": "Toutes les Lignes",
                "Entity_showConflict.title": "Seulement les Conflits",
                "Entity_close..title":"Cloturer",

                "BaseCountryName_isoCode_description.title":"Code Pays",
                "BaseCountryName_iso2_description.title":"Code Pays Iso 2",
                "BaseCountryName_iso3_description.title":"Code Pays Iso 3",
                "BaseCountryName_langsIso2_description.title":"Langue",
                "BaseCountryName_name_description.title":"Nom du Pays",

                "BaseGender_FEMALE_description.title":"Feminin",
                "BaseGender_MALE_description.title":"Masculin",

                "BaseLanguage_fr_description.title":"Français",
                "BaseLanguage_en_description.title":"Englais",

                "workspace_loginName":"Nom de Connexion",
                "workspace_ouWsIdentif":"Espace de Travaille",
                "workspace_admin":"ADMINISTRATION",
                "workspace_logOut":"Deconnexion"

            });
            $translateProvider.preferredLanguage('en');
        });

})();

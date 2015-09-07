package org.keycloak.services.validation;

import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.services.messages.Messages;

import javax.ws.rs.core.MultivaluedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Validation {

    public static final String FIELD_PASSWORD_CONFIRM = "password-confirm";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_LAST_NAME = "lastName";
    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_USERNAME = "username";
    
    // Actually allow same emails like angular. See ValidationTest.testEmailValidation()
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9!#$%&'*+/=?^_`{|}~.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*");

    public static List<FormMessage> validateRegistrationForm(RealmModel realm, MultivaluedMap<String, String> formData, List<String> requiredCredentialTypes, PasswordPolicy policy) {
        List<FormMessage> errors = new ArrayList<>();

        if (!realm.isRegistrationEmailAsUsername() && isBlank(formData.getFirst(FIELD_USERNAME))) {
            addError(errors, FIELD_USERNAME, Messages.MISSING_USERNAME);
        }

        if (isBlank(formData.getFirst(FIELD_FIRST_NAME))) {
            addError(errors, FIELD_FIRST_NAME, Messages.MISSING_FIRST_NAME);
        }

        if (isBlank(formData.getFirst(FIELD_LAST_NAME))) {
            addError(errors, FIELD_LAST_NAME, Messages.MISSING_LAST_NAME);
        }

        if (isBlank(formData.getFirst(FIELD_EMAIL))) {
            addError(errors, FIELD_EMAIL, Messages.MISSING_EMAIL);
        } else if (!isEmailValid(formData.getFirst(FIELD_EMAIL))) {
            addError(errors, FIELD_EMAIL, Messages.INVALID_EMAIL);
        }

        if (requiredCredentialTypes.contains(CredentialRepresentation.PASSWORD)) {
            if (isBlank(formData.getFirst(FIELD_PASSWORD))) {
                addError(errors, FIELD_PASSWORD, Messages.MISSING_PASSWORD);
            } else if (!formData.getFirst(FIELD_PASSWORD).equals(formData.getFirst(FIELD_PASSWORD_CONFIRM))) {
                addError(errors, FIELD_PASSWORD_CONFIRM, Messages.INVALID_PASSWORD_CONFIRM);
            }
        }

        if (formData.getFirst(FIELD_PASSWORD) != null) {
            PasswordPolicy.Error err = policy.validate(realm.isRegistrationEmailAsUsername()?formData.getFirst(FIELD_EMAIL):formData.getFirst(FIELD_USERNAME), formData.getFirst(FIELD_PASSWORD));
            if (err != null)
                errors.add(new FormMessage(FIELD_PASSWORD, err.getMessage(), err.getParameters()));
        }
        
        return errors;
    }
    
    private static void addError(List<FormMessage> errors, String field, String message){
        errors.add(new FormMessage(field, message));
    }

    public static List<FormMessage> validateUpdateProfileForm(MultivaluedMap<String, String> formData) {
        return validateUpdateProfileForm(null, formData);
    }

    public static List<FormMessage> validateUpdateProfileForm(RealmModel realm, MultivaluedMap<String, String> formData) {
        List<FormMessage> errors = new ArrayList<>();
        
        if (realm != null && realm.isEditUsernameAllowed() && isBlank(formData.getFirst(FIELD_USERNAME))) {
            addError(errors, FIELD_USERNAME, Messages.MISSING_USERNAME);
        }

        if (isBlank(formData.getFirst(FIELD_FIRST_NAME))) {
            addError(errors, FIELD_FIRST_NAME, Messages.MISSING_FIRST_NAME);
        }

        if (isBlank(formData.getFirst(FIELD_LAST_NAME))) {
            addError(errors, FIELD_LAST_NAME, Messages.MISSING_LAST_NAME);
        }

        if (isBlank(formData.getFirst(FIELD_EMAIL))) {
            addError(errors, FIELD_EMAIL, Messages.MISSING_EMAIL);
        } else if (!isEmailValid(formData.getFirst(FIELD_EMAIL))) {
            addError(errors, FIELD_EMAIL, Messages.INVALID_EMAIL);
        }

        return errors;
    }
    
    /**
     * Validate if user object contains all mandatory fields.
     * 
     * @param realm user is for
     * @param user to validate
     * @return true if user object contains all mandatory values, false if some mandatory value is missing
     */
    public static boolean validateUserMandatoryFields(RealmModel realm, UserModel user){
        return!(isBlank(user.getFirstName()) || isBlank(user.getLastName()) || isBlank(user.getEmail()));        
    }

    /**
     * Check if string is empty (null or lenght is 0)
     * 
     * @param s to check
     * @return true if string is empty
     */
    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
    
    /**
     * Check if string is blank (null or lenght is 0 or contains only white characters)
     * 
     * @param s to check
     * @return true if string is blank
     */
    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }


}

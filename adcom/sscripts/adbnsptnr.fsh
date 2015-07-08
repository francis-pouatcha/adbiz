set ACCEPT_DEFAULTS true;

new-project --named adbnsptnr.server --topLevelPackage org.adorsys.adbnsptnr --finalName adbnsptnr.server --projectFolder adbnsptnr.server;

as7 setup;

persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

set ACCEPT_DEFAULTS false;

enum setup;

repogen setup;

cd ~~;

@/* Type Business Partner*/;
java new-enum-type --named BpPtnrType --package ~.jpa;
enum add-enum-class-description --title "Partner Type" --text "Type of this business partner.";
enum add-enum-class-description  --locale fr --title "Type de partenaire" --text "Type de partenaire.";
java new-enum-const INDIVIDUAL;
enum add-enum-constant-description --onConstant INDIVIDUAL --title "Individual" --text "Individual person";
enum add-enum-constant-description --locale fr --onConstant INDIVIDUAL --title "Physique" --text "Personnalié physique";
java new-enum-const LEGAL;
enum add-enum-constant-description --onConstant LEGAL --title "Legal" --text "Legal entity";
enum add-enum-constant-description --locale fr --onConstant LEGAL --title "Morale" --text "Personnalité juridique";

cd ~~;

@/* Type Business Partner*/;
java new-enum-type --named BpPtnrRole --package ~.jpa;
enum add-enum-class-description --title "Partner Role" --text "Role of this business partner.";
enum add-enum-class-description --locale fr --title "Role du Partenaire" --text "Role de partenaire.";
java new-enum-const CUSTOMER;
enum add-enum-constant-description --onConstant CUSTOMER --title "Customer" --text "Customer";
enum add-enum-constant-description --onConstant CUSTOMER --title "Client" --text "Client" --locale fr;
java new-enum-const INSURANCE;
enum add-enum-constant-description --onConstant INSURANCE --title "Insurance" --text "Insurance";
enum add-enum-constant-description --onConstant INSURANCE --title "Assurance" --text "Assurance" --locale fr;
java new-enum-const SUPPLIER;
enum add-enum-constant-description --onConstant SUPPLIER --title "Supplier" --text "Supplier";
enum add-enum-constant-description --locale fr --onConstant SUPPLIER --title "Fournisseur" --text "Fournisseur";
java new-enum-const EMPLOYER;
enum add-enum-constant-description --onConstant EMPLOYER --title "Employer" --text "Employer";
enum add-enum-constant-description --onConstant EMPLOYER --title "Employeur" --text "Employeur" --locale fr;
java new-enum-const STAFF;
enum add-enum-constant-description --onConstant STAFF --title "Staff" --text "Staff";
enum add-enum-constant-description --onConstant STAFF --title "Personnel" --text "Personnel" --locale fr;
java new-enum-const MANUFACTURER;
enum add-enum-constant-description --onConstant MANUFACTURER --title "Manufacturer" --text "Supplier";
enum add-enum-constant-description --onConstant MANUFACTURER --title "Fabricant" --text "Fabricant" --locale fr;
java new-enum-const GOVERNMENT;
enum add-enum-constant-description --onConstant GOVERNMENT --title "Government" --text "Government";
enum add-enum-constant-description --onConstant GOVERNMENT --title "Gouvernement" --text "Gouvernement" --locale fr;
java new-enum-const BROKER;
enum add-enum-constant-description --onConstant BROKER --title "Broker" --text "Broker";
enum add-enum-constant-description --onConstant BROKER --title "Courtier" --text "Courtier" --locale fr;
java new-enum-const SHAREHOLDER;
enum add-enum-constant-description --onConstant SHAREHOLDER --title "Shareholder" --text "Shareholder";
enum add-enum-constant-description --onConstant SHAREHOLDER --title "Actionaire" --text "Actionaire" --locale fr;
java new-enum-const BANKER;
enum add-enum-constant-description --onConstant BANKER --title "Banker" --text "Banker";
enum add-enum-constant-description --onConstant BANKER --title "Banquier" --text "Banquier" --locale fr;

cd ~~;

@/* Business Partner Category */;
entity --named BpPtnrCtgry --package ~.jpa;
description add-class-description --title "Customer Category" --text "Partner categories details";
description add-class-description  --locale fr --title "Categorie Client" --text "Details sur categorie";

field string --named ctgryCode;
description add-field-description --onProperty ctgryCode --title "Category Code" --text "The code of this client category";
description add-field-description --onProperty ctgryCode --title "Code Categorie" --text "Le code de cette categorie client" --locale fr;
constraint NotNull --onProperty ctgryCode;

field string --named parentCode;
description add-field-description --onProperty parentCode --title "parent Code" --text "The code of the parent category";
description add-field-description --onProperty parentCode --title "Code Parent" --text "Le code du parent de cette categorie" --locale fr;
constraint NotNull --onProperty parentCode;

field custom --named ptnrRole --type ~.jpa.BpPtnrRole;
description add-field-description --onProperty ptnrRole --title "Partner Role" --text "The Partner Role.";
description add-field-description --onProperty ptnrRole --title "Role du Partenaire" --text "Role du Partenaire" --locale fr;
enum enumerated-field --onProperty ptnrRole ;

cd ~~;

@/* Business Partner Category */;
entity --named BpPtnrCtgryDtls --package ~.jpa;
description add-class-description --title "Partner Category Details" --text "Partner Category Details";
description add-class-description  --locale fr --title "Details sur Categorie Client" --text "Details sur categorie";

field string --named ctgryCode;
description add-field-description --onProperty ctgryCode --title "Category Code" --text "The code of this client category";
description add-field-description --onProperty ctgryCode --title "Code Categorie" --text "Le code de cette categorie client" --locale fr;
constraint NotNull --onProperty ctgryCode;

field string --named langIso2;
description add-field-description --onProperty langIso2 --title "Language Code" --text "Language Code";
description add-field-description --onProperty langIso2 --title "Code Language" --text "Code Language" --locale fr;
constraint NotNull --onProperty langIso2;

field string --named name;
description add-field-description --onProperty name --title "Category Name" --text "The name of this client category";
description add-field-description --onProperty name --title "Libelle Categorie" --text "Le nom de cette categorie client" --locale fr;
constraint NotNull --onProperty name;

field string --named description;
description add-field-description --onProperty description --title "Description" --text "Description of this client category.";
description add-field-description --onProperty description --title "Description" --text "Description de cette categorie client." --locale fr;
constraint Size --onProperty description --max 256;

cd ~~;

@/* Business Partner */;
entity --named BpBnsPtnr --package ~.jpa;
description add-class-description --title "Business Partner" --text "The business partner";
description add-class-description  --locale fr --title "Partenaire commercial" --text "Partenaire commercial";

field string --named ptnrNbr;
description add-field-description --onProperty ptnrNbr --title "Identifier" --text "Identifier of this Partner";
description add-field-description --onProperty ptnrNbr --title "Identifiantr" --text "Identifiant de ce Partenaire" --locale fr;
constraint NotNull --onProperty ptnrNbr;

field custom --named ptnrType --type ~.jpa.BpPtnrType;
description add-field-description --onProperty ptnrType --title "Partner Type" --text "The client type.";
description add-field-description --onProperty ptnrType --title "Type Partenaire" --text "Le type de client." --locale fr;
enum enumerated-field --onProperty ptnrType ;
@/* enumeration{PHYSIQUE, MORAL} */;

field string --named ctryOfRsdnc;
description add-field-description --onProperty ctryOfRsdnc --title "Country Of Residence" --text "Country Of Residence";
description add-field-description --onProperty ctryOfRsdnc --title "Pays de Residence" --text "Pays de Residence" --locale fr;

cd ~~;

@/* Business Partner Name*/;
entity --named BpIndivPtnrName --package ~.jpa;
description add-class-description --title "Individual Partner Name" --text "Individual Partner Name";
description add-class-description  --locale fr --title "Nom Partenaire Individu" --text "Nom Partenaire Individu";

field string --named ptnrNbr;
description add-field-description --onProperty ptnrNbr --title "Identifier" --text "Identifier of this Partner";
description add-field-description --onProperty ptnrNbr --title "Identifiantr" --text "Identifiant de ce Partenaire" --locale fr;
constraint NotNull --onProperty ptnrNbr;

field string --named gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this client.";
description add-field-description --onProperty gender --title "Genre" --text "Le genre de ce client." --locale fr;

field string --named firstName;
description add-field-description --onProperty firstName --title "First Name" --text "The first name of this client.";
description add-field-description --onProperty firstName --title "Nom" --text "Le prénom de ce client." --locale fr;

field string --named lastName;
description add-field-description --onProperty lastName --title "last Name" --text "The name of this client";
description add-field-description --onProperty lastName --title "Prénom" --text "Le nom de ce client" --locale fr;
constraint NotNull --onProperty lastName;
description add-notNull-message --onProperty lastName --title "The user last name is required" --text "The user last name is required";
description add-notNull-message --onProperty lastName --title "Le nom de ce client est réquis" --text "Le nom de ce client est réquis" --locale fr;

field string --named fullName;
description add-field-description --onProperty fullName --title "Full Name" --text "The full name of this client";
description add-field-description --onProperty fullName --title "Nom Complet" --text "Le nom complet de ce client" --locale fr;
constraint NotNull --onProperty fullName;

field temporal --type TIMESTAMP --named brthDt; 
description add-field-description --onProperty brthDt --title "Birth Date" --text "The birth date of this client";
description add-field-description --onProperty brthDt --title "Date de Naissance" --text "La date de naissance du client" --locale fr;

cd ~~;

@/* Business Partner Name*/;
entity --named BpLegalPtnrId --package ~.jpa;
description add-class-description --title "Legal Partner Identity" --text "Legal Partner Identity";
description add-class-description  --locale fr --title "Identité Partenaire Legal" --text "Identité Partenaire Legal";

field string --named ptnrNbr;
description add-field-description --onProperty ptnrNbr --title "Identifier" --text "Identifier of this Partner";
description add-field-description --onProperty ptnrNbr --title "Identifiantr" --text "Identifiant de ce Partenaire" --locale fr;
constraint NotNull --onProperty ptnrNbr;

field string --named cpnyName;
description add-field-description --onProperty cpnyName --title "Company Name" --text "The company Name";
description add-field-description --onProperty cpnyName --title "Raison Sociale" --text "La raison sociale" --locale fr;
constraint NotNull --onProperty cpnyName;

field string --named shortName;
description add-field-description --onProperty shortName --title "Short Name" --text "The short name of this partner";
description add-field-description --onProperty shortName --title "Nom abevié" --text "Le nom abrevié de ce prtenaire" --locale fr;
constraint NotNull --onProperty shortName;

field string --named legalForm;
description add-field-description --onProperty legalForm --title "Legal Form" --text "The legal form";
description add-field-description --onProperty legalForm --title "Forme Juridique" --text "La forme juridique" --locale fr;

field number --named equity --type java.math.BigDecimal;
description add-field-description --onProperty equity --title "Equity" --text "Equity";
description add-field-description --onProperty equity --title "Capita Social" --text "Capital Social" --locale fr;

field string --named cmrcRgstrNbr;
description add-field-description --onProperty cmrcRgstrNbr --title "Register Number" --text "Register Number";
description add-field-description --onProperty cmrcRgstrNbr --title "Registre de Commerce" --text "Registre de Commerce" --locale fr;

field string --named taxPayerIdNbr;
description add-field-description --onProperty taxPayerIdNbr --title "Tax Payer Id Number" --text "Tax Payer Id Number";
description add-field-description --onProperty taxPayerIdNbr --title "Numéro de Contribuable" --text "Numéro de Contribuable" --locale fr;

cd ~~;

@/* Business Partner Id Type*/;
java new-enum-type --named BpPtnrIdType --package ~.jpa;
enum add-enum-class-description --title "Partner Id Type" --text "Partner Id Type.";
enum add-enum-class-description  --locale fr --title "Genre Identité du partenaire" --text "Genre Identité du partenaire";
java new-enum-const IDCARDNBR;
enum add-enum-constant-description --onConstant IDCARDNBR --title "National Identity Cart Number" --text "National Identity Cart Number";
enum add-enum-constant-description --onConstant IDCARDNBR --title "Numéro Carte Identité Nationale" --text "Numéro Carte Identité Nationale" --locale fr;
java new-enum-const RESDTCARDNBR;
enum add-enum-constant-description --onConstant RESDTCARDNBR --title "Resident Card Number" --text "Resident Card Number";
enum add-enum-constant-description --onConstant RESDTCARDNBR --title "Numéro Carte de Résident" --text "Numéro Carte de Résident" --locale fr;
java new-enum-const DRIVERLICNBR;
enum add-enum-constant-description --onConstant DRIVERLICNBR --title "Driver Licence Number" --text "Driver Licence Number";
enum add-enum-constant-description --onConstant DRIVERLICNBR --title "Numéro Permit de Conduire" --text "Numéro Permit de Conduire" --locale fr;
java new-enum-const PASSPORTNBR;
enum add-enum-constant-description --onConstant PASSPORTNBR --title "Passport Number" --text "Passport Number";
enum add-enum-constant-description --onConstant PASSPORTNBR --title "Numéro Passport" --text "Numéro Passport" --locale fr;
java new-enum-const EMPLOYEENBR;
enum add-enum-constant-description --onConstant EMPLOYEENBR --title "Employee Number" --text "Employee Number";
enum add-enum-constant-description --onConstant EMPLOYEENBR --title "Numéro Employée" --text "Numéro Employée" --locale fr;
java new-enum-const MEMBERSHIP;
enum add-enum-constant-description --onConstant MEMBERSHIP --title "Membership Card Number" --text "Membership Card Number";
enum add-enum-constant-description --onConstant MEMBERSHIP --title "Numéro Carte de Membre" --text "Numéro Carte de Membre" --locale fr;
java new-enum-const INSURER;
enum add-enum-constant-description --onConstant INSURER --title "Insurer" --text "Insurer";
enum add-enum-constant-description --onConstant INSURER --title "Assurer" --text "Assurer" --locale fr;
java new-enum-const INSURED;
enum add-enum-constant-description --onConstant INSURED --title "Insured" --text "Insured";
enum add-enum-constant-description --onConstant INSURED --title "Assuré" --text "Assuré" --locale fr;

cd ~~;

@/* Business Partner Name*/;
entity --named BpPtnrIdDtls --package ~.jpa;
description add-class-description --title "Partner Identity Details" --text "Partner Identity Details";
description add-class-description  --locale fr --title "Identité Partenaire" --text "Identité Partenaire";

field string --named ptnrNbr;
description add-field-description --onProperty ptnrNbr --title "Identifier" --text "Identifier of this Partner";
description add-field-description --onProperty ptnrNbr --title "Identifiantr" --text "Identifiant de ce Partenaire" --locale fr;
constraint NotNull --onProperty ptnrNbr;

field custom --named ptnrIdType --type ~.jpa.BpPtnrIdType;
description add-field-description --onProperty ptnrIdType --title "Identity Type" --text "Identity Type";
description add-field-description --onProperty ptnrIdType --title "Genre Identité" --text "Genre Identité" --locale fr;
enum enumerated-field --onProperty ptnrIdType ;

field temporal --type TIMESTAMP --named issuedDt; 
description add-field-description --onProperty issuedDt --title "Issued Date" --text "Issued Date";
description add-field-description --onProperty issuedDt --title "Délivré le" --text "Délivré le" --locale fr;

field temporal --type TIMESTAMP --named expirdDt; 
description add-field-description --onProperty expirdDt --title "Expiration Date" --text "Expiration Date";
description add-field-description --onProperty expirdDt --title "Date Expiration" --text "Date Expiration" --locale fr;

field string --named issuedBy;
description add-field-description --onProperty issuedBy --title "Issued By" --text "Issued By";
description add-field-description --onProperty issuedBy --title "Delivré Par" --text "Délivré Par" --locale fr;

field string --named issuedIn;
description add-field-description --onProperty issuedIn --title "Issued In" --text "Issued In";
description add-field-description --onProperty issuedIn --title "Delivré A" --text "Délivré A" --locale fr;

field string --named issuingCtry;
description add-field-description --onProperty issuingCtry --title "Issuing Country" --text "Issuing Country";
description add-field-description --onProperty issuingCtry --title "Pays de Délivrance" --text "Pays de Délivrance" --locale fr;

cd ~~;

@/* Business Partner Contact*/;
entity --named BpPtnrContact --package ~.jpa;
description add-class-description --title "Partner Contact" --text "Partner Contact";
description add-class-description  --locale fr --title "Contact Partenaire" --text "Contact Partenaire";

field string --named ptnrNbr;
description add-field-description --onProperty ptnrNbr --title "Identifier" --text "Identifier of this Partner";
description add-field-description --onProperty ptnrNbr --title "Identifiantr" --text "Identifiant de ce Partenaire" --locale fr;
constraint NotNull --onProperty ptnrNbr;

field string --named langIso2;
description add-field-description --onProperty langIso2 --title "Language" --text "Language";
description add-field-description --onProperty langIso2 --title "Language" --text "language" --locale fr;
constraint NotNull --onProperty langIso2;

field string --named cntctRole;
description add-field-description --onProperty cntctRole --title "Contact Role" --text "Role of the contact";
description add-field-description --onProperty cntctRole --title "Role du Contact" --text "Role du contact" --locale fr;

field string --named description;
description add-field-description --onProperty description --title "Description" --text "Description of this contact.";
description add-field-description --onProperty description --title "Description" --text "Description du contact." --locale fr;
constraint Size --onProperty description --max 256;

cd ~~;

@/* Business Partner Credit Details*/;
entity --named BpPtnrCreditDtls --package ~.jpa;
description add-class-description --title "Partner Credit Details" --text "Partner Credit Details";
description add-class-description  --locale fr --title "Details Credit Partenaire" --text "Details Credit Partenaire";

field string --named ptnrNbr;
description add-field-description --onProperty ptnrNbr --title "Identifier" --text "Identifier of this Partner";
description add-field-description --onProperty ptnrNbr --title "Identifiantr" --text "Identifiant de ce Partenaire" --locale fr;
constraint NotNull --onProperty ptnrNbr;

field boolean --named creditAuthrzd --primitive false;
description add-field-description --onProperty creditAuthrzd --title "Credit Authorized" --text "Whether or not the customer can purchase on credit";
description add-field-description --onProperty creditAuthrzd --title "Crédit Autorisé" --text "Autorise ou non le crédit au client" --locale fr;

field number --named ttlCreditLn --type java.math.BigDecimal;
description add-field-description --onProperty ttlCreditLn --title "Max Credit" --text "Total credit line for this customer..";
description add-field-description --onProperty ttlCreditLn --title "Credit Maximum" --text "Le montant max de credit qu on peut accorder au client." --locale fr;

cd ~~;

@/* Business Partner Category */;
entity --named BpCtgryOfPtnr --package ~.jpa;
description add-class-description --title "Category of Partner" --text "Category of Partner";
description add-class-description  --locale fr --title "Category du Partenaire" --text "Category du Partenaire";

field string --named ptnrNbr;
description add-field-description --onProperty ptnrNbr --title "Identifier" --text "Identifier of this Partner";
description add-field-description --onProperty ptnrNbr --title "Identifiantr" --text "Identifiant de ce Partenaire" --locale fr;
constraint NotNull --onProperty ptnrNbr;

field string --named ctgryCode;
description add-field-description --onProperty ctgryCode --title "Category Code" --text "The code of this client category";
description add-field-description --onProperty ctgryCode --title "Code Categorie" --text "Le code de cette categorie client" --locale fr;
constraint NotNull --onProperty ctgryCode;

field custom --named whenInRole --type ~.jpa.BpPtnrRole;
description add-field-description --onProperty whenInRole --title "When Partner Role" --text "Role for which this category applies";
description add-field-description --onProperty whenInRole --title "Quand Partenaire en Role" --text "Role pour lequel cette category est appliquée" --locale fr;
enum enumerated-field --onProperty whenInRole;

cd ~~;

@/* Business Partner Category Discount*/;
entity --named BpCtgryDscnt --package ~.jpa;
description add-class-description --title "Discount by Category" --text "Discount by Category";
description add-class-description  --locale fr --title "Rabat par Category" --text "Rabat par Category";

field string --named ctgryCode;
description add-field-description --onProperty ctgryCode --title "Category Code" --text "The code of this client category";
description add-field-description --onProperty ctgryCode --title "Code Categorie" --text "Le code de cette categorie client" --locale fr;
constraint NotNull --onProperty ctgryCode;

field boolean --named dscntAuthrzd --primitive false;
description add-field-description --onProperty dscntAuthrzd --title "Discount Authorized" --text "Whether or not the customer can be given discount";
description add-field-description --onProperty dscntAuthrzd --title "Remise Autorisée" --text "Autorise ou non la remise globale sur les produits au client" --locale fr;

field number --named maxDscntRatePct --type java.math.BigDecimal;
description add-field-description --onProperty maxDscntRatePct --title "Max Discount Rate" --text "Max Discount Rate";
description add-field-description --onProperty maxDscntRatePct --title "Max Taux Rabais" --text "Max Taux Rabais" --locale fr;

cd ~~;

@/* Account Balance Side */;
java new-enum-type --named BpAccBalanceSide --package ~.jpa;
enum add-enum-class-description --title "Account Balance Side" --text "Account Balance Side";
enum add-enum-class-description  --locale fr --title "Coté du Solde" --text "Coté du Solde";
java new-enum-const D;
enum add-enum-constant-description --onConstant D --title "Debit" --text "Debit";
enum add-enum-constant-description --onConstant D --title "Debit" --text "Debit" --locale fr;
java new-enum-const C;
enum add-enum-constant-description --onConstant C --title "Credit" --text "Credit";
enum add-enum-constant-description --onConstant C --title "Credit" --text "Credit" --locale fr;

cd ~~;

@/* Business Partner Account Balance*/;
entity --named BpPtnrAccntBlnce --package ~.jpa;
description add-class-description --title "Partner Account Balance" --text "Partner Account Balance";
description add-class-description  --locale fr --title "Balance Compte Partenaire" --text "Balance Compte Partenaire";

field string --named ptnrNbr;
description add-field-description --onProperty ptnrNbr --title "Identifier" --text "Identifier of this Partner";
description add-field-description --onProperty ptnrNbr --title "Identifiantr" --text "Identifiant de ce Partenaire" --locale fr;
constraint NotNull --onProperty ptnrNbr;

field custom --named side --type ~.jpa.BpAccBalanceSide;
description add-field-description --onProperty side --title "Side" --text "Side";
description add-field-description --onProperty side --title "Coté" --text "Coté" --locale fr;
enum enumerated-field --onProperty side;

field number --named accBalance --type java.math.BigDecimal;
description add-field-description --onProperty accBalance --title "Account Balance" --text "Account Balance";
description add-field-description --onProperty accBalance --title "Solde du Compte" --text "Solde du Compte" --locale fr;

field temporal --type TIMESTAMP --named balanceDt; 
description add-field-description --onProperty balanceDt --title "Balance Date" --text "Balance Date";
description add-field-description --onProperty balanceDt --title "Date du Solde" --text "Date du Solde" --locale fr;
constraint NotNull --onProperty balanceDt;

cd ~~ ;

@/* Insurrance */;
entity --named Insurrance --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Insurrance" --text "The Insurrance .";
description add-class-description --locale fr --title "Assurance" --text "Assurance";

field temporal --type TIMESTAMP --named beginDate; 
description add-field-description --onProperty beginDate --title "Begin Date" --text "The date of beginning pf this inssurance";
description add-field-description --onProperty beginDate --title "date de debut" --text "La date de debut de cette assurance" --locale fr;
constraint NotNull --onProperty beginDate;
description add-notNull-message --onProperty beginDate --title "The begin date is required" --text "The begin date is required";
description add-notNull-message --onProperty beginDate --title "La date de debut est requis" --text "la date de debut est requis" --locale fr;

field temporal --type TIMESTAMP --named endDate; 
description add-field-description --onProperty endDate --title "End Date" --text "The date of ending pf this inssurance";
description add-field-description --onProperty endDate --title "date de fin" --text "La date de fin de cette assurance" --locale fr;

field string --named cstmrNbr;
description add-field-description --onProperty cstmrNbr --title "Customer" --text "The Customer.";
description add-field-description --onProperty cstmrNbr --title "Client" --text "Le client." --locale fr;
constraint NotNull --onProperty cstmrNbr;

field string --named insurerNbr;
description add-field-description --onProperty insurerNbr --title "Insurer" --text "The insurer";
description add-field-description --onProperty insurerNbr --title "Assureur" --text "Assureur" --locale fr;
constraint NotNull --onProperty insurerNbr;

field number --named coverRatePct --type java.math.BigDecimal;
description add-field-description --onProperty coverRatePct --title "Coverage Rate" --text "The coverage rate for this client.";
description add-field-description --onProperty coverRatePct --title "Taux Couverture" --text "Taux de couverture pour ce client." --locale fr;
constraint NotNull --onProperty coverRatePct;

field string --named memberNbr;
description add-field-description --onProperty memberNbr --title "Member Number" --text "Member Number";
description add-field-description --onProperty memberNbr --title "Numéro du Membre" --text "Numéro du Membre" --locale fr;

cd ~~;

repogen setup;

repogen new-repository --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys;

cd ~~;

mvn clean install -DskipTests;













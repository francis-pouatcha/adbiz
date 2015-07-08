set ACCEPT_DEFAULTS true;

new-project --named adsales.server --topLevelPackage org.adorsys.adsales --finalName adsales.server --projectFolder adsales.server;

as7 setup;

persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

set ACCEPT_DEFAULTS false;

enum setup;

repogen setup;

cd ~~;

@/* Role In Sales Order */;
entity --named DynEnum --package ~.jpa;
description add-class-description --title "Dynamic Enumeration" --text "Dynamic Enumeration";
description add-class-description --title "Énumeration Dynamique" --text "Énumeration Dynamique" --locale fr;

field string --named enumKey;
description add-field-description --onProperty enumKey --title "Key" --text "Key";
description add-field-description --onProperty enumKey --title "Clé" --text "Clé" --locale fr;
constraint NotNull --onProperty enumKey;

field string --named langIso2;
description add-field-description --onProperty langIso2 --title "Language" --text "Language";
description add-field-description --onProperty langIso2 --title "Language" --text "Language" --locale fr;
constraint NotNull --onProperty langIso2;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "Name";
description add-field-description --onProperty name --title "Nom" --text "Nom" --locale fr;
constraint NotNull --onProperty name;

field string --named description;
description add-field-description --onProperty description --title "Description" --text "Description";
description add-field-description --onProperty description --title "Description" --text "Description" --locale fr;
constraint NotNull --onProperty description;
constraint Size --onProperty description --max 256;

@/* Enum type Sales Status */;
entity --named SlsSalesStatus --package ~.jpa;
description add-class-description --title "Sales Status" --text "Status of the sales";
description add-class-description --title "Status Vente" --text "Status da la vente" --locale fr;

java new-enum-type --named SlsSalesStatusEnum --package ~.jpa;
enum add-enum-class-description --title "Sales Status" --text "Status of the sales";
enum add-enum-class-description --title "Status Vente" --text "Status da la vente" --locale fr;
java new-enum-const SUSPENDED ;
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspended" --text "Suspended";
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspendu" --text "Suspendu" --locale fr ;
java new-enum-const ONGOING ;
enum add-enum-constant-description --onConstant ONGOING --title "Ongoing" --text "Ongoing";
enum add-enum-constant-description --onConstant ONGOING --title "En Cours" --text "En cours" --locale fr ;
java new-enum-const RESUMED;
enum add-enum-constant-description --onConstant RESUMED --title "Resumed" --text "Resumed";
enum add-enum-constant-description --onConstant RESUMED --title "Repris" --text "Repris" --locale fr ;
java new-enum-const CLOSED;
enum add-enum-constant-description --onConstant CLOSED --title "Closed" --text "Closed";
enum add-enum-constant-description --onConstant CLOSED --title "Cloturé" --text "Cloturé" --locale fr ;

cd ~~;

@/* Processing Step */;
@/* Enum type Sales Status */;
entity --named ProcStep --package ~.jpa;
description add-class-description --title "Processing Step" --text "Sales Order Processing Step";
description add-class-description --title "Etape Execution" --text "Etape d Execution de la Commande Client" --locale fr;

java new-enum-type --named ProcStepEnum --package ~.jpa;
enum add-enum-class-description --title "Processing Step" --text "Sales Order Processing Step";
enum add-enum-class-description --title "Etape Execution" --text "Etape d Execution de la Commande Client"--locale fr;
java new-enum-const INITIATING;
enum add-enum-constant-description --onConstant INITIATING --title "Initiating" --text "Initiating";
enum add-enum-constant-description --onConstant INITIATING --title "Initialisant" --text "Initialisant" --locale fr;
java new-enum-const CLOSING;
enum add-enum-constant-description --onConstant CLOSING --title "Closing" --text "Closing";
enum add-enum-constant-description --onConstant CLOSING --title "En Cloture" --text "En Cloture" --locale fr;
java new-enum-const ANNULATING;
enum add-enum-constant-description --onConstant ANNULATING --title "Annulating" --text "Annulating";
enum add-enum-constant-description --onConstant ANNULATING --title "En Annulation" --text "En Annulation" --locale fr;
java new-enum-const APPROVING;
enum add-enum-constant-description --onConstant APPROVING --title "Approving" --text "Approving";
enum add-enum-constant-description --onConstant APPROVING --title "En Approbation" --text "En Approbation" --locale fr;
java new-enum-const RECALLING;
enum add-enum-constant-description --onConstant RECALLING --title "Recalling" --text "Recalling";
enum add-enum-constant-description --onConstant RECALLING --title "En Rappel" --text "En Rappel" --locale fr;
java new-enum-const MODIFYING;
enum add-enum-constant-description --onConstant MODIFYING --title "Modifying" --text "Modifying";
enum add-enum-constant-description --onConstant MODIFYING --title "En Modification" --text "En Modification" --locale fr;
java new-enum-const COMMITTING;
enum add-enum-constant-description --onConstant COMMITTING --title "Committing" --text "Committing";
enum add-enum-constant-description --onConstant COMMITTING --title "Engagement en Cours" --text "Engagement en Cours" --locale fr;
java new-enum-const POSTING;
enum add-enum-constant-description --onConstant POSTING --title "Posting" --text "Posting";
enum add-enum-constant-description --onConstant POSTING --title "En Comptabilité" --text "En Comptabilité" --locale fr;
java new-enum-const REVERSING;
enum add-enum-constant-description --onConstant REVERSING --title "Reversing" --text "Reversing";
enum add-enum-constant-description --onConstant REVERSING --title "En Inversion" --text "En Inversion" --locale fr;

cd ~~;

 @/* History Type */;
entity --named HistoryType --package ~.jpa;
description add-class-description --title "Sales Order History Type" --text "Sales Order History Type";
description add-class-description --title "Type D History de la Commande Client" --text "Type D History de la Commande Client" --locale fr;
 
java new-enum-type --named HistoryTypeEnum --package ~.jpa;
enum add-enum-class-description --title "Sales Order History Type" --text "Sales Order History Type";
enum add-enum-class-description --title "Type D History de la Commande Client" --text "Type D History de la Commande Client" --locale fr;
java new-enum-const INFO;
enum add-enum-constant-description --onConstant INFO --title "Initiating" --text "Initiating";
enum add-enum-constant-description --onConstant INFO --title "En Initiation" --text "En Initiation" --locale fr ;
java new-enum-const APPROVAL_REQUESTED;
enum add-enum-constant-description --onConstant APPROVAL_REQUESTED --title "Approval Requested" --text "Approval Requested";
enum add-enum-constant-description --onConstant APPROVAL_REQUESTED --title "Approbation Requise" --text "Approbation Requise" --locale fr ;
java new-enum-const APPROVED;
enum add-enum-constant-description --onConstant APPROVED --title "Approved" --text "Approved";
enum add-enum-constant-description --onConstant APPROVED --title "Approuvé" --text "Approuvé" --locale fr ;
java new-enum-const FAILED_APPROVAL;
enum add-enum-constant-description --onConstant FAILED_APPROVAL --title "Failed Approval" --text "Failed Approval";
enum add-enum-constant-description --onConstant FAILED_APPROVAL --title "Échec d Approbation" --text "Échec d Approbation" --locale fr ;
java new-enum-const DENIED_APPROVAL;
enum add-enum-constant-description --onConstant DENIED_APPROVAL --title "Denied Approval" --text "Denied Approval";
enum add-enum-constant-description --onConstant DENIED_APPROVAL --title "Approbation refusé" --text "Approbation refusé" --locale fr ;
java new-enum-const FROZEN_APPROVING;
enum add-enum-constant-description --onConstant FROZEN_APPROVING --title "Frozen Approving" --text "Frozen Approving";
enum add-enum-constant-description --onConstant FROZEN_APPROVING --title "Gelé en Approbation" --text "Gelé en Approbation" --locale fr;
java new-enum-const FROZEN_ABANDONED;
enum add-enum-constant-description --onConstant FROZEN_ABANDONED --title "Frozen Abandoned" --text "Frozen Abandoned";
enum add-enum-constant-description --onConstant FROZEN_ABANDONED --title "Abandonné Gelé" --text "Abandonné gelé" --locale fr;
java new-enum-const FROZEN_TERMINATED;
enum add-enum-constant-description --onConstant FROZEN_TERMINATED --title "Frozen Terminated" --text "Frozen Terminated";
enum add-enum-constant-description --onConstant FROZEN_TERMINATED --title "Terminé Gelé" --text "Terminé Gelé" --locale fr;
java new-enum-const MODIFIED;
enum add-enum-constant-description --onConstant MODIFIED --title "Modified" --text "Modified";
enum add-enum-constant-description --onConstant MODIFIED --title "Modifié" --text "Modifié" --locale fr;
java new-enum-const CANCELED;
enum add-enum-constant-description --onConstant CANCELED --title "Canceled" --text "Canceled";
enum add-enum-constant-description --onConstant CANCELED --title "Annulé" --text "Annulé" --locale fr;
java new-enum-const FAILED_CANCELATION;
enum add-enum-constant-description --onConstant FAILED_CANCELATION --title "Failed Cancellation" --text "Failed Cancellation";
enum add-enum-constant-description --onConstant FAILED_CANCELATION --title "Échec de l Annulation" --text "Échec de l Annulation" --locale fr;
java new-enum-const COMMITTED;
enum add-enum-constant-description --onConstant COMMITTED --title "Committed" --text "Committed";
enum add-enum-constant-description --onConstant COMMITTED --title "Engagé" --text "Engagé" --locale fr;
java new-enum-const FAILED_COMMITMENT;
enum add-enum-constant-description --onConstant FAILED_COMMITMENT --title "Failed Commitment" --text "Failed Commitment";
enum add-enum-constant-description --onConstant FAILED_COMMITMENT --title "Engagement D Échec" --text "Engagement D Échec" --locale fr;
java new-enum-const CLOSED;
enum add-enum-constant-description --onConstant CLOSED --title "Closed" --text "Closed";
enum add-enum-constant-description --onConstant CLOSED --title "Conclu" --text "Conclu" --locale fr;
java new-enum-const FAILED_CLOSING;
enum add-enum-constant-description --onConstant FAILED_CLOSING --title "Failed Closing" --text "Failed Closing";
enum add-enum-constant-description --onConstant FAILED_CLOSING --title "Échec de Conclusion" --text "Échec de Conclusion" --locale fr;
java new-enum-const POSTED;
enum add-enum-constant-description --onConstant POSTED --title "Posted" --text "Posted";
enum add-enum-constant-description --onConstant POSTED --title "Publié" --text "Publié" --locale fr;
java new-enum-const REVERSED;
enum add-enum-constant-description --onConstant REVERSED --title "Reversed" --text "Reversed";
enum add-enum-constant-description --onConstant REVERSED --title "Inversé" --text "Inversé" --locale fr;
java new-enum-const FAILED_REVERSE;
enum add-enum-constant-description --onConstant FAILED_REVERSE --title "Failed Reverse" --text "Failed Reverse";
enum add-enum-constant-description --onConstant FAILED_REVERSE --title "Échec Inversion" --text "Échec d Inversion" --locale fr;
java new-enum-const RECALL;
enum add-enum-constant-description --onConstant RECALL --title "Recall" --text "Recall";
enum add-enum-constant-description --onConstant RECALL --title "Rappel" --text "Rappel" --locale fr;
java new-enum-const FAILED_RECALL;
enum add-enum-constant-description --onConstant FAILED_RECALL --title "Failed Recall" --text "Failed Recall";
enum add-enum-constant-description --onConstant FAILED_RECALL --title "Échec du Rappel" --text "Échec du Rappel" --locale fr;

cd ~~;

@/* Sales Order History*/;
entity --named SlsSOHstry --package ~.jpa;
description add-class-description --title "Sales Order History" --text "A sales order history";
description add-class-description  --locale fr --title "Historique Commande Client" --text "Historique commande client.";

field string --named soNbr;
description add-field-description --onProperty soNbr --title "Sales Order Number" --text "The sales order number.";
description add-field-description --onProperty soNbr --title "Numéro de Commande Client" --text "Le numéro de la commande client." --locale fr;
constraint NotNull --onProperty soNbr;

field string --named soStatus;
description add-field-description --onProperty soStatus --title "Sales Order Status" --text "Status of the sales order";
description add-field-description --onProperty soStatus --title "Status Commande Client" --text "Status da la commande client" --locale fr;
constraint NotNull --onProperty soStatus;

field temporal --type TIMESTAMP --named hstryDt; 
description add-field-description --onProperty hstryDt --title "History Date" --text "The date of this history entry.";
description add-field-description --onProperty hstryDt --title "Date Historique" --text "La date de cette historique" --locale fr;
constraint NotNull --onProperty hstryDt;

field string --named orignLogin;
description add-field-description --onProperty orignLogin --title "Originating organization unit" --text "Originating organization unit";
description add-field-description --onProperty orignLogin --title "Unité organisationelle originante" --text "Unité organisationelle originante" --locale fr;
constraint NotNull --onProperty orignLogin;

field string --named orignWrkspc;
description add-field-description --onProperty orignWrkspc --title "Originating Workspace" --text "Originating workspace";
description add-field-description --onProperty orignWrkspc --title "Espace Travaille Originaire" --text "Espace Travaille Originaire" --locale fr;
constraint NotNull --onProperty orignWrkspc;

field string --named procStep;
description add-field-description --onProperty procStep --title "Processing Step" --text "Processing step of the sales order";
description add-field-description --onProperty procStep --title "Etape de Traitement" --text "Etape de traitement la commande client" --locale fr;
constraint NotNull --onProperty procStep;

field string --named histryType;
description add-field-description --onProperty histryType --title "Sales Order History Type" --text "Sales Order History Type";
description add-field-description --onProperty histryType --title "Type D History de la Commande Client" --text "Type D History de la Commande Client" --locale fr;
constraint NotNull --onProperty histryType;

field string --named comment;
description add-field-description --onProperty comment --title "Comment" --text "Comment";
description add-field-description --onProperty comment --title "Commentaire" --text "Commentaire" --locale fr;

field string --named addtnlInfo;
description add-field-description --onProperty addtnlInfo --title "Additional Information" --text "Additional Information";
description add-field-description --onProperty addtnlInfo --title "Informations Complémentaires" --text "Informations Complémentaires" --locale fr;

cd ~~;

@/* Sales Order Item */;
entity --named SlsSOItem --package ~.jpa;
description add-class-description --title "Sales Order Item" --text "The sales order item.";
description add-class-description  --locale fr --title "Ligne Commande Client" --text "La ligne de command client.";

field string --named soNbr;
description add-field-description --onProperty soNbr --title "Sales Order  Number" --text "The sales order number";
description add-field-description --onProperty soNbr --title "Numéro de la commande" --text "Numéro de la commande" --locale fr;
constraint NotNull --onProperty soNbr;

field string --named lotPic;
description add-field-description --onProperty lotPic --title "Lot Identifier" --text "The identifier of this lot. Might be printet as a bar code and put on each article of this lot.";
description add-field-description --onProperty lotPic --title "Identifiant du Lot" --text "Le code identifiant ce lot. Pourait etre imprimé comme code bar et posé su chaque produit de ce lot." --locale fr;
constraint NotNull --onProperty lotPic;

field string --named artPic;
description add-field-description --onProperty artPic --title "Article PIC" --text "The article code";
description add-field-description --onProperty artPic --title "CIP Article" --text "Code article" --locale fr;
constraint NotNull --onProperty artPic;

field number --named orderedQty --type java.math.BigDecimal;
description add-field-description --onProperty orderedQty --title "Quantity Ordered" --text "The quantity ordered in this line.";
description add-field-description --onProperty orderedQty --title "Quantité Commandés" --text "La quantité commandée dans cette ligne." --locale fr;
constraint NotNull --onProperty orderedQty;

field number --named returnedQty --type java.math.BigDecimal;
description add-field-description --onProperty returnedQty --title "Quantity Returned" --text "The quantity returned in this line.";
description add-field-description --onProperty returnedQty --title "Quantité Retournée" --text "La quantité retournée dans cette ligne." --locale fr;

field number --named deliveredQty --type java.math.BigDecimal;
description add-field-description --onProperty deliveredQty --title "Quantity Returned" --text "The quantity returned in this line.";
description add-field-description --onProperty deliveredQty --title "Quantité Retournée" --text "La quantité retournée dans cette ligne." --locale fr;

field number --named sppuPreTax --type java.math.BigDecimal;
description add-field-description --onProperty sppuPreTax --title "Sales Price per Unit Pre Tax" --text "The sales price per unit pre tax";
description add-field-description --onProperty sppuPreTax --title "Prix de Vente Unitaire Hors Tax" --text "Prix de vente unitaire hors taxe" --locale fr;

field string --named sppuCur;
description add-field-description --onProperty sppuCur --title "Currency of the Sales Price per Unit" --text "The currency of the sales price per unit.";
description add-field-description --onProperty sppuCur --title "Dévise du Prix de Vente Unitaire" --text "La dévise du prix de vente unitaire." --locale fr;

field number --named grossSPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty grossSPPreTax --title "Gross Sales Amount Pre Tax" --text "Gross Sales Amount Pre Tax";
description add-field-description --onProperty grossSPPreTax --title "Montant Brut Vente Hors Taxes" --text "Montant Brut Vente Hors Taxes" --locale fr;

field number --named rebate --type java.math.BigDecimal;
description add-field-description --onProperty rebate --title "Rebate Amount" --text "Rebate or Discount amount.";
description add-field-description --onProperty rebate --title "Montant Remise" --text "Montant de la remise" --locale fr;

field number --named netSPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty netSPPreTax --title "Net Sales Amount Pre Tax" --text "Net Sales Amount Pre Tax";
description add-field-description --onProperty netSPPreTax --title "Montant Net Vente Hors Taxes" --text "Montant Net Vente Hors Taxes" --locale fr;

field number --named vatPct --type java.math.BigDecimal;
description add-field-description --onProperty vatPct --title "VAT Rate" --text "The VAT rate in percent.";
description add-field-description --onProperty vatPct --title "Taux TVA" --text "The taux de TVA en pourcent." --locale fr;

field number --named vatAmount --type java.math.BigDecimal;
description add-field-description --onProperty vatAmount --title "VAT Amount" --text "Tax amount for this sales order.";
description add-field-description --onProperty vatAmount --title "Montant TVA" --text "Montant de la taxe de la vente." --locale fr;

field number --named netSPTaxIncl --type java.math.BigDecimal;
description add-field-description --onProperty netSPTaxIncl --title "Sales Amount Tax Included" --text "Sales Amount Tax Included";
description add-field-description --onProperty netSPTaxIncl --title "Montant Vente TTC" --text "Le montant de la vente toute taxe comprise." --locale fr;

field string --named objctOrgUnit;
description add-field-description --onProperty objctOrgUnit --title "Ordered Object Organization Unit" --text "Ordered Object Organization Unit";
description add-field-description --onProperty objctOrgUnit --title "Unité Organisationelle pour Objet en Vente" --text "Unité Organisationelle pour Objet en Vente" --locale fr;
constraint NotNull --onProperty objctOrgUnit;

cd ~~;

@/* Role In Sales Order */;
entity --named SlsRoleInSales --package ~.jpa;
description add-class-description --title "Role in Sales" --text "Role of this business partner in the sales";
description add-class-description --title "Role dans cette Vente" --text "Role dans cette vente" --locale fr;

java new-enum-type --named SlsRoleInSalesEnum --package ~.jpa;
enum add-enum-class-description --title "Role in Sales" --text "Role of this business partner in the sales";
enum add-enum-class-description --title "Role dans cette Vente" --text "Role dans cette vente" --locale fr;
java new-enum-const PATIENT;
enum add-enum-constant-description --onConstant PATIENT --title "Patient" --text "Patient";
enum add-enum-constant-description --onConstant PATIENT --title "Patient" --text "Patient" --locale fr ;
java new-enum-const INSURED;
enum add-enum-constant-description --onConstant INSURED --title "Insured" --text "Insured";
enum add-enum-constant-description --onConstant INSURED --title "Assuré" --text "Assuré" --locale fr ;
java new-enum-const INSURER;
enum add-enum-constant-description --onConstant INSURER --title "Insurer" --text "Insurer";
enum add-enum-constant-description --onConstant INSURER --title "Assureur" --text "Assureur" --locale fr ;
java new-enum-const HOSPITAL;
enum add-enum-constant-description --onConstant HOSPITAL --title "Hospital" --text "Hospital";
enum add-enum-constant-description --onConstant HOSPITAL --title "Hôpital" --text "Hôpital" --locale fr ;
java new-enum-const CUSTOMER;
enum add-enum-constant-description --onConstant CUSTOMER --title "Customer" --text "Customer";
enum add-enum-constant-description --onConstant CUSTOMER --title "Client" --text "Client" --locale fr ;

cd ~~;

@/* Sales Order */;
entity --named SlsSalesOrder --package ~.jpa;
description add-class-description --title "Sales Order" --text "A sales order.";
description add-class-description  --locale fr --title "Commande Client" --text "Une commande client.";

field string --named soNbr;
description add-field-description --onProperty soNbr --title "Sales Order Number" --text "The sales order number.";
description add-field-description --onProperty soNbr --title "Numéro de Commande Client" --text "Le numéro de la commande client." --locale fr;
constraint NotNull --onProperty soNbr;

field custom --named soStatus --type ~.jpa.SlsSalesStatus;
description add-field-description --onProperty soStatus --title "Status" --text "The status of this sales order.";
description add-field-description --onProperty soStatus --title "Status" --text "État de cette commande client." --locale fr;
enum enumerated-field --onProperty soStatus ;
display add-list-field --field soStatus;

field string --named soCur;
description add-field-description --onProperty soCur --title "Currency" --text "Currency of this sales order";
description add-field-description --onProperty soCur --title "Dévise" --text "Dévise de cette commande client" --locale fr;

field number --named grossSPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty grossSPPreTax --title "Gross Sales Amount Pre Tax" --text "Gross Sales Amount Pre Tax";
description add-field-description --onProperty grossSPPreTax --title "Montant Brut Vente Hors Taxes" --text "Montant Vente Achat Hors Taxes" --locale fr;

field number --named rebate --type java.math.BigDecimal;
description add-field-description --onProperty rebate --title "Rebate Amount" --text "Rebate or Discount amount.";
description add-field-description --onProperty rebate --title "Montant Remise" --text "Montant de la remise" --locale fr;

field number --named netSPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty netSPPreTax --title "Net Sales Amount Pre Tax" --text "Net Sales Amount Pre Tax";
description add-field-description --onProperty netSPPreTax --title "Montant Net Vent Hors Taxes" --text "Montant Net Vente Hors Taxes" --locale fr;

field number --named vatAmount --type java.math.BigDecimal;
description add-field-description --onProperty vatAmount --title "VAT Amount" --text "Tax amount for this sales order.";
description add-field-description --onProperty vatAmount --title "Montant TVA" --text "Montant de la taxe de cette commande." --locale fr;

field number --named netSPTaxIncl --type java.math.BigDecimal;
description add-field-description --onProperty netSPTaxIncl --title "Sales Amount Tax Included" --text "Sales Amount Tax Included";
description add-field-description --onProperty netSPTaxIncl --title "Montant Vente TTC" --text "Le montant de vente toute taxe comprise." --locale fr;

field number --named pymtDscntPct --type java.math.BigDecimal;
description add-field-description --onProperty pymtDscntPct --title "Payment Discount Percent" --text "Payment Discount en Pourcent.";
description add-field-description --onProperty pymtDscntPct --title "Escompte en Pourcent" --text "Escompte en Pourcent." --locale fr;

field number --named pymtDscntAmt --type java.math.BigDecimal;
description add-field-description --onProperty pymtDscntAmt --title "Payment Discount Amount" --text "Discount amount for promt payment.";
description add-field-description --onProperty pymtDscntAmt --title "Montant Excompte" --text "Montant de la remise pour payement anticipe" --locale fr;

field number --named netSalesAmt --type java.math.BigDecimal;
description add-field-description --onProperty netSalesAmt --title "Net Sales Amount" --text "Net Sales Amount";
description add-field-description --onProperty netSalesAmt --title "Montant Net Vente" --text "Montant Net Vente" --locale fr;

field number --named rdngDscntAmt --type java.math.BigDecimal;
description add-field-description --onProperty rdngDscntAmt --title "Rounding Discount Amount" --text "Rounding Discount Amount";
description add-field-description --onProperty rdngDscntAmt --title "Montant Remise pour Arrondi" --text "Montant Remise pour Arrondi" --locale fr;

field number --named netAmtToPay --type java.math.BigDecimal;
description add-field-description --onProperty netAmtToPay --title "Net Amount to Pay" --text "Net amount to pay.";
description add-field-description --onProperty netAmtToPay --title "Montant net a payer" --text "Net a payer." --locale fr;

cd ~~;

@/* Sales Order Parties*/;
entity --named SlsSOPtnr --package ~.jpa;
description add-class-description --title "Sales Order Involved Party" --text "A sales order involved party.";
description add-class-description --title "Persone Impliqué dans une Commande Client" --text "Persone Impliqué dans une Commande Client" --locale fr;

field string --named soNbr;
description add-field-description --onProperty soNbr --title "Sales Order Number" --text "The sales order number.";
description add-field-description --onProperty soNbr --title "Numéro de Commande Client" --text "Le numéro de la commande client." --locale fr;
constraint NotNull --onProperty soNbr;

field string --named ptnrNbr;
description add-field-description --onProperty ptnrNbr --title "Partner Identifier" --text "Party Identifier";
description add-field-description --onProperty ptnrNbr --title "Identifiant du partenaire" --text "Identifiant du partenaire" --locale fr;
constraint NotNull --onProperty ptnrNbr;

field string --named roleInSO;
description add-field-description --onProperty roleInSO --title "Role in Sales Order" --text "Role of this business partner in the sales order.";
description add-field-description --onProperty roleInSO --title "Role dans cette Commande" --text "Role dans cette Commande" --locale fr;
constraint NotNull --onProperty roleInSO;

cd ~~;

@/* Invoice */;
@/* InvoiceType */;
entity --named SlsInvoiceType --package ~.jpa;
description add-class-description --title "Invoice Type" --text "The type of this invoice.";
description add-class-description --title "Type de Facture" --text "Le type d une facture." --locale fr;

java new-enum-type --named SlsInvoiceTypeEnum --package ~.jpa ;
enum add-enum-class-description --title "Invoice Type" --text "The type of this invoice.";
enum add-enum-class-description --locale fr --title "Type de Facture" --text "Le type d une facture.";
java new-enum-const SALES;
enum add-enum-constant-description --onConstant SALES --title "Sales invoice" --text "Sales invoice";
enum add-enum-constant-description --onConstant SALES --title "Facture de vente" --text "Facture de vente" --locale fr;
java new-enum-const PROFORMA;
enum add-enum-constant-description --onConstant PROFORMA --title "Proforma Invoice" --text "Proforma Invoice";
enum add-enum-constant-description --onConstant PROFORMA --title "Facture Proforma" --text "Facture proforma" --locale fr;
java new-enum-const RETURN;
enum add-enum-constant-description --onConstant RETURN --title "Return Invoice" --text "Return Invoice";
enum add-enum-constant-description --onConstant RETURN --title "Facture Retour" --text "Facture Retour" --locale fr;

cd ~~;

@/* Sales Order History*/;
entity --named SlsInvceHistory --package ~.jpa;
description add-class-description --title "Invoice History" --text "A invoice history";
description add-class-description --title "Historique Facture" --text "Historique facture." --locale fr;

field string --named invceNbr;
description add-field-description --onProperty invceNbr --title "Invoice Number" --text "The sales order number.";
description add-field-description --onProperty invceNbr --title "Numéro de Facture" --text "Numéro de Facture." --locale fr;
constraint NotNull --onProperty invceNbr;

field string --named invceStatus;
description add-field-description --onProperty invceStatus --title "Invoice Status" --text "Status of the invoice";
description add-field-description --onProperty invceStatus --title "Status Facture" --text "Status da la facture" --locale fr;
constraint NotNull --onProperty invceStatus;

field temporal --type TIMESTAMP --named hstryDt; 
description add-field-description --onProperty hstryDt --title "History Date" --text "The date of this history entry.";
description add-field-description --onProperty hstryDt --title "Date Historique" --text "La date de cette historique" --locale fr;
constraint NotNull --onProperty hstryDt;

field string --named orignLogin;
description add-field-description --onProperty orignLogin --title "Originating organization unit" --text "Originating organization unit";
description add-field-description --onProperty orignLogin --title "Unité organisationelle originante" --text "Unité organisationelle originante" --locale fr;
constraint NotNull --onProperty orignLogin;

field string --named orignWrkspc;
description add-field-description --onProperty orignWrkspc --title "Originating Workspace" --text "Originating workspace";
description add-field-description --onProperty orignWrkspc --title "Espace Travaille Originaire" --text "Espace Travaille Originaire" --locale fr;
constraint NotNull --onProperty orignWrkspc;

field string --named procStep;
description add-field-description --onProperty procStep --title "Processing Step" --text "Processing step of the invoice";
description add-field-description --onProperty procStep --title "Etape de Traitement" --text "Etape de traitement la facture" --locale fr;
constraint NotNull --onProperty procStep;

field string --named hstryType;
description add-field-description --onProperty hstryType --title "History Type" --text "History Type";
description add-field-description --onProperty hstryType --title "Type History" --text "Type History" --locale fr;
constraint NotNull --onProperty hstryType;

field string --named comment;
description add-field-description --onProperty comment --title "Comment" --text "Comment";
description add-field-description --onProperty comment --title "Commentaire" --text "Commentaire" --locale fr;

field string --named addtnlInfo;
description add-field-description --onProperty addtnlInfo --title "Additional Information" --text "Additional Information";
description add-field-description --onProperty addtnlInfo --title "Informations Complémentaires" --text "Informations Complémentaires" --locale fr;

cd ~~;

@/* Enum type Sales Status */;
entity --named SlsInvceStatus --package ~.jpa;
description add-class-description --title "Invoice Status" --text "Status of the invoice";
description add-class-description --title "Status Facture" --text "Status da la facture" --locale fr;

java new-enum-type --named SlsInvceStatusEnum --package ~.jpa;
enum add-enum-class-description --title "Invoice Status" --text "Status of the invoice";
enum add-enum-class-description --title "Status Facture" --text "Status da la facture" --locale fr;
java new-enum-const SUSPENDED ;
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspended" --text "Suspended";
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspendu" --text "Suspendu" --locale fr ;
java new-enum-const OPEN;
enum add-enum-constant-description --onConstant OPEN --title "Open" --text "Open";
enum add-enum-constant-description --onConstant OPEN --title "Ouvert" --text "Ouvert" --locale fr ;
java new-enum-const RESUMED;
enum add-enum-constant-description --onConstant RESUMED --title "Resumed" --text "Resumed";
enum add-enum-constant-description --onConstant RESUMED --title "Repris" --text "Repris" --locale fr ;
java new-enum-const CLOSED;
enum add-enum-constant-description --onConstant CLOSED --title "Closed" --text "Closed";
enum add-enum-constant-description --onConstant CLOSED --title "Cloturé" --text "Cloturé" --locale fr ;
java new-enum-const SETTLED;
enum add-enum-constant-description --onConstant SETTLED --title "Settled" --text "Settled";
enum add-enum-constant-description --onConstant SETTLED --title "Établi" --text "Établi" --locale fr ;

cd ~~;

@/* Entite Ligne Facture */;
entity --named SlsInvceItem --package ~.jpa;
description add-class-description --title "Invoice Item" --text "An invoice item.";
description add-class-description  --locale fr --title "Ligne Facture" --text "Une ligne facture.";

field string --named invNbr;
description add-field-description --onProperty invNbr --title "Invoice  Number" --text "Invoice  Number";
description add-field-description --onProperty invNbr --title "Numéro Facture" --text "Numéro Facture" --locale fr;
constraint NotNull --onProperty invNbr;

field string --named lotPic;
description add-field-description --onProperty lotPic --title "Lot Identifier" --text "The identifier of this lot. Might be printet as a bar code and put on each article of this lot.";
description add-field-description --onProperty lotPic --title "Identifiant du Lot" --text "Le code identifiant ce lot. Pourait etre imprimé comme code bar et posé su chaque produit de ce lot." --locale fr;
constraint NotNull --onProperty lotPic;

field string --named artPic;
description add-field-description --onProperty artPic --title "Article PIC" --text "The article code";
description add-field-description --onProperty artPic --title "CIP Article" --text "Code article" --locale fr;
constraint NotNull --onProperty artPic;

field number --named qty --type java.math.BigDecimal;
description add-field-description --onProperty qty --title "Quantity" --text "The quantity purchased in this line.";
description add-field-description --onProperty qty --title "Quantité" --text "La quantité achetée dans cette ligne." --locale fr;

field number --named sppuPreTax --type java.math.BigDecimal;
description add-field-description --onProperty sppuPreTax --title "Sales Price per Unit Pre Tax" --text "The sales price per unit pre tax";
description add-field-description --onProperty sppuPreTax --title "Prix de Vente Unitaire Hors Tax" --text "Prix de vente unitaire hors taxe" --locale fr;

field string --named sppuCur;
description add-field-description --onProperty sppuCur --title "Currency of the Sales Price per Unit" --text "The currency of the sales price per unit.";
description add-field-description --onProperty sppuCur --title "Dévise du Prix de Vente Unitaire" --text "La dévise du prix de vente unitaire." --locale fr;

field number --named grossSPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty grossSPPreTax --title "Gross Sales Amount Pre Tax" --text "Gross Sales Amount Pre Tax";
description add-field-description --onProperty grossSPPreTax --title "Montant Brut Vente Hors Taxes" --text "Montant Brut Vente Hors Taxes" --locale fr;

field number --named rebate --type java.math.BigDecimal;
description add-field-description --onProperty rebate --title "Rebate Amount" --text "Rebate or Discount amount.";
description add-field-description --onProperty rebate --title "Montant Remise" --text "Montant de la remise" --locale fr;

field number --named netSPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty netSPPreTax --title "Net Sales Amount Pre Tax" --text "Net Sales Amount Pre Tax";
description add-field-description --onProperty netSPPreTax --title "Montant Net Vente Hors Taxes" --text "Montant Net Vente Hors Taxes" --locale fr;

field number --named vatPct --type java.math.BigDecimal;
description add-field-description --onProperty vatPct --title "VAT Rate" --text "The VAT rate in percent.";
description add-field-description --onProperty vatPct --title "Taux TVA" --text "The taux de TVA en pourcent." --locale fr;

field number --named vatAmount --type java.math.BigDecimal;
description add-field-description --onProperty vatAmount --title "VAT Amount" --text "Tax amount for this sales order.";
description add-field-description --onProperty vatAmount --title "Montant TVA" --text "Montant de la taxe de la vente." --locale fr;

field number --named netSPTaxIncl --type java.math.BigDecimal;
description add-field-description --onProperty netSPTaxIncl --title "Sales Amount Tax Included" --text "Sales Amount Tax Included";
description add-field-description --onProperty netSPTaxIncl --title "Montant Vente TTC" --text "Le montant de la vente toute taxe comprise." --locale fr;

field string --named objctOrgUnit;
description add-field-description --onProperty objctOrgUnit --title "Object Organization Unit" --text "Object Organization Unit of invoiced object";
description add-field-description --onProperty objctOrgUnit --title "Unité Organisationelle" --text "Unité Organisationelle de l objet facturé" --locale fr;
constraint NotNull --onProperty objctOrgUnit;

cd ~~;
@/* Entité Facture */;
entity --named SlsInvoice --package ~.jpa;
description add-class-description --title "Customer Invoice" --text "An invoice.";
description add-class-description  --locale fr --title "Facture Client" --text "Une facture.";

field string --named invceType;
description add-field-description --onProperty invceType --title "Invoice Type" --text "The type of this invoice.";
description add-field-description --onProperty invceType --title "Type de Facture" --text "Le type de cette facture." --locale fr;
constraint NotNull --onProperty invceType;

field string --named invceNbr;
description add-field-description --onProperty invceNbr --title "Invoice  Number" --text "Invoice  Number";
description add-field-description --onProperty invceNbr --title "Numéro Facture" --text "Numéro Facture" --locale fr;
constraint NotNull --onProperty invceNbr;

field string --named soNbr;
description add-field-description --onProperty soNbr --title "Sales Order Number" --text "The sales order number.";
description add-field-description --onProperty soNbr --title "Numéro de Commande Client" --text "Le numéro de la commande client." --locale fr;
constraint NotNull --onProperty soNbr;

field string --named invceStatus;
description add-field-description --onProperty invceStatus --title "Status" --text "The status of this invoice.";
description add-field-description --onProperty invceStatus --title "Status" --text "État de cette facture." --locale fr;
constraint NotNull --onProperty invceStatus;

field string --named invceCur;
description add-field-description --onProperty invceCur --title "Currency" --text "Currency of this invoice";
description add-field-description --onProperty invceCur --title "Dévise" --text "Dévise de cette facture" --locale fr;

field number --named grossSPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty grossSPPreTax --title "Gross Sales Amount Pre Tax" --text "Gross Sales Amount Pre Tax";
description add-field-description --onProperty grossSPPreTax --title "Montant Brut Vente Hors Taxes" --text "Montant Vente Achat Hors Taxes" --locale fr;

field number --named rebate --type java.math.BigDecimal;
description add-field-description --onProperty rebate --title "Rebate Amount" --text "Rebate or Discount amount.";
description add-field-description --onProperty rebate --title "Montant Remise" --text "Montant de la remise" --locale fr;

field number --named netSPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty netSPPreTax --title "Net Sales Amount Pre Tax" --text "Net Sales Amount Pre Tax";
description add-field-description --onProperty netSPPreTax --title "Montant Net Vent Hors Taxes" --text "Montant Net Vente Hors Taxes" --locale fr;

field number --named vatAmount --type java.math.BigDecimal;
description add-field-description --onProperty vatAmount --title "VAT Amount" --text "Tax amount for this invoice.";
description add-field-description --onProperty vatAmount --title "Montant TVA" --text "Montant de la taxe de cette facture." --locale fr;

field number --named netSPTaxIncl --type java.math.BigDecimal;
description add-field-description --onProperty netSPTaxIncl --title "Sales Amount Tax Included" --text "Sales Amount Tax Included";
description add-field-description --onProperty netSPTaxIncl --title "Montant Vente TTC" --text "Le montant de vente toute taxe comprise." --locale fr;

field number --named pymtDscntPct --type java.math.BigDecimal;
description add-field-description --onProperty pymtDscntPct --title "Payment Discount Percent" --text "Payment Discount en Pourcent.";
description add-field-description --onProperty pymtDscntPct --title "Escompte en Pourcent" --text "Escompte en Pourcent." --locale fr;

field number --named pymtDscntAmt --type java.math.BigDecimal;
description add-field-description --onProperty pymtDscntAmt --title "Payment Discount Amount" --text "Discount amount for promt payment.";
description add-field-description --onProperty pymtDscntAmt --title "Montant Excompte" --text "Montant de la remise pour payement anticipe" --locale fr;

field number --named netSalesAmt --type java.math.BigDecimal;
description add-field-description --onProperty netSalesAmt --title "Net Sales Amount" --text "Net Sales Amount";
description add-field-description --onProperty netSalesAmt --title "Montant Net Vente" --text "Montant Net Vente" --locale fr;

field number --named rdngDscntAmt --type java.math.BigDecimal;
description add-field-description --onProperty rdngDscntAmt --title "Rounding Discount Amount" --text "Rounding Discount Amount";
description add-field-description --onProperty rdngDscntAmt --title "Montant Remise pour Arrondi" --text "Montant Remise pour Arrondi" --locale fr;

field number --named netAmtToPay --type java.math.BigDecimal;
description add-field-description --onProperty netAmtToPay --title "Net Amount to Pay" --text "Net amount to pay.";
description add-field-description --onProperty netAmtToPay --title "Montant net a payer" --text "Net a payer." --locale fr;

cd ~~;

@/* Invoice Parties*/;
entity --named SlsInvcePtnr --package ~.jpa;
description add-class-description --title "Invoice Involved Party" --text "A invoice involved partner.";
description add-class-description  --locale fr --title "Persone Impliqué dans une Facture" --text "Persone Impliqué dans une Facture";

field string --named invceNbr;
description add-field-description --onProperty invceNbr --title "Invoice Number" --text "The invoice number.";
description add-field-description --onProperty invceNbr --title "Numéro de Facture" --text "Le numéro de la facture." --locale fr;
constraint NotNull --onProperty invceNbr;

field string --named ptnrNbr;
description add-field-description --onProperty ptnrNbr --title "Partner Identifier" --text "Party Identifier";
description add-field-description --onProperty ptnrNbr --title "Identifiant du partenaire" --text "Identifiant du partenaire" --locale fr;
constraint NotNull --onProperty ptnrNbr;

field string --named roleInInvce;
description add-field-description --onProperty roleInInvce --title "Role in Invoice" --text "Role of this business partner in the Invoice.";
description add-field-description --onProperty roleInInvce --title "Role dans cette Facture" --text "Role dans cette facture" --locale fr;
constraint NotNull --onProperty roleInInvce;

cd ~~;

@/* Enum type Sales Status */;
java new-enum-type --named SlsAmtSide --package ~.jpa;
enum add-enum-class-description --title "Amount Side" --text "Amount Side";
enum add-enum-class-description --title "Sens du Montant" --text "Sens du Montant" --locale fr;
java new-enum-const D;
enum add-enum-constant-description --onConstant D --title "Debit" --text "Debit";
enum add-enum-constant-description --onConstant D --title "Débit" --text "Débit" --locale fr ;
java new-enum-const C;
enum add-enum-constant-description --onConstant C --title "Credit" --text "Credit";
enum add-enum-constant-description --onConstant C --title "Crédit" --text "Crédit" --locale fr ;

cd ~~;

@/* Enum type Settlement Operation */;
@/* Enum type Sales Status */;
entity --named SlsSttlmtOp --package ~.jpa;
description add-class-description --title "Settlement Operation" --text "Settlement Operation";
description add-class-description --title "Opération de règlement" --text "Opération de règlement" --locale fr;

java new-enum-type --named SlsSttlmtOpEnum --package ~.jpa;
enum add-enum-class-description --title "Settlement Operation" --text "Settlement Operation";
enum add-enum-class-description --title "Opération de règlement" --text "Opération de règlement" --locale fr;
java new-enum-const DOWN_PAYMENT;
enum add-enum-constant-description --onConstant DOWN_PAYMENT --title "Down Payment" --text "Down Payment";
enum add-enum-constant-description --onConstant DOWN_PAYMENT --title "Acompte" --text "Acompte" --locale fr ;
java new-enum-const INVOICING;
enum add-enum-constant-description --onConstant INVOICING --title "Invoicing" --text "Invoicing";
enum add-enum-constant-description --onConstant INVOICING --title "Facturation" --text "Facturation" --locale fr ;
java new-enum-const CASH_PAYMENT;
enum add-enum-constant-description --onConstant CASH_PAYMENT --title "Cash Payment" --text "Cash Payment";
enum add-enum-constant-description --onConstant CASH_PAYMENT --title "Paiement en Espèces" --text "Paiement en espèces" --locale fr ;
java new-enum-const GOODWILL_SETTLMT;
enum add-enum-constant-description --onConstant GOODWILL_SETTLMT --title "Goodwill Settlement" --text "Goodwill Settlement";
enum add-enum-constant-description --onConstant GOODWILL_SETTLMT --title "Règlement par Complaisances" --text "Règlement par Complaisances" --locale fr ;
java new-enum-const VOUCHER_PAYMENT;
enum add-enum-constant-description --onConstant VOUCHER_PAYMENT --title "Voucher Payment" --text "Voucher Payment";
enum add-enum-constant-description --onConstant VOUCHER_PAYMENT --title "Règlement par bon" --text "Règlement par bon" --locale fr ;

cd ~~;

@/* Payments Sttlmt*/;
entity --named SlsAcct --package ~.jpa;
description add-class-description --title "Invoice Account" --text "Invoice Account";
description add-class-description  --locale fr --title "Compte Facture" --text "Compte Facture";

@/* Shall not be blank */;
field string --named soAndInvceNbr;
description add-field-description --onProperty soAndInvceNbr --title "Invoice and Sales Order Number" --text "The invoice and sales order number.";
description add-field-description --onProperty soAndInvceNbr --title "Numéro de Facture et de commande" --text "Le numéro de la facture et de commande." --locale fr;
constraint NotNull --onProperty soAndInvceNbr;

field string --named invceNbr;
description add-field-description --onProperty invceNbr --title "Invoice Number" --text "The invoice number.";
description add-field-description --onProperty invceNbr --title "Numéro de Facture" --text "Le numéro de la facture." --locale fr;

field string --named soNbr;
description add-field-description --onProperty soNbr --title "Invoice Number" --text "The invoice number.";
description add-field-description --onProperty soNbr --title "Numéro de Facture" --text "Le numéro de la facture." --locale fr;

field string --named ptnrNbr;
description add-field-description --onProperty ptnrNbr --title "Partner Identifier" --text "Party Identifier";
description add-field-description --onProperty ptnrNbr --title "Identifiant du partenaire" --text "Identifiant du partenaire" --locale fr;
constraint NotNull --onProperty ptnrNbr;

field string --named roleInInvce;
description add-field-description --onProperty roleInInvce --title "Role in Invoice" --text "Role of this business partner in the Invoice.";
description add-field-description --onProperty roleInInvce --title "Role dans cette Facture" --text "Role dans cette facture" --locale fr;
constraint NotNull --onProperty roleInInvce;

field string --named sttlmtOp;
description add-field-description --onProperty sttlmtOp --title "Settlement Operation" --text "Settlement Operation";
description add-field-description --onProperty sttlmtOp --title "Opération de règlement" --text "Opération de règlement" --locale fr;
constraint NotNull --onProperty sttlmtOp;

field string --named sttlmtOpHint;
description add-field-description --onProperty sttlmtOpHint --title "Settlement Operation Hint" --text "Settlement Operation Hint";
description add-field-description --onProperty sttlmtOpHint --title "Details sur Opération de règlement" --text "Detail Opération de règlement" --locale fr;
constraint NotNull --onProperty sttlmtOpHint;

field number --named amt --type java.math.BigDecimal;
description add-field-description --onProperty amt --title "Amount" --text "Amount";
description add-field-description --onProperty amt --title "Montant" --text "Montant" --locale fr;

field custom --named amtSide --type ~.jpa.SlsAmtSide;
description add-field-description --onProperty amtSide --title "Amount Side" --text "Amount Side";
description add-field-description --onProperty amtSide --title "Sens Montant" --text "Sens Montant" --locale fr;
enum enumerated-field --onProperty amtSide;
constraint NotNull --onProperty amtSide;

field number --named blnce --type java.math.BigDecimal;
description add-field-description --onProperty blnce --title "Balance" --text "Balance";
description add-field-description --onProperty blnce --title "Balance" --text "Balance" --locale fr;

field custom --named blnceSide --type ~.jpa.SlsAmtSide;
description add-field-description --onProperty blnceSide --title "Balance Side" --text "Balance Side";
description add-field-description --onProperty blnceSide --title "Sens Balance" --text "Sens Balance" --locale fr;
enum enumerated-field --onProperty blnceSide;
constraint NotNull --onProperty blnceSide;

field temporal --type TIMESTAMP --named evntDt; 
description add-field-description --onProperty evntDt --title "Event Date" --text "Event Date";
description add-field-description --onProperty evntDt --title "Date Evenement" --text "Date Evenement" --locale fr;
constraint NotNull --onProperty evntDt;

cd ~~ ;

repogen setup;

repogen new-repository --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys;

cd ~~;

mvn clean install -DskipTests;













set ACCEPT_DEFAULTS true;

new-project --named adcshdwr.server --topLevelPackage org.adorsys.adcshdwr --finalName adcshdwr.server --projectFolder adcshdwr.server;

as7 setup;

persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

set ACCEPT_DEFAULTS false;

enum setup;

repogen setup;

cd ~~;

@/* Entité Caisse */;
entity --named CdrCshDrawer --package ~.jpa;
description add-class-description --title "Cash Drawer" --text "A cash drawer.";
description add-class-description  --locale fr --title "Caisse" --text "Une caisse.";

field string --named cdrNbr;
description add-field-description --onProperty cdrNbr --title "Cash Drawer Number" --text "The number of this cash drawer.";
description add-field-description --onProperty cdrNbr --title "Numéro de Caisse" --text "Le numéro de cette caisse." --locale fr;
constraint NotNull --onProperty cdrNbr;

field string --named cashier;
description add-field-description --onProperty cashier --title "Cashier" --text "The user collecting the payment on this drawer.";
description add-field-description --onProperty cashier --title "Caissier" --text "Utilisateur percevant le paiement surcette caisse." --locale fr;
constraint NotNull --onProperty cashier;

field string --named closedBy;
description add-field-description --onProperty closedBy --title "Closed By" --text "The user who closed this cash drawer.";
description add-field-description --onProperty closedBy --title "Fermé Par" --text "Utilisateur ayant fermé la caisse." --locale fr;
constraint NotNull --onProperty closedBy;

field temporal --type TIMESTAMP --named opngDt; 
description add-field-description --onProperty opngDt --title "Opening Date" --text "The opening date of this drawer.";
description add-field-description --onProperty opngDt --title "Date d Ouverture" --text "La date d ouverture de cette caisse." --locale fr;
constraint NotNull --onProperty opngDt;

field temporal --type TIMESTAMP --named clsngDt; 
description add-field-description --onProperty clsngDt --title "Closing Date" --text "The closing date of this drawer.";
description add-field-description --onProperty clsngDt --title "Date de Fermeture" --text "La date de fermeture de cette caisse." --locale fr;

field number --named initialAmt --type java.math.BigDecimal;
description add-field-description --onProperty initialAmt --title "Initial Amount" --text "The initial amount.";
description add-field-description --onProperty initialAmt --title "Fond de Caisse" --text "Le fond initial de la caisse." --locale fr;

field number --named ttlCashIn --type java.math.BigDecimal;
description add-field-description --onProperty ttlCashIn --title "Total Cash In" --text "The total cash in.";
description add-field-description --onProperty ttlCashIn --title "Total Encaissement" --text "Encaissement totale." --locale fr;

field number --named ttlCashOut --type java.math.BigDecimal;
description add-field-description --onProperty ttlCashOut --title "Total Cash Out" --text "Total withdrawal from this drawer.";
description add-field-description --onProperty ttlCashOut --title "Total Retrait" --text "Total des decaissements éffectués en caisse." --locale fr;

field number --named ttlCash --type java.math.BigDecimal;
description add-field-description --onProperty ttlCash --title "Total Cash" --text "Total cash in this drawer.";
description add-field-description --onProperty ttlCash --title "Total Cash" --text "Total cash dans cette caisse." --locale fr;

field number --named ttlCheck --type java.math.BigDecimal;
description add-field-description --onProperty ttlCheck --title "Total Checks" --text "Total checks in this drawer.";
description add-field-description --onProperty ttlCheck --title "Total Chèque" --text "Total chèque dans cette caisse." --locale fr;

field number --named ttlCredCard --type java.math.BigDecimal;
description add-field-description --onProperty ttlCredCard --title "Total Credit Card" --text "Total credit cards by this drawer.";
description add-field-description --onProperty ttlCredCard --title "Total Carte Credit" --text "Total carte de credit par cette caisse." --locale fr;

field number --named ttlVchrIn --type java.math.BigDecimal;
description add-field-description --onProperty ttlVchrIn --title "Total Voucher In" --text "Total Voucher In";
description add-field-description --onProperty ttlVchrIn --title "Total Avoir Recu" --text "Total Avoir Recu" --locale fr;

field number --named ttlVchrOut --type java.math.BigDecimal;
description add-field-description --onProperty ttlVchrOut --title "Total Voucher Out" --text "Total Voucher Out";
description add-field-description --onProperty ttlVchrOut --title "Total Avoir Emis" --text "Total Avoir Emis" --locale fr;

cd ~~;

java new-enum-type --named CdrPymntMode --package ~.jpa;
enum add-enum-class-description --title "Payment Mode" --text "Mode of payment.";
enum add-enum-class-description --locale fr --title "Mode Paiement" --text "Mode de paiement.";
java new-enum-const CASH;
enum add-enum-constant-description --onConstant CASH --title "Cash" --text "Cash Payment";
enum add-enum-constant-description --locale fr --onConstant CASH --title "Caisse" --text "Paiement Caisse";
java new-enum-const CHECK;
enum add-enum-constant-description --onConstant CHECK --title "Check" --text "Check Payement";
enum add-enum-constant-description --locale fr --onConstant CHECK --title "Cheque" --text "Paiement Cheque";
java new-enum-const CREDIT_CARD;
enum add-enum-constant-description --onConstant CREDIT_CARD --title "Credit Card" --text "Credit card payment";
enum add-enum-constant-description --locale fr --onConstant CREDIT_CARD --title "Carte de crédit" --text "Paiement par cartes de crédit.";
java new-enum-const VOUCHER;
enum add-enum-constant-description --onConstant VOUCHER --title "Client Voucher" --text "Client voucher";
enum add-enum-constant-description --locale fr --onConstant VOUCHER --title "Avoir Client" --text "Avoir client";

cd ~~;

@/* Entite Ligne Payement */;
entity --named CdrPymntObject --package ~.jpa;
description add-class-description --title "Payment Object" --text "Payment Object";
description add-class-description  --locale fr --title "Objet du Paiement" --text "Objet du Paiement";

field string --named pymntNbr;
description add-field-description --onProperty pymntNbr --title "Payment Number" --text "The paiment number.";
description add-field-description --onProperty pymntNbr --title "Numéro du Paiement" --text "Le numéro du paiement." --locale fr;
constraint NotNull --onProperty pymntNbr;

field string --named origDocType;
description add-field-description --onProperty origDocType --title "Originating Document Type" --text "Originating Document Type";
description add-field-description --onProperty origDocType --title "Type du Document Originaire" --text "Type du Document Originaire" --locale fr;

field string --named origDocNbr;
description add-field-description --onProperty origDocNbr --title "Document Number" --text "The document number.";
description add-field-description --onProperty origDocNbr --title "Numéro du document" --text "Le numéro du document." --locale fr;
constraint NotNull --onProperty origDocNbr;

field string --named origItemNbr;
description add-field-description --onProperty origItemNbr --title "Item Number" --text "Item Number";
description add-field-description --onProperty origItemNbr --title "Numéro de la ligne originaire." --text "Numéro de la ligne originaire." --locale fr;

field number --named amt --type java.math.BigDecimal;
description add-field-description --onProperty amt --title "Payment Amount" --text "The payment amount.";
description add-field-description --onProperty amt --title "Montant du Paiement" --text "Le montant du paiement." --locale fr;
constraint NotNull --onProperty amt;

field string --named orgUnit;
description add-field-description --onProperty orgUnit --title "Organizational Unit" --text "Organizational Unit";
description add-field-description --onProperty orgUnit --title "Unité Organisationelle" --text "Unité Organisationelle" --locale fr;
constraint NotNull --onProperty orgUnit;

cd ~~;

@/* Entite Ligne Payement */;
entity --named CdrPymntItem --package ~.jpa;
description add-class-description --title "Payment Item" --text "A payment item.";
description add-class-description  --locale fr --title "Ligne de Paiement" --text "Une ligne de paiement";

field string --named pymntNbr;
description add-field-description --onProperty pymntNbr --title "Payment Number" --text "The paiment number.";
description add-field-description --onProperty pymntNbr --title "Numéro du Paiement" --text "Le numéro du paiement." --locale fr;
constraint NotNull --onProperty pymntNbr;

field custom --named pymntMode --type ~.jpa.CdrPymntMode;
description add-field-description --onProperty pymntMode --title "Payment Mode" --text "The Mode of this payment.";
description add-field-description --onProperty pymntMode --title "Mode de Paiement" --text "Le Mode de paiement." --locale fr;
enum enumerated-field --onProperty pymntMode;
constraint NotNull --onProperty pymntMode;

field string --named pymntDocType;
description add-field-description --onProperty pymntDocType --title "Payment Document Type" --text "Payment Document Type";
description add-field-description --onProperty pymntDocType --title "Type du Document du Paiement" --text "Type du Document du Paiement" --locale fr;

field string --named pymntDocNbr;
description add-field-description --onProperty pymntDocNbr --title "Payment Document Number" --text "The payment document number.";
description add-field-description --onProperty pymntDocNbr --title "Numéro du Document de Payment" --text "Le numéro du document de payement." --locale fr;

field number --named amt --type java.math.BigDecimal;
description add-field-description --onProperty amt --title "Payment Amount" --text "The payment amount.";
description add-field-description --onProperty amt --title "Montant du Paiement" --text "Le montant du paiement." --locale fr;
constraint NotNull --onProperty amt;

field number --named rcvdAmt --type java.math.BigDecimal;
description add-field-description --onProperty rcvdAmt --title "Received Amount" --text "The amount received from the payment.";
description add-field-description --onProperty rcvdAmt --title "Montant Reçue" --text "Le montant reçue du paiement." --locale fr;

field number --named diffAmt --type java.math.BigDecimal;
description add-field-description --onProperty diffAmt --title "Difference" --text "The difference (amount returned to payer).";
description add-field-description --onProperty diffAmt --title "Différence" --text "La différence (montant retourné au client)." --locale fr;

cd ~~;

@/* Entité  paiement */;
entity --named CdrPymnt --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Payment" --text "A payment.";
description add-class-description  --locale fr --title "Paiement" --text "Un paiement.";

field string --named pymntNbr;
description add-field-description --onProperty pymntNbr --title "Payment Number" --text "The paiment number.";
description add-field-description --onProperty pymntNbr --title "Numéro du Paiement" --text "Le numéro du paiement." --locale fr;
constraint NotNull --onProperty pymntNbr;

field temporal --type TIMESTAMP --named pymntDt; 
description add-field-description --onProperty pymntDt --title "Payment Date" --text "The payment date.";
description add-field-description --onProperty pymntDt --title "Date de Paiement" --text "La date de paiement." --locale fr;
constraint NotNull --onProperty pymntDt;

field temporal --type TIMESTAMP --named valueDt; 
description add-field-description --onProperty valueDt --title "Value Date" --text "Value date.";
description add-field-description --onProperty valueDt --title "Valorisation" --text "Valorisation" --locale fr;
constraint NotNull --onProperty valueDt;

field number --named amt --type java.math.BigDecimal;
description add-field-description --onProperty amt --title "Payment Amount" --text "The payment amount.";
description add-field-description --onProperty amt --title "Montant du Paiement" --text "Le montant du paiement." --locale fr;

field string --named cashier;
description add-field-description --onProperty cashier --title "Cashier" --text "The user collecting the payment.";
description add-field-description --onProperty cashier --title "Caissier" --text "L utilisateur percevant le paiement." --locale fr;
constraint NotNull --onProperty cashier;

field string --named cdrNbr;
description add-field-description --onProperty cdrNbr --title "Cash Drawer" --text "The cash drawer in use.";
description add-field-description --onProperty cdrNbr --title "Caisse" --text "La caisse utilisé." --locale fr;

field string --named paidBy;
description add-field-description --onProperty paidBy --title "Paid By" --text "Paid By.";
description add-field-description --onProperty paidBy --title "Payer Par" --text "Payer Par." --locale fr;
constraint NotNull --onProperty paidBy;

field string --named rcptNbr;
description add-field-description --onProperty rcptNbr --title "Paid By" --text "Paid By.";
description add-field-description --onProperty rcptNbr --title "Payer Par" --text "Payer Par." --locale fr;
constraint NotNull --onProperty rcptNbr;

field temporal --type TIMESTAMP --named rcptPrntDt; 
description add-field-description --onProperty rcptPrntDt --title "Payment Date" --text "The payment date.";
description add-field-description --onProperty rcptPrntDt --title "Date de Paiement" --text "La date de paiement." --locale fr;

cd ~~;

@/* Direct Sales */;
entity --named CdrDsArtItem --package ~.jpa;
description add-class-description --title "Direct Sales Item" --text "The direct sales item.";
description add-class-description  --locale fr --title "Ligne de Vente Directe" --text "Ligne de Vente Directe";

field string --named dsNbr;
description add-field-description --onProperty dsNbr --title "Direct Sales Number" --text "Direct Sales Number";
description add-field-description --onProperty dsNbr --title "Numéro de la vente directe" --text "Numéro de la vente directe" --locale fr;
constraint NotNull --onProperty dsNbr;

field string --named lotPic;
description add-field-description --onProperty lotPic --title "Lot Identifier" --text "The identifier of this lot. Might be printet as a bar code and put on each article of this lot.";
description add-field-description --onProperty lotPic --title "Identifiant du Lot" --text "Le code identifiant ce lot. Pourait etre imprimé comme code bar et posé su chaque produit de ce lot." --locale fr;
constraint NotNull --onProperty lotPic;

field string --named artPic;
description add-field-description --onProperty artPic --title "Article PIC" --text "The article code";
description add-field-description --onProperty artPic --title "CIP Article" --text "Code article" --locale fr;
constraint NotNull --onProperty artPic;

field number --named soldQty --type java.math.BigDecimal;
description add-field-description --onProperty soldQty --title "Quantity Sold" --text "The quantity sold in this line.";
description add-field-description --onProperty soldQty --title "Quantité Vendus" --text "La quantité vendue dans cette ligne." --locale fr;
constraint NotNull --onProperty soldQty;

field number --named returnedQty --type java.math.BigDecimal;
description add-field-description --onProperty returnedQty --title "Quantity Returned" --text "The quantity returned in this line.";
description add-field-description --onProperty returnedQty --title "Quantité Retournée" --text "La quantité retournée dans cette ligne." --locale fr;

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

field number --named restockgFees --type java.math.BigDecimal;
description add-field-description --onProperty restockgFees --title "Restocking Fees" --text "Restocking Fees";
description add-field-description --onProperty restockgFees --title "Frais de Restockage" --text "Frais de Restockage" --locale fr;

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

entity --named CdrDsPymntItem --package ~.jpa;
description add-class-description --title "Payment Item" --text "A payment item.";
description add-class-description  --locale fr --title "Ligne de Paiement" --text "Une ligne de paiement";

field string --named dsNbr;
description add-field-description --onProperty dsNbr --title "Direct Sales Number" --text "Direct Sales Number";
description add-field-description --onProperty dsNbr --title "Numéro de la vente directe" --text "Numéro de la vente directe" --locale fr;
constraint NotNull --onProperty dsNbr;

field custom --named pymntMode --type ~.jpa.CdrPymntMode;
description add-field-description --onProperty pymntMode --title "Payment Mode" --text "The Mode of this payment.";
description add-field-description --onProperty pymntMode --title "Mode de Paiement" --text "Le Mode de paiement." --locale fr;
enum enumerated-field --onProperty pymntMode;
constraint NotNull --onProperty pymntMode;

field string --named pymntDocType;
description add-field-description --onProperty pymntDocType --title "Payment Document Type" --text "Payment Document Type";
description add-field-description --onProperty pymntDocType --title "Type du Document du Paiement" --text "Type du Document du Paiement" --locale fr;

field string --named pymntDocNbr;
description add-field-description --onProperty pymntDocNbr --title "Payment Document Number" --text "The payment document number.";
description add-field-description --onProperty pymntDocNbr --title "Numéro du Document de Payment" --text "Le numéro du document de payement." --locale fr;

field number --named amt --type java.math.BigDecimal;
description add-field-description --onProperty amt --title "Payment Amount" --text "The payment amount.";
description add-field-description --onProperty amt --title "Montant du Paiement" --text "Le montant du paiement." --locale fr;
constraint NotNull --onProperty amt;

field number --named rcvdAmt --type java.math.BigDecimal;
description add-field-description --onProperty rcvdAmt --title "Received Amount" --text "The amount received from the payment.";
description add-field-description --onProperty rcvdAmt --title "Montant Reçue" --text "Le montant reçue du paiement." --locale fr;

field number --named diffAmt --type java.math.BigDecimal;
description add-field-description --onProperty diffAmt --title "Difference" --text "The difference (amount returned to payer).";
description add-field-description --onProperty diffAmt --title "Différence" --text "La différence (montant retourné au client)." --locale fr;

cd ~~;

@/* Direct Sales */;
entity --named CdrDrctSales --package ~.jpa;
description add-class-description --title "Direct Sales" --text "Direct Sales";
description add-class-description  --locale fr --title "Vente Directe" --text "Vente Directe";

field string --named dsNbr;
description add-field-description --onProperty dsNbr --title "Direct Sales Number" --text "Direct Sales Number";
description add-field-description --onProperty dsNbr --title "Numéro de Vente Directe" --text "Numéro de Vente Directe" --locale fr;
constraint NotNull --onProperty dsNbr;

field string --named dsCur;
description add-field-description --onProperty dsCur --title "Currency" --text "Currency of this sales";
description add-field-description --onProperty dsCur --title "Dévise" --text "Dévise de cette vente" --locale fr;

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

field string --named cashier;
description add-field-description --onProperty cashier --title "Cashier" --text "The user collecting the payment.";
description add-field-description --onProperty cashier --title "Caissier" --text "L utilisateur percevant le paiement." --locale fr;
constraint NotNull --onProperty cashier;

field string --named cdrNbr;
description add-field-description --onProperty cdrNbr --title "Cash Drawer" --text "The cash drawer in use.";
description add-field-description --onProperty cdrNbr --title "Caisse" --text "La caisse utilisé." --locale fr;

field string --named rcptNbr;
description add-field-description --onProperty rcptNbr --title "Paid By" --text "Paid By.";
description add-field-description --onProperty rcptNbr --title "Payer Par" --text "Payer Par." --locale fr;
constraint NotNull --onProperty rcptNbr;

field temporal --type TIMESTAMP --named rcptPrntDt; 
description add-field-description --onProperty rcptPrntDt --title "Payment Date" --text "The payment date.";
description add-field-description --onProperty rcptPrntDt --title "Date de Paiement" --text "La date de paiement." --locale fr;


@/* Accounts Receivable */;
@/* Entité AvoirClient */;
entity --named CdrCstmrVchr --package ~.jpa;
description add-class-description --title "Client Voucher" --text "Client voucher";
description add-class-description  --locale fr --title "Avoir Client" --text "Avoir client";

field string --named vchrNbr;
description add-field-description --onProperty vchrNbr --title "Voucher Number" --text "The client voucher number.";
description add-field-description --onProperty vchrNbr --title "Numéro de cet Avoir" --text "Le numéro de l avoir du client." --locale fr;
constraint NotNull --onProperty vchrNbr;

field string --named dsNbr;
description add-field-description --onProperty dsNbr --title "Direct Sales Number" --text "Direct Sales Number";
description add-field-description --onProperty dsNbr --title "Numéro de la vente directe" --text "Numéro de la vente directe" --locale fr;
constraint NotNull --onProperty dsNbr;

field number --named amt --type java.math.BigDecimal;
description add-field-description --onProperty amt --title "Payment Amount" --text "The payment amount.";
description add-field-description --onProperty amt --title "Montant du Paiement" --text "Le montant du paiement." --locale fr;
constraint NotNull --onProperty amt;

field string --named cstmrNbr;
description add-field-description --onProperty cstmrNbr --title "Customer" --text "Customer.";
description add-field-description --onProperty cstmrNbr --title "Client" --text "Client." --locale fr;

field string --named cstmrName;
description add-field-description --onProperty cstmrName --title "Customer Name" --text "Customer Name.";
description add-field-description --onProperty cstmrName --title "Nom du Client" --text "Nom du Client." --locale fr;

field boolean --named canceled --primitive false; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the voucher was canceled or not.";
description add-field-description --onProperty canceled --title "Annulé" --text "Indique si l avoir  a été annulé ou non." --locale fr;

field string --named cashier;
description add-field-description --onProperty cashier --title "Cashier" --text "The user collecting the payment.";
description add-field-description --onProperty cashier --title "Caissier" --text "L utilisateur percevant le paiement." --locale fr;
constraint NotNull --onProperty cashier;

field string --named cdrNbr;
description add-field-description --onProperty cdrNbr --title "Cash Drawer" --text "The cash drawer in use.";
description add-field-description --onProperty cdrNbr --title "Caisse" --text "La caisse utilisé." --locale fr;

field temporal --type TIMESTAMP --named prntDt; 
description add-field-description --onProperty prntDt --title "Printed Date" --text "Printed date.";
description add-field-description --onProperty prntDt --title "Date Imprimé" --text "Date Imprimé" --locale fr;

field number --named amtUsed --type java.math.BigDecimal;
description add-field-description --onProperty amtUsed --title "Amount Used" --text "Amount used.";
description add-field-description --onProperty amtUsed --title "Montant Utilisé" --text "Montant utilisé." --locale fr;

field boolean --named settled --primitive false; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the voucher is settled or not.";
description add-field-description --onProperty settled --title "Soldé" --text "Indique si l avoir est soldé ou non." --locale fr;

field number --named restAmt --type java.math.BigDecimal;
description add-field-description --onProperty restAmt --title "Rest Amount" --text "The remaining credit on this voucher.";
description add-field-description --onProperty restAmt --title "Montant Restant" --text "Rest de l avoir client" --locale fr;

cd ~~;

@/* Payment History*/;
entity --named CdrPymtHstry --package ~.jpa;
description add-class-description --title "Payment History" --text "Payment history";
description add-class-description  --locale fr --title "Historique Payment" --text "Historique Payment";

field string --named pymntNbr;
description add-field-description --onProperty pymntNbr --title "Payment Number" --text "The payment number.";
description add-field-description --onProperty pymntNbr --title "Numéro du Paiement" --text "Le numéro du paiement" --locale fr;
constraint NotNull --onProperty pymntNbr;

field string --named cdrNbr;
description add-field-description --onProperty cdrNbr --title "Cash Drawer Number" --text "Cash Drawer Number";
description add-field-description --onProperty cdrNbr --title "Numéro de Caisse" --text "Numéro de Caisse" --locale fr;
constraint NotNull --onProperty cdrNbr;

cd ~~;

@/* Direct Sales History*/;
entity --named CdrDsHstry --package ~.jpa;
description add-class-description --title "Direct Sales History" --text "Direct sales history";
description add-class-description  --locale fr --title "Historique Vente Directe" --text "Historique Vente Directe.";

field string --named dsNbr;
description add-field-description --onProperty dsNbr --title "Direct Sales Number" --text "Direct Sales Number";
description add-field-description --onProperty dsNbr --title "Numéro de Vente" --text "Numéro de Vente" --locale fr;
constraint NotNull --onProperty dsNbr;

field string --named cdrNbr;
description add-field-description --onProperty cdrNbr --title "Cash Drawer Number" --text "Cash Drawer Number";
description add-field-description --onProperty cdrNbr --title "Numéro de Caisse" --text "Numéro de Caisse" --locale fr;
constraint NotNull --onProperty cdrNbr;

cd ~~;

@/* Vouvher History*/;
entity --named CdrVchrHstry --package ~.jpa;
description add-class-description --title "Voucher History" --text "Voucher history";
description add-class-description  --locale fr --title "Historique Avoir" --text "Historique avoir";

field string --named vchrNbr;
description add-field-description --onProperty vchrNbr --title "Voucher Number" --text "Voucher Number";
description add-field-description --onProperty vchrNbr --title "Numéro Avoir" --text "Numéro de cet avoir" --locale fr;
constraint NotNull --onProperty vchrNbr;

field string --named cdrNbr;
description add-field-description --onProperty cdrNbr --title "Cash Drawer Number" --text "Cash Drawer Number";
description add-field-description --onProperty cdrNbr --title "Numéro de Caisse" --text "Numéro de Caisse" --locale fr;
constraint NotNull --onProperty cdrNbr;

cd ~~;

repogen setup;

repogen new-repository --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys;

cd ~~;

mvn clean install -DskipTests;













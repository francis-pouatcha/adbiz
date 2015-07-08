
set ACCEPT_DEFAULTS true;

new-project --named adprocmt.server --topLevelPackage org.adorsys.adprocmt --finalName adprocmt.server --projectFolder adprocmt.server;

as7 setup;

persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

set ACCEPT_DEFAULTS false;

enum setup;

repogen setup;

cd ~~ ;

@/* Enum ProcurementOrderType */;
java new-enum-type --named ProcmtPOType --package ~.jpa;
enum add-enum-class-description --title "Procurement Order Type" --text "Procurement order type";
enum add-enum-class-description  --locale fr --title "Type Commande Fournisseur" --text "Type commande fournisseur";
java new-enum-const ORDINARY;
enum add-enum-constant-description --onConstant ORDINARY --title "Ordinary" --text "Ordinary procurement order";
enum add-enum-constant-description --onConstant ORDINARY --title "Ordinaire" --text "Commande fournisseur ordinaire" --locale fr ;
java new-enum-const PACKAGED;
enum add-enum-constant-description --onConstant PACKAGED --title "Packaged" --text "Packaged procurement order";
enum add-enum-constant-description --onConstant PACKAGED --title "Emballée" --text "Commande fournisseur emballée" --locale fr ;
java new-enum-const SPECIAL;
enum add-enum-constant-description --onConstant SPECIAL --title "Special" --text "Special procurement order";
enum add-enum-constant-description --onConstant SPECIAL --title "Spéciale" --text "Commande fournisseur spéciale" --locale fr ;

cd ~~;

@/* Enum  ProcmtPOTriggerMode */;
java new-enum-type --named ProcmtPOTriggerMode --package ~.jpa;
enum add-enum-class-description --title "Procurement Order Trigger Mode" --text "Procurement order trigger mode";
enum add-enum-class-description  --locale fr --title "Criteres de Preparation de Commande Fournisseur" --text "Criteres pour initialisation de commande fournisseur";
java new-enum-const MANUAL;
enum add-enum-constant-description --onConstant MANUAL --title "Manual" --text "Manual procurement order trigger mode";
enum add-enum-constant-description --onConstant MANUAL --title "Manuelle" --text "Initialisation de commande fournisseur manuelle" --locale fr ;
java new-enum-const STOCK_SHORTAGE;
enum add-enum-constant-description --onConstant STOCK_SHORTAGE --title "Stock shortage" --text "Procurement order trigger on stock shortage";
enum add-enum-constant-description --onConstant STOCK_SHORTAGE --title "Pénurie de Stock" --text "Commande fournisseur initialisé sur pénurie" --locale fr ;
java new-enum-const MOST_SOLD;
enum add-enum-constant-description --onConstant MOST_SOLD --title "Most product sold" --text "Procurement order trigerred for most sold product";
enum add-enum-constant-description --onConstant MOST_SOLD --title "Produits les Plus Vendu" --text "Commande initialisé pour les produits les plus vendus" --locale fr ;

cd ~~;

@/* Enum type ProcmtPOStatus State */;
java new-enum-type --named ProcmtPOStatus --package ~.jpa;
enum add-enum-class-description --title "Inventory Status" --text "The state of an inventory";
enum add-enum-class-description --title "Status Inventaire" --text "État du document" --locale fr;
java new-enum-const SUSPENDED ;
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspended" --text "Suspended document";
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspendu" --text "Document suspendu" --locale fr ;
java new-enum-const ONGOING ;
enum add-enum-constant-description --onConstant ONGOING --title "Ongoing" --text "Ongoing document";
enum add-enum-constant-description --onConstant ONGOING --title "En Cours" --text "Document en cours" --locale fr ;
java new-enum-const RESTORED ;
enum add-enum-constant-description --onConstant RESTORED --title "Restored" --text "Restored document";
enum add-enum-constant-description --onConstant RESTORED --title "Restauré" --text "Document restauré" --locale fr ;
java new-enum-const CLOSED ;
enum add-enum-constant-description --onConstant CLOSED --title "Closed" --text "Closed document";
enum add-enum-constant-description --onConstant CLOSED --title "Cloturé" --text "Document cloturé" --locale fr ;

cd ~~;

@/* Entity ProcurementOrderItem */;
entity --named PrcmtPOItem --package ~.jpa;
description add-class-description --title "Procurement Order Item" --text "Procurement order item";
description add-class-description  --locale fr --title "Ligne Commande Fournisseur" --text "Ligne de commande fournisseur";

field string --named poNbr;
description add-field-description --onProperty poNbr --title "Procurement Order Number" --text "The procurement order number";
description add-field-description --onProperty poNbr --title "Numéro Commande Fournisseur" --text "Numéro de la commande fournisseur" --locale fr;
constraint NotNull --onProperty poNbr;

field string --named artPic; 
description add-field-description --onProperty artPic --title "Main PIC" --text "The main product identification code used to identify lots during sales.";
description add-field-description --onProperty artPic --title "CIP Principal" --text "Le code identifiant produit principal, utilisé pour identifier les lots de produits lors de la vente." --locale fr;
constraint NotNull --onProperty artPic;

field string --named supplier;
description add-field-description --onProperty supplier --title "Supplier" --text "The supplier mentioned on the delivery slip while products are being delivered.";
description add-field-description --onProperty supplier --title "Fournisseur" --text "Le fournisseur mentionné sur le bordereau de livraison des produits qui entrent en stock." --locale fr;

field number --named qtyOrdered --type java.math.BigDecimal;
description add-field-description --onProperty qtyOrdered --title "Quantity Ordered" --text "The quantity ordered in this lot.";
description add-field-description --onProperty qtyOrdered --title "Quantité Commandée" --text "La quantité de produits commandés dans le lot." --locale fr;
constraint NotNull --onProperty qtyOrdered;

field number --named stkQtyPreOrder --type java.math.BigDecimal;
description add-field-description --onProperty stkQtyPreOrder --title "Stock Quantity Before Order" --text "The quantity in stock at the moment of this procurement..";
description add-field-description --onProperty stkQtyPreOrder --title "Quantité en Stock Avant Commande" --text "La quantité en stock au moment de cette commande." --locale fr;
constraint NotNull --onProperty stkQtyPreOrder;

field number --named pppuPreTax --type java.math.BigDecimal;
description add-field-description --onProperty pppuPreTax --title "Purchase Price per Unit" --text "The purchase price per unit.";
description add-field-description --onProperty pppuPreTax --title "Prix d Achat Unitaire" --text "Prix d achat unitaire." --locale fr;

field string --named pppuCur;
description add-field-description --onProperty pppuCur --title "Currency of the Purchase Price per Unit" --text "The currency of the purchase price per unit.";
description add-field-description --onProperty pppuCur --title "Dévise du Prix d Achat Unitaire" --text "La dévise du prix d achat unitaire." --locale fr;

field number --named grossPPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty grossPPPreTax --title "Gross Purchase Amount Pre Tax" --text "Gross Purchase Amount Pre Tax";
description add-field-description --onProperty grossPPPreTax --title "Montant Brut Achat Hors Taxes" --text "Montant Brut Achat Hors Taxes" --locale fr;

field number --named rebate --type java.math.BigDecimal;
description add-field-description --onProperty rebate --title "Rebate Amount" --text "Rebate or Discount amount.";
description add-field-description --onProperty rebate --title "Montant Remise" --text "Montant de la remise" --locale fr;

field number --named netPPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty netPPPreTax --title "Net Purchase Amount Pre Tax" --text "Net Purchase Amount Pre Tax";
description add-field-description --onProperty netPPPreTax --title "Montant Net Achat Hors Taxes" --text "Montant Net Achat Hors Taxes" --locale fr;

field number --named vatPct --type java.math.BigDecimal;
description add-field-description --onProperty vatPct --title "VAT Rate" --text "The VAT rate in percent.";
description add-field-description --onProperty vatPct --title "Taux TVA" --text "The taux de TVA en pourcent." --locale fr;

field number --named vatAmount --type java.math.BigDecimal;
description add-field-description --onProperty vatAmount --title "VAT Amount" --text "Tax amount for this purchase order.";
description add-field-description --onProperty vatAmount --title "Montant TVA" --text "Montant de la taxe de l approvisionement." --locale fr;

field number --named netPPTaxIncl --type java.math.BigDecimal;
description add-field-description --onProperty netPPTaxIncl --title "Purchase Amount Tax Included" --text "Purchase Amount Tax Included";
description add-field-description --onProperty netPPTaxIncl --title "Montant Achat TTC" --text "Le montant achat toute taxe comprise." --locale fr;

field string --named creatingUsr;
description add-field-description --onProperty creatingUsr --title "Creating User" --text "The user creating this procurement order item";
description add-field-description --onProperty creatingUsr --title "Agent Créateur" --text "Utilisateur ayant crée cet ligne de commande" --locale fr;
constraint NotNull --onProperty creatingUsr;

field temporal --type TIMESTAMP --named createdDt; 
description add-field-description --onProperty createdDt --title "Created Date" --text "The creation date";
description add-field-description --onProperty createdDt --title "Date de Creation" --text "La date de creation" --locale fr;
format add-date-pattern --onProperty createdDt --pattern "dd-MM-yyyy HH:mm"; 
constraint NotNull --onProperty createdDt;

cd ~~

@/* Entity PrcmtProcOrder */;
entity --named PrcmtProcOrder --package ~.jpa;
description add-class-description --title "Procurement Order" --text "Procurement order";
description add-class-description  --locale fr --title "Commande Fournisseur" --text "Commande fournisseur";

field temporal --type TIMESTAMP --named orderDt; 
description add-field-description --onProperty orderDt --title "Submission Date" --text "Date of submission of the order to Suplier created from the module Ubipharm";
description add-field-description --onProperty orderDt --title "Date de Soumission" --text "Date de soumission de la commande au fournisseur crée à partir du module d Ubipharm" --locale fr;
constraint NotNull --onProperty orderDt;

field string --named poNbr;
description add-field-description --onProperty poNbr --title "Procurement Order Number" --text "The procurement order number";
description add-field-description --onProperty poNbr --title "Numéro Commande Fournisseur" --text "Numéro de la commande fournisseur" --locale fr;
constraint NotNull --onProperty poNbr;

field string --named supplier;
description add-field-description --onProperty supplier --title "Supplier" --text "The supplier mentioned on the delivery slip while products are being delivered.";
description add-field-description --onProperty supplier --title "Fournisseur" --text "Le fournisseur mentionné sur le bordereau de livraison des produits qui entrent en stock." --locale fr;

field string --named poCur;
description add-field-description --onProperty poCur --title "Currency of the Purchase Order" --text "Currency of the Purchase Order";
description add-field-description --onProperty poCur --title "Dévise de la Commande Fournisseur" --text "Dévise de la Commande Fournisseur" --locale fr;

field number --named grossPPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty grossPPPreTax --title "Gross Purchase Amount Pre Tax" --text "Gross Purchase Amount Pre Tax";
description add-field-description --onProperty grossPPPreTax --title "Montant Brut Achat Hors Taxes" --text "Montant Brut Achat Hors Taxes" --locale fr;

field number --named rebate --type java.math.BigDecimal;
description add-field-description --onProperty rebate --title "Rebate Amount" --text "Rebate or Discount amount.";
description add-field-description --onProperty rebate --title "Montant Remise" --text "Montant de la remise" --locale fr;

field number --named netPPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty netPPPreTax --title "Net Purchase Amount Pre Tax" --text "Net Purchase Amount Pre Tax";
description add-field-description --onProperty netPPPreTax --title "Montant Net Achat Hors Taxes" --text "Montant Net Achat Hors Taxes" --locale fr;

field number --named vatAmount --type java.math.BigDecimal;
description add-field-description --onProperty vatAmount --title "VAT Amount" --text "Tax amount for this purchase order.";
description add-field-description --onProperty vatAmount --title "Montant TVA" --text "Montant de la taxe de l approvisionement." --locale fr;

field number --named netPPTaxIncl --type java.math.BigDecimal;
description add-field-description --onProperty netPPTaxIncl --title "Purchase Amount Tax Included" --text "Purchase Amount Tax Included";
description add-field-description --onProperty netPPTaxIncl --title "Montant Achat TTC" --text "Le montant achat toute taxe comprise." --locale fr;

field number --named pymtDscntPct --type java.math.BigDecimal;
description add-field-description --onProperty pymtDscntPct --title "Paymeny Discount" --text "Payment Discount";
description add-field-description --onProperty pymtDscntPct --title "Excompte" --text "Escompte sur Payment" --locale fr;

field number --named pymtDscntAmt --type java.math.BigDecimal;
description add-field-description --onProperty pymtDscntAmt --title "Payment Discount Amount" --text "Discount amount for promt payment.";
description add-field-description --onProperty pymtDscntAmt --title "Montant Excompte" --text "Montant de la remise pour payement anticipe" --locale fr;

field number --named netPurchAmt --type java.math.BigDecimal;
description add-field-description --onProperty netPurchAmt --title "Net Purchase Amount" --text "Net Purchase Amount";
description add-field-description --onProperty netPurchAmt --title "Montant Net Achat" --text "Montant Net Achat" --locale fr;

field number --named rdngDscntAmt --type java.math.BigDecimal;
description add-field-description --onProperty rdngDscntAmt --title "Rounding Discount Amount" --text "Rounding Discount Amount";
description add-field-description --onProperty rdngDscntAmt --title "Montant Remise pour Arrondi" --text "Montant Remise pour Arrondi" --locale fr;

field number --named netAmtToPay --type java.math.BigDecimal;
description add-field-description --onProperty netAmtToPay --title "Net Amount to Pay" --text "Teh net amount to pay.";
description add-field-description --onProperty netAmtToPay --title "Montant net a payer" --text "Montant de la remise de l approvisionement." --locale fr;

field temporal --type TIMESTAMP --named submitedDt; 
description add-field-description --onProperty submitedDt --title "Submission Date" --text "Date of submission of the order to Suplier created from the module Ubipharm";
description add-field-description --onProperty submitedDt --title "Date de Soumission" --text "Date de soumission de la commande au fournisseur crée à partir du module d Ubipharm" --locale fr;

field string --named ordrngOrgUnit;
description add-field-description --onProperty ordrngOrgUnit --title "Organization Unit" --text "Organization Unit";
description add-field-description --onProperty ordrngOrgUnit --title "Departement Receptrice" --text "Departement Receptrice" --locale fr;

field string --named creatingUsr;
description add-field-description --onProperty creatingUsr --title "Creating User" --text "The user creating this procurement order item";
description add-field-description --onProperty creatingUsr --title "Agent Créateur" --text "Utilisateur ayant crée cet ligne de commande" --locale fr;
constraint NotNull --onProperty creatingUsr;

field temporal --type TIMESTAMP --named createdDt; 
description add-field-description --onProperty createdDt --title "Created Date" --text "The creation date";
description add-field-description --onProperty createdDt --title "Date de Creation" --text "La date de creation" --locale fr;
format add-date-pattern --onProperty createdDt --pattern "dd-MM-yyyy HH:mm"; 
constraint NotNull --onProperty createdDt;

field custom --named poTriggerMode --type ~.jpa.ProcmtPOTriggerMode;
description add-field-description --onProperty poTriggerMode --title "Procurement Order Trigger Mode" --text "Procurement Order Trigger Mode.";
description add-field-description --onProperty poTriggerMode --title "Criteres de Preparation" --text "Criteres de Preparation de Commande Fournisseur." --locale fr;
enum enumerated-field --onProperty poTriggerMode;
constraint NotNull --onProperty poTriggerMode;

field custom --named poType --type ~.jpa.ProcmtPOType;
description add-field-description --onProperty poType --title "Procurement Order Type" --text "Procurement Order Type.";
description add-field-description --onProperty poType --title "Type Commande Fournisseur" --text "Type Commande Fournisseur." --locale fr;
enum enumerated-field --onProperty poType ;
constraint NotNull --onProperty poType;

field custom --named poStatus --type ~.jpa.ProcmtPOStatus;
description add-field-description --onProperty poStatus --title "Status" --text "The status of this purchase order.";
description add-field-description --onProperty poStatus --title "Statut" --text "État de cette commande fournisseur." --locale fr;
enum enumerated-field --onProperty poStatus ;
constraint NotNull --onProperty poStatus;

@/* ==================================== */;
cd ~~;

@/* Enum type ProcmtDlvryStatus State */;
java new-enum-type --named ProcmtDlvryStatus --package ~.jpa;
enum add-enum-class-description --title "Delivery Status" --text "The state of a delivery";
enum add-enum-class-description --title "Status Livraison" --text "État de la livraison" --locale fr;
java new-enum-const SUSPENDED ;
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspended" --text "Suspended";
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspendu" --text "Suspendu" --locale fr ;
java new-enum-const ONGOING ;
enum add-enum-constant-description --onConstant ONGOING --title "Ongoing" --text "Ongoing";
enum add-enum-constant-description --onConstant ONGOING --title "En Cours" --text "En cours" --locale fr ;
java new-enum-const RESTORED ;
enum add-enum-constant-description --onConstant RESTORED --title "Restored" --text "Restored";
enum add-enum-constant-description --onConstant RESTORED --title "Restauré" --text "Restauré" --locale fr ;
java new-enum-const CLOSED ;
enum add-enum-constant-description --onConstant CLOSED --title "Closed" --text "Closed";
enum add-enum-constant-description --onConstant CLOSED --title "Cloturé" --text "Cloturé" --locale fr ;

cd ~~;

@/* Entity DeliveryItem */;
entity --named PrcmtDlvryItem --package ~.jpa;
description add-class-description --title "Delivery Order Item" --text "Delivery order item";
description add-class-description  --locale fr --title "Ligne de livraison" --text "la ligne de livraison";

field string --named dlvryNbr;
description add-field-description --onProperty dlvryNbr --title "Delivery  Number" --text "The Delivery order number";
description add-field-description --onProperty dlvryNbr --title "Numéro de la Livraison" --text "Numéro de la livraison" --locale fr;
constraint NotNull --onProperty dlvryNbr;

field string --named poNbr;
description add-field-description --onProperty poNbr --title "Procurement Order Number" --text "The procurement order number";
description add-field-description --onProperty poNbr --title "Numéro Commande Fournisseur" --text "Numéro de la commande fournisseur" --locale fr;

field string --named lotPic;
description add-field-description --onProperty lotPic --title "Lot Identifier" --text "The identifier of this lot. Might be printet as a bar code and put on each article of this lot.";
description add-field-description --onProperty lotPic --title "Identifiant du Lot" --text "Le code identifiant ce lot. Pourait etre imprimé comme code bar et posé su chaque produit de ce lot." --locale fr;
constraint NotNull --onProperty lotPic;

field string --named artPic;
description add-field-description --onProperty artPic --title "Article PIC" --text "The article code";
description add-field-description --onProperty artPic --title "CIP Article" --text "Code article" --locale fr;
constraint NotNull --onProperty artPic;

field string --named supplier;
description add-field-description --onProperty supplier --title "Supplier" --text "The supplier mentioned on the delivery slip while products are being delivered.";
description add-field-description --onProperty supplier --title "Fournisseur" --text "Le fournisseur mentionné sur le bordereau de livraison des produits qui entrent en stock." --locale fr;

field string --named supplierPic;
description add-field-description --onProperty supplierPic --title "Supplier PIC" --text "PIC supplier for this article";
description add-field-description --onProperty supplierPic --title "CIP Fournisseur" --text "CIP forunisseur par ce lot" --locale fr;

field temporal --type TIMESTAMP --named expirDt; 
description add-field-description --onProperty expirDt --title "Expiration Date" --text "Expiration date for the article in this lot";
description add-field-description --onProperty expirDt --title "Date de Peremption" --text "Date de peremption du produit dans le lot" --locale fr;

field number --named qtyOrdered --type java.math.BigDecimal;
description add-field-description --onProperty qtyOrdered --title "Quantity Ordered" --text "The quantity ordered in this lot.";
description add-field-description --onProperty qtyOrdered --title "Quantité Commandée" --text "La quantité de produits commandés dans le lot." --locale fr;

field number --named qtyDlvrd --type java.math.BigDecimal;
description add-field-description --onProperty qtyDlvrd --title "Quantity Delivered" --text "The quantity delivered in this lot.";
description add-field-description --onProperty qtyDlvrd --title "Quantité Livrée" --text "La quantité de produits livrée dans le lot." --locale fr;
constraint NotNull --onProperty qtyDlvrd;

field number --named freeQty --type java.math.BigDecimal;
description add-field-description --onProperty freeQty --title "Free Quantity" --text "The auntity of products given by the supplier free of charge during purchasing. These articles are a value aded for the products in stock.";
description add-field-description --onProperty freeQty --title "Quantité Gratuite" --text "La quantité de produits fournis gratuitement par le fournisseur lors de l approvisionnement. Ces produits sont une plus value pour les produits dans le stock" --locale fr;

field number --named stkQtyPreDlvry --type java.math.BigDecimal;
description add-field-description --onProperty stkQtyPreDlvry --title "Stock Quantity Before Delivery" --text "Stock Quantity Before Delivery";
description add-field-description --onProperty stkQtyPreDlvry --title "Quantité en Stock avant Livraison" --text "Quantité en Stock avant Livraison" --locale fr;

field number --named pppuPreTax --type java.math.BigDecimal;
description add-field-description --onProperty pppuPreTax --title "Purchase Price per Unit" --text "The purchase price per unit.";
description add-field-description --onProperty pppuPreTax --title "Prix d Achat Unitaire" --text "Prix d achat unitaire." --locale fr;

field string --named pppuCur;
description add-field-description --onProperty pppuCur --title "Currency of the Purchase Price per Unit" --text "The currency of the purchase price per unit.";
description add-field-description --onProperty pppuCur --title "Dévise du Prix d Achat Unitaire" --text "La dévise du prix d achat unitaire." --locale fr;

field number --named grossPPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty grossPPPreTax --title "Gross Purchase Amount Pre Tax" --text "Gross Purchase Amount Pre Tax";
description add-field-description --onProperty grossPPPreTax --title "Montant Brut Achat Hors Taxes" --text "Montant Brut Achat Hors Taxes" --locale fr;

field number --named rebate --type java.math.BigDecimal;
description add-field-description --onProperty rebate --title "Rebate Amount" --text "Rebate or Discount amount.";
description add-field-description --onProperty rebate --title "Montant Remise" --text "Montant de la remise" --locale fr;

field number --named netPPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty netPPPreTax --title "Net Purchase Amount Pre Tax" --text "Net Purchase Amount Pre Tax";
description add-field-description --onProperty netPPPreTax --title "Montant Net Achat Hors Taxes" --text "Montant Net Achat Hors Taxes" --locale fr;

field number --named vatPct --type java.math.BigDecimal;
description add-field-description --onProperty vatPct --title "VAT Rate" --text "The VAT rate in percent.";
description add-field-description --onProperty vatPct --title "Taux TVA" --text "The taux de TVA en pourcent." --locale fr;

field number --named vatAmount --type java.math.BigDecimal;
description add-field-description --onProperty vatAmount --title "VAT Amount" --text "Tax amount for this purchase order.";
description add-field-description --onProperty vatAmount --title "Montant TVA" --text "Montant de la taxe de l approvisionement." --locale fr;

field number --named netPPTaxIncl --type java.math.BigDecimal;
description add-field-description --onProperty netPPTaxIncl --title "Purchase Amount Tax Included" --text "Purchase Amount Tax Included";
description add-field-description --onProperty netPPTaxIncl --title "Montant Achat TTC" --text "Le montant achat toute taxe comprise." --locale fr;

field number --named sppuPreTax --type java.math.BigDecimal;
description add-field-description --onProperty sppuPreTax --title "Sales Price per Unit" --text "The sales price per unit.";
description add-field-description --onProperty sppuPreTax --title "Prix de Vente Unitaire" --text "Prix de vente unitaire." --locale fr;

field number --named purchWrntyDys --type java.math.BigDecimal;
description add-field-description --onProperty purchWrntyDys --title "Purchase Warranty Time in Days" --text "Purchase Warranty Time in Days";
description add-field-description --onProperty purchWrntyDys --title "Temps de Guarranty sur Achat en Jours" --text "Temps de Guarranty sur Achat en Jours" --locale fr;

field number --named purchRtrnDays --type java.math.BigDecimal;
description add-field-description --onProperty purchRtrnDays --title "Return on Purchase in Days" --text "The time in days in which a return on purchase is accepted.";
description add-field-description --onProperty purchRtrnDays --title "Temps de Retour sur Achat en Jours" --text "Le delais de retour de cet article au fournisseur en jour." --locale fr;

field string --named sppuCur;
description add-field-description --onProperty sppuCur --title "Currency of the Sales Price per Unit" --text "The currency of the sales price per unit.";
description add-field-description --onProperty sppuCur --title "Dévise du Prix de Vente Unitaire" --text "La dévise du prix de vente unitaire." --locale fr;

field number --named minSppuHT --type java.math.BigDecimal;
description add-field-description --onProperty minSppuHT --title "Minimun Sales Price per Unit" --text "The minimum of the sales price per unit.";
description add-field-description --onProperty minSppuHT --title "Minimum du Prix de Vente Unitaire" --text "Le minimum du prix de vente unitaire." --locale fr;

field number --named vatSalesPct --type java.math.BigDecimal;
description add-field-description --onProperty vatSalesPct --title "VAT Rate" --text "The VAT rate in percent.";
description add-field-description --onProperty vatSalesPct --title "Taux TVA" --text "The taux de TVA en pourcent." --locale fr;

field number --named salesWrntyDys --type java.math.BigDecimal;
description add-field-description --onProperty salesWrntyDys --title "Sales Warranty Time in Days" --text "Sales Warranty Time in Days";
description add-field-description --onProperty salesWrntyDys --title "Temps de Guarranty sur Vente en Jours" --text "Temps de Guarranty sur Vente en Jours" --locale fr;

field number --named salesRtrnDays --type java.math.BigDecimal;
description add-field-description --onProperty salesRtrnDays --title "Return on Sales in Days" --text "The time in days in which a return on sales is accepted.";
description add-field-description --onProperty salesRtrnDays --title "Temps de Retour sur Vente en Jours" --text "Le delais de retour de cet article ar un client en jour." --locale fr;

field string --named creatingUsr;
description add-field-description --onProperty creatingUsr --title "Creating User" --text "The user creating this delivery order item";
description add-field-description --onProperty creatingUsr --title "Agent Créateur" --text "Utilisateur ayant crée cet ligne de livraison" --locale fr;
constraint NotNull --onProperty creatingUsr;

field temporal --type TIMESTAMP --named creationDt; 
description add-field-description --onProperty creationDt --title "Creation Date" --text "The creation date of this stock movement.";
description add-field-description --onProperty creationDt --title "Date de Création" --text "La date de création de cette movement de stock." --locale fr;
format add-date-pattern --onProperty creationDt --pattern "dd-MM-yyyy HH:mm"; 

cd ~~ ;

@/* Entity Delivery */;
entity --named PrcmtDelivery --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Delivery" --text "The Delivery";
description add-class-description  --locale fr --title "Livraison" --text "Une Livraison";

field string --named dlvryNbr;
description add-field-description --onProperty dlvryNbr --title "Delivery  Number" --text "The Delivery order number";
description add-field-description --onProperty dlvryNbr --title "Numéro de la Livraison" --text "Numéro de la livraison" --locale fr;
constraint NotNull --onProperty dlvryNbr;

field string --named dlvrySlipNbr;
description add-field-description --onProperty dlvrySlipNbr --title "Delivery Slip Number" --text "The delivery slip number (generaly available on the delivery slip)";
description add-field-description --onProperty dlvrySlipNbr --title "Numéro Bordereau de Livraison" --text "Numéro de bordereau de livraison (mentionné géneralement sur le bordereau de livraison)" --locale fr;
constraint NotNull --onProperty dlvrySlipNbr;
description add-notNull-message --onProperty dlvrySlipNbr --title "The delivery slip number is required" --text "The delivery slip number is required";
description add-notNull-message --onProperty dlvrySlipNbr --title "Le numéro de bordereau de livraison est réquis" --text "Le numéro de bordereau de livraison est réquis" --locale fr;

field temporal --type TIMESTAMP --named dtOnDlvrySlip; 
description add-field-description --onProperty dtOnDlvrySlip --title "Date on Delivery Slip" --text "Date as mentioned on delivery slip";
description add-field-description --onProperty dtOnDlvrySlip --title "Date sur Bordereau" --text "Date de livraison mentionée sur le bordereau de livraison de l approvisionement" --locale fr;
format add-date-pattern --onProperty dtOnDlvrySlip --pattern "dd-MM-yyyy"; 

field temporal --type TIMESTAMP --named orderDt; 
description add-field-description --onProperty orderDt --title "Order Date" --text "Order date.";
description add-field-description --onProperty orderDt --title "Date de Commande" --text "Date de commande." --locale fr;

field temporal --type TIMESTAMP --named dlvryDt; 
description add-field-description --onProperty dlvryDt --title "Delivery Date" --text "Date on which the products where effectively delivered products.";
description add-field-description --onProperty dlvryDt --title "Date de Livraison" --text "Date à laquelle a été livrée les produits qui entrent en stock.." --locale fr;
constraint NotNull --onProperty dlvryDt;

field string --named supplier;
description add-field-description --onProperty supplier --title "Supplier" --text "The supplier mentioned on the delivery slip while products are being delivered.";
description add-field-description --onProperty supplier --title "Fournisseur" --text "Le fournisseur mentionné sur le bordereau de livraison des produits qui entrent en stock." --locale fr;

field string --named dlvryCur;
description add-field-description --onProperty dlvryCur --title "Currency of the Delivery" --text "Currency of the Delivery";
description add-field-description --onProperty dlvryCur --title "Dévise de cette livraison" --text "Dévise de cette livraison" --locale fr;

field number --named grossPPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty grossPPPreTax --title "Gross Purchase Amount Pre Tax" --text "Gross Purchase Amount Pre Tax";
description add-field-description --onProperty grossPPPreTax --title "Montant Brut Achat Hors Taxes" --text "Montant Brut Achat Hors Taxes" --locale fr;

field number --named rebate --type java.math.BigDecimal;
description add-field-description --onProperty rebate --title "Rebate Amount" --text "Rebate or Discount amount.";
description add-field-description --onProperty rebate --title "Montant Remise" --text "Montant de la remise" --locale fr;

field number --named netPPPreTax --type java.math.BigDecimal;
description add-field-description --onProperty netPPPreTax --title "Net Purchase Amount Pre Tax" --text "Net Purchase Amount Pre Tax";
description add-field-description --onProperty netPPPreTax --title "Montant Net Achat Hors Taxes" --text "Montant Net Achat Hors Taxes" --locale fr;

field number --named vatAmount --type java.math.BigDecimal;
description add-field-description --onProperty vatAmount --title "VAT Amount" --text "Tax amount for this purchase order.";
description add-field-description --onProperty vatAmount --title "Montant TVA" --text "Montant de la taxe de l approvisionement." --locale fr;

field number --named netPPTaxIncl --type java.math.BigDecimal;
description add-field-description --onProperty netPPTaxIncl --title "Purchase Amount Tax Included" --text "Purchase Amount Tax Included";
description add-field-description --onProperty netPPTaxIncl --title "Montant Achat TTC" --text "Le montant achat toute taxe comprise." --locale fr;

field number --named pymtDscntPct --type java.math.BigDecimal;
description add-field-description --onProperty pymtDscntPct --title "VAT Rate" --text "The VAT rate in percent.";
description add-field-description --onProperty pymtDscntPct --title "Taux TVA" --text "The taux de TVA en pourcent." --locale fr;

field number --named pymtDscntAmt --type java.math.BigDecimal;
description add-field-description --onProperty pymtDscntAmt --title "Payment Discount Amount" --text "Discount amount for promt payment.";
description add-field-description --onProperty pymtDscntAmt --title "Montant Excompte" --text "Montant de la remise pour payement anticipe" --locale fr;
format add-number-type --onProperty pymtDscntAmt --type CURRENCY;

field number --named netPurchAmt --type java.math.BigDecimal;
description add-field-description --onProperty netPurchAmt --title "Net Purchase Amount" --text "Net Purchase Amount";
description add-field-description --onProperty netPurchAmt --title "Montant Net Achat" --text "Montant Net Achat" --locale fr;
format add-number-type --onProperty netPurchAmt --type CURRENCY;

field number --named rdngDscntAmt --type java.math.BigDecimal;
description add-field-description --onProperty rdngDscntAmt --title "Rounding Discount Amount" --text "Rounding Discount Amount";
description add-field-description --onProperty rdngDscntAmt --title "Montant Remise pour Arrondi" --text "Montant Remise pour Arrondi" --locale fr;
format add-number-type --onProperty rdngDscntAmt --type CURRENCY;

field number --named netAmtToPay --type java.math.BigDecimal;
description add-field-description --onProperty netAmtToPay --title "Net Amount to Pay" --text "Teh net amount to pay.";
description add-field-description --onProperty netAmtToPay --title "Montant net a payer" --text "Montant de la remise de l approvisionement." --locale fr;
format add-number-type --onProperty netAmtToPay --type CURRENCY;

field custom --named dlvryStatus --type ~.jpa.ProcmtDlvryStatus;
description add-field-description --onProperty dlvryStatus --title "Delivery  Status" --text "The delivery processing state.";
description add-field-description --onProperty dlvryStatus --title "Etat  Livraison" --text "L etat de traitement de cette Livraison." --locale fr;
enum enumerated-field --onProperty dlvryStatus ;
constraint NotNull --onProperty dlvryStatus;

field string --named rcvngOrgUnit;
description add-field-description --onProperty rcvngOrgUnit --title "Organization Unit" --text "Organization Unit";
description add-field-description --onProperty rcvngOrgUnit --title "Departement Receptrice" --text "Departement Receptrice" --locale fr;

field string --named creatingUsr;
description add-field-description --onProperty creatingUsr --title "Creating User" --text "The user creating this delivery order item";
description add-field-description --onProperty creatingUsr --title "Agent Créateur" --text "Utilisateur ayant crée cet ligne de livraison" --locale fr;
constraint NotNull --onProperty creatingUsr;

field temporal --type TIMESTAMP --named creationDt; 
description add-field-description --onProperty creationDt --title "Creation Date" --text "The creation date of this stock movement.";
description add-field-description --onProperty creationDt --title "Date de Création" --text "La date de création de cette movement de stock." --locale fr;
constraint NotNull --onProperty creationDt;

cd ~~ ;

@/* ========================== Procurement =======================*/;
@/* Entite Ligne Facture Fournisseur */;
entity --named SupplierInvoiceItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Supplier Invoice Item" --text "An supplier invoice item.";
description add-class-description  --locale fr --title "Ligne Facture Fournisseur" --text "Une ligne facture fournisseur.";


field string --named internalPic;
description add-field-description --onProperty internalPic --title "Local PIC" --text "The Local pic of referenced product.";
description add-field-description --onProperty internalPic --title "Cip MAison" --text "Le cip Maison du produit reference." --locale fr;
display add-toString-field --field internalPic;
display add-list-field --field internalPic;

field manyToOne --named article --fieldType ~.jpa.Article;
description add-field-description --onProperty article --title "Article" --text "The  Article";
description add-field-description --onProperty article --title "Article" --text "Article ." --locale fr;
association set-selection-mode --onProperty article --selectionMode COMBOBOX;
association set-type --onProperty article --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field article.articleName;
display add-list-field --field article.articleName;

field number --named deliveryQty --type java.math.BigDecimal;
description add-field-description --onProperty deliveryQty --title "Quantity delivery" --text "The quantity delivered in this line.";
description add-field-description --onProperty deliveryQty --title "Quantité Livrée" --text "La quantité livrée dans cette ligne." --locale fr;
@/* Default=0 */;
display add-toString-field --field deliveryQty;
display add-list-field --field deliveryQty;

field number --named purchasePricePU --type java.math.BigDecimal;
description add-field-description --onProperty purchasePricePU --title "Purchase Price per Unit" --text "The order price per unit for product of this line.";
description add-field-description --onProperty purchasePricePU --title "Prix d achat Unitaire" --text "Prix unitaire du produit de la ligne de facture" --locale fr;
format add-number-type --onProperty purchasePricePU --type CURRENCY ;
display add-list-field --field purchasePricePU;

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit for product of this line.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix unitaire du produit de la ligne de facture" --locale fr;
format add-number-type --onProperty salesPricePU --type CURRENCY;
display add-list-field --field salesPricePU;

field number --named amountReturn --type java.math.BigDecimal;
description add-field-description --onProperty amountReturn --title "Amount Return" --text "Amount Returned Product";
description add-field-description --onProperty amountReturn --title "Prix de Vente Unitaire" --text "Montant Total Des Retour" --locale fr;
format add-number-type --onProperty amountReturn --type CURRENCY ;
display add-list-field --field amountReturn;

field number --named totalPurchasePrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPurchasePrice --title "Total purchase Price" --text "The total sales price for product of this line.";
description add-field-description --onProperty totalPurchasePrice --title "Prix d achat Total" --text "Prix total du produit de la ligne de facture" --locale fr;
format add-number-type --onProperty totalPurchasePrice --type CURRENCY ;
display add-list-field --field totalPurchasePrice;


cd ~~;


@/* Entité Facture */;
entity --named SupplierInvoice --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Supplier Invoice" --text "An invoice.";
description add-class-description  --locale fr --title "Facture Fournisseur" --text "Une facture.";
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole MANAGER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole STOCKS;


field custom --named invoiceType --type ~.jpa.InvoiceType;
description add-field-description --onProperty invoiceType --title "Invoice Type" --text "The type of this invoice.";
description add-field-description --onProperty invoiceType --title "Type de Facture" --text "Le type de cette facture." --locale fr;
enum enumerated-field --onProperty invoiceType ;
display add-list-field --field invoiceType;

field string --named invoiceNumber;
description add-field-description --onProperty invoiceNumber --title "Invoice Number" --text "The number of the invoice.";
description add-field-description --onProperty invoiceNumber --title "Numéro Facture" --text "Le numéro de cette facture." --locale fr;
display add-toString-field --field invoiceNumber;
display add-list-field --field invoiceNumber;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern= dd-MM-yyyy HH:mm*/;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this invoicd.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette facture." --locale fr;
display add-list-field --field creationDate;
format add-date-pattern --onProperty creationDate --pattern "dd-MM-yyyy HH:mm"; 

field manyToOne --named supplier --fieldType ~.jpa.Supplier;
description add-field-description --onProperty supplier --title "Supplier" --text "The Supplier referenced by this invoice";
description add-field-description --onProperty supplier --title "Fournisseur" --text "Le Fournisseur mentionné sur la facture" --locale fr;
association set-selection-mode --onProperty supplier --selectionMode FORWARD;
association set-type --onProperty supplier --type AGGREGATION --targetEntity ~.jpa.Supplier;
display add-list-field --field supplier.name;
constraint NotNull --onProperty  supplier ;
description add-notNull-message --onProperty supplier --title "The supplier must be selected" --text "The customer must be selected";
description add-notNull-message --onProperty supplier --title "Le Founisseur doit être sélectionné" --text "Le client doit être sélectionné" --locale fr;

field manyToOne --named creatingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty creatingUser --title "Sales Agent" --text "The user creating this invoice";
description add-field-description --onProperty creatingUser --title "Vendeur" --text "Éditeur de cette facture" --locale fr;
constraint NotNull --onProperty creatingUser;
description add-notNull-message --onProperty creatingUser --title "The creating user must be selected" --text "The creating user must be selected";
description add-notNull-message --onProperty creatingUser --title "Utilisateur créant doit être sélectionné" --text "Utilisateur créant doit être sélectionné" --locale fr;
association set-selection-mode --onProperty creatingUser --selectionMode COMBOBOX;
association set-type --onProperty creatingUser --type AGGREGATION --targetEntity ~.jpa.Login;
display add-list-field --field creatingUser.fullName;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "The agency where sale has been made.";
description add-field-description --onProperty agency --title "Agence" --text "Agence dans la quelle la vente a été éffectuée." --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "The agency of this sale must be selected" --text "The agency of this sale must be selected";
description add-notNull-message --onProperty agency --title "Le site de cette vente doit être sélectionné" --text "Le site de cette vente doit être sélectionné" --locale fr;

field manyToOne --named delivery --fieldType ~.jpa.Delivery;
description add-field-description --onProperty delivery --title "Delivery" --text "The Delivery  generating of this invoice.";
description add-field-description --onProperty delivery --title "Livraison Fournisseur" --text "La livraison  originaire de la facture" --locale fr;
association set-selection-mode --onProperty delivery --selectionMode FORWARD;
association set-type --onProperty delivery --type AGGREGATION --targetEntity ~.jpa.Delivery;
display add-list-field --field delivery.deliveryNumber;

field boolean --named settled --primitive false; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the invoice is settled.";
description add-field-description --onProperty settled --title "Soldée" --text "Indique si la facture est soldée ou pas." --locale fr;
@/* default=false */;
display add-list-field --field settled;

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this sales order.";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette commande client." --locale fr;
@/* Default=0, montant_ht */;
display add-list-field --field amountBeforeTax;

field number --named taxAmount --type java.math.BigDecimal;
description add-field-description --onProperty taxAmount --title "Amount VAT" --text "Total amount VAT for this sales order.";
description add-field-description --onProperty taxAmount --title "Montant TVA" --text "Montant total TVA pour cette commande client." --locale fr;
format add-number-type --onProperty taxAmount --type CURRENCY ;
display add-list-field --field taxAmount;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this sales order. The sum of all discounts.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Remise totale de la commande, c est-à-dire la somme des remise totales des  lignes de commande." --locale fr;
format add-number-type --onProperty amountDiscount --type CURRENCY;
display add-list-field --field amountDiscount;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this sales order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette commande client." --locale fr;
format add-number-type --onProperty amountAfterTax --type CURRENCY;
display add-list-field --field amountAfterTax;

field number --named netToPay --type java.math.BigDecimal;
description add-field-description --onProperty netToPay --title "Net a Payer" --text "The net amount to pay.";
description add-field-description --onProperty netToPay --title "Net a Payer" --text "Le montant net à payer." --locale fr;
format add-number-type --onProperty netToPay --type CURRENCY ;
display add-list-field --field netToPay;

field number --named advancePayment --type java.math.BigDecimal;
description add-field-description --onProperty advancePayment --title "Advance Payment" --text "The advance payment.";
description add-field-description --onProperty advancePayment --title "Net a Payer" --text "L avance sur paiement." --locale fr;
format add-number-type --onProperty advancePayment --type CURRENCY;
display add-list-field --field advancePayment;

field number --named totalRestToPay --type java.math.BigDecimal;
description add-field-description --onProperty totalRestToPay --title "Ret to Pay" --text "The rest to pay.";
description add-field-description --onProperty totalRestToPay --title "Reste a Payer" --text "Le reste a payer." --locale fr;
format add-number-type --onProperty totalRestToPay --type CURRENCY;
display add-list-field --field totalRestToPay;

field oneToMany --named invoiceItems --fieldType ~.jpa.SupplierInvoiceItem --inverseFieldName invoice;
description add-field-description --onProperty invoiceItems --title "Invoice Items" --text "The invoice items";
description add-field-description --onProperty invoiceItems --title "Lignes Facture" --text "Les ligne facture" --locale fr;
association set-type --onProperty invoiceItems --type COMPOSITION --targetEntity ~.jpa.SupplierInvoiceItem;
association set-selection-mode --onProperty invoiceItems --selectionMode TABLE;

cd ../SupplierInvoiceItem.java;
description add-field-description --onProperty invoice --title "Invoice" --text "The invoice containing this item";
description add-field-description --onProperty invoice --title "Facture" --text "Facture contenant cette ligne" --locale fr;
association set-type --onProperty invoice --type COMPOSITION --targetEntity ~.jpa.SupplierInvoice;



repogen setup;

repogen new-repository --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys;

cd ~~;

mvn clean install -DskipTests;













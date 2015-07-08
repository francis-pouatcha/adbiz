
cd ~~;

@/* Entity SalesMargin */;
entity --named SlsSalesMargin --package ~.jpa;
description add-class-description --title "Sales Margin" --text "The sales margin on this product.";
description add-class-description  --locale fr --title "Taux de Marge" --text "Le taux de marge du produit.";

field string --named name;
description add-field-description --onProperty name --title "Margin Name" --text "The code of this margin";
description add-field-description --onProperty name --title "Nom Marge" --text "Code de la marge" --locale fr;

field number --named ratePct --type java.math.BigDecimal;
description add-field-description --onProperty ratePct --title "Marging Rate in Percent" --text "The rate of this margin in percent.";
description add-field-description --onProperty ratePct --title "Taux de Marge en Pourcent" --text "Taux de la marge en pourcent." --locale fr;

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this article is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si le produit est active ou non" --locale fr;

cd ~~ ;

@/* Enum type Sales Order Status State */;
java new-enum-type --named SlsSOStatus --package ~.jpa;
enum add-enum-class-description --title "Sales Order Status" --text "Status of the sales order";
enum add-enum-class-description --title "Status COmmande Client" --text "Status da la commande client" --locale fr;
java new-enum-const SUSPENDED ;
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspended" --text "Suspended";
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspendu" --text "Suspendu" --locale fr ;
java new-enum-const ONGOING ;
enum add-enum-constant-description --onConstant ONGOING --title "Ongoing" --text "Ongoing";
enum add-enum-constant-description --onConstant ONGOING --title "En Cours" --text "En cours" --locale fr ;
java new-enum-const RESUMED;
enum add-enum-constant-description --onConstant RESTORED --title "Resumed" --text "Resumed";
enum add-enum-constant-description --onConstant RESTORED --title "Repris" --text "Repris" --locale fr ;
java new-enum-const CLOSED;
enum add-enum-constant-description --onConstant CLOSED --title "Closed" --text "Closed";
enum add-enum-constant-description --onConstant CLOSED --title "Cloturé" --text "Cloturé" --locale fr ;

cd ~~ ;

@/* Commande Client */;

 @/* SalesOrderType */;
java new-enum-type --named SalesOrderType --package ~.jpa ;
enum add-enum-class-description --title "Sales Order Type" --text "The type of this sales order.";
enum add-enum-class-description  --locale fr --title "Type de Commande Client" --text "Le type d une commande client.";
java new-enum-const CASH_SALE;
enum add-enum-constant-description --onConstant CASH_SALE --title "Cash Sale" --text "Cash Sale";
enum add-enum-constant-description --locale fr --onConstant CASH_SALE --title "Vente Au comptant" --text "Vente en espece";
java new-enum-const PARTIAL_SALE;
enum add-enum-constant-description --onConstant PARTIAL_SALE --title "Partial Sale" --text "Credit sale";
enum add-enum-constant-description --locale fr --onConstant PARTIAL_SALE --title "Vente Partiel" --text "Vente à crédit";
java new-enum-const PROFORMA_SALE;
enum add-enum-constant-description --onConstant PROFORMA_SALE --title "Pro Forma Sale" --text "Pro forma sale";
enum add-enum-constant-description --locale fr --onConstant PROFORMA_SALE --title "Vente Pro Forma" --text "Vente pro forma";

cd ~~;


@/* Sales Order Item */;
entity --named SalesOrderItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Order Item" --text "The sales order item.";
description add-class-description  --locale fr --title "Ligne Commande Client" --text "La ligne de command client.";
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole MANAGER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole SALES;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole CASHIER;

field number --named orderedQty --type java.math.BigDecimal;
description add-field-description --onProperty orderedQty --title "Quantity Ordered" --text "The quantity ordered in this line.";
description add-field-description --onProperty orderedQty --title "Quantité Commandés" --text "La quantité commandée dans cette ligne." --locale fr;
@/* Default=0 */;
display add-list-field --field orderedQty;

field number --named returnedQty --type java.math.BigDecimal;
description add-field-description --onProperty returnedQty --title "Quantity Returned" --text "The quantity returned in this line.";
description add-field-description --onProperty returnedQty --title "Quantité Retournée" --text "La quantité retournée dans cette ligne." --locale fr;
@/* Default=0 */;
display add-list-field --field returnedQty;

field number --named deliveredQty --type java.math.BigDecimal;
description add-field-description --onProperty deliveredQty --title "Quantity Returned" --text "The quantity returned in this line.";
description add-field-description --onProperty deliveredQty --title "Quantité Retournée" --text "La quantité retournée dans cette ligne." --locale fr;
@/* Default=0 */;
display add-list-field --field deliveredQty;

field temporal --type TIMESTAMP --named recordDate; 
@/* Pattern=d-MM-yyy HH:MM  */;
description add-field-description --onProperty recordDate --title "Record Date" --text "Creation date for this line.";
description add-field-description --onProperty recordDate --title "Date de Saisie" --text "Date de saisie de cette ligne." --locale fr;
format add-date-pattern --onProperty recordDate --pattern "dd-MM-yyyy HH:mm"; 

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix de vente unitaire." --locale fr;
format add-number-type --onProperty salesPricePU --type CURRENCY;
@/*  Default=0 */; 
display add-list-field --field salesPricePU;

field number --named totalSalePrice --type java.math.BigDecimal;
description add-field-description --onProperty totalSalePrice --title "Total Sale Price" --text "Total Sale Price.";
description add-field-description --onProperty totalSalePrice --title "Prix de vente Total" --text "Prix de vente Total." --locale fr;
format add-number-type --onProperty totalSalePrice --type CURRENCY;
@/* Default=0., Formule=(remise * qté_commande) */;
display add-list-field --field totalSalePrice;

field string --named internalPic; 
description add-field-description --onProperty internalPic --title "Local PIC" --text "The internal product identification code used to identify lots during sales.";
description add-field-description --onProperty internalPic --title "CIP Maison" --text "Le code identifiant produit maison, utilisé pour identifier les lots de produits lors de la vente." --locale fr;
constraint Size --onProperty internalPic --min 7;
description add-size-message --onProperty internalPic --title "The internal product identification code must have more than 7 characters" --text "The internal product identification code must have more than 7 characters";
description add-size-message --onProperty internalPic --title "Le code pour identification de produit interne doit avoir plus de 7 caractères" --text "Le code pour identification de produit interne doit avoir plus de 7 caractères" --locale fr;
display add-list-field --field internalPic;

field manyToOne --named article --fieldType ~.jpa.Article;
description add-field-description --onProperty article --title "Article" --text "The article of this sales order item";
description add-field-description --onProperty article --title "Article" --text "Le produit de cette ligne de commande client" --locale fr;
association set-selection-mode --onProperty article --selectionMode FORWARD;
association set-type --onProperty article --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field article.articleName;
display add-list-field --field article.articleName;
constraint NotNull --onProperty article;
description add-notNull-message --onProperty article --title "The article of this sales order item must be selected" --text "The article of this sales order item must be selected";
description add-notNull-message --onProperty article --title "Le produit de cette ligne de commande client doit être sélectionné" --text "Le produit de cette ligne de commande client doit être sélectionné" --locale fr;

field manyToOne --named vat --fieldType ~.jpa.VAT;
description add-field-description --onProperty vat --title "VAT" --text "The value added tax";
description add-field-description --onProperty vat --title "TVA" --text "La taxe sur la valeur ajoute" --locale fr;
association set-selection-mode --onProperty vat --selectionMode  COMBOBOX;
association set-type --onProperty vat --type AGGREGATION --targetEntity ~.jpa.VAT;
display add-list-field --field vat.rate;

cd ~~;



@/* Sales Order */;
entity --named SalesOrder --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Order" --text "A sales order.";
description add-class-description  --locale fr --title "Commande Client" --text "Une commande client.";
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole MANAGER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole CASHIER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole SALES;

field manyToOne --named cashDrawer --fieldType ~.jpa.CashDrawer;
description add-field-description --onProperty cashDrawer --title "Cash Drawer" --text "The cash drawer in use.";
description add-field-description --onProperty cashDrawer --title "Caisse" --text "La caisse utilisé." --locale fr;
association set-selection-mode --onProperty cashDrawer --selectionMode COMBOBOX;
association set-type --onProperty cashDrawer --type AGGREGATION --targetEntity ~.jpa.CashDrawer;
display add-list-field --field cashDrawer.cashDrawerNumber;

field string --named soNumber;
description add-field-description --onProperty soNumber --title "Sales Order Number" --text "The sales order number.";
description add-field-description --onProperty soNumber --title "Numéro de Commande Client" --text "Le numéro de la commande client." --locale fr;
display add-toString-field --field soNumber;
display add-list-field --field soNumber;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this order.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette commande." --locale fr;
format add-date-pattern --onProperty creationDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named cancelationDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty cancelationDate --title "Cancelation Date" --text "The cancelation date of this order.";
description add-field-description --onProperty cancelationDate --title "Date d Annulation" --text "La date d annulation de cette commande." --locale fr;
format add-date-pattern --onProperty cancelationDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named restorationDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty restorationDate --title "Restoration Date" --text "The restoration date of this order.";
description add-field-description --onProperty restorationDate --title "Date de Restauration" --text "La date de restauration de cette commande." --locale fr;
format add-date-pattern --onProperty restorationDate --pattern "dd-MM-yyyy HH:mm"; 

field manyToOne --named customer --fieldType ~.jpa.Customer;
description add-field-description --onProperty customer --title "Customer" --text "The client ordering";
description add-field-description --onProperty customer --title "Client" --text "Le client qui passe la commande" --locale fr;
association set-selection-mode --onProperty customer --selectionMode FORWARD;
association set-type --onProperty customer --type AGGREGATION --targetEntity ~.jpa.Customer;
display add-list-field --field customer.fullName;
constraint NotNull --onProperty customer;
description add-notNull-message --onProperty customer --title "The client ordering must be selected" --text "The client ordering must be selected";
description add-notNull-message --onProperty customer --title "Le client doit être sélectionné" --text "Le client doit être sélectionné" --locale fr;

field manyToOne --named insurance --fieldType ~.jpa.Insurrance;
description add-field-description --onProperty insurance --title "Insurance" --text "The Insurance in charge";
description add-field-description --onProperty insurance --title "Assurance" --text "Assurance prenant la facture en charge" --locale fr;
association set-selection-mode --onProperty insurance --selectionMode FORWARD;
association set-type --onProperty insurance --type AGGREGATION --targetEntity ~.jpa.Insurrance;
display add-list-field --field insurance.customer.fullName;
display add-list-field --field insurance.insurer.fullName;

field manyToOne --named vat --fieldType ~.jpa.VAT;
description add-field-description --onProperty vat --title "VAT" --text "The value added tax";
description add-field-description --onProperty vat --title "TVA" --text "La taxe sur valeur ajouté" --locale fr;
association set-selection-mode --onProperty vat --selectionMode COMBOBOX;
association set-type --onProperty vat --type AGGREGATION --targetEntity ~.jpa.VAT;
display add-list-field --field vat.rate;

field manyToOne --named salesAgent --fieldType ~.jpa.Login;
description add-field-description --onProperty salesAgent --title "Sales Agent" --text "The user making this sale";
description add-field-description --onProperty salesAgent --title "Vendeur" --text "Utilisateur éffectuant cette vente" --locale fr;
association set-selection-mode --onProperty salesAgent --selectionMode FORWARD;
association set-type --onProperty salesAgent --type AGGREGATION --targetEntity ~.jpa.Login;
display add-list-field --field salesAgent.fullName;
constraint NotNull --onProperty salesAgent;
description add-notNull-message --onProperty salesAgent --title "The user making this sale must be selected" --text "The user making this sale must be selected";
description add-notNull-message --onProperty salesAgent --title "Le vendeur doit être sélectionné" --text "Le vendeur doit être sélectionné" --locale fr;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "The Agency where sale has been made";
description add-field-description --onProperty agency --title "Agence" --text "Agence dans la quelle la vente a été éffectuée" --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency ;
description add-notNull-message --onProperty agency --title "The agency where sale has been made must be selected" --text "The agency where sale has been made must be selected";
description add-notNull-message --onProperty agency --title "Le site dans la quelle la vente a été éffectuée doit être sélectionné" --text "Le site dans la quelle la vente a été éffectuée doit être sélectionné" --locale fr;

field custom --named salesOrderStatus --type ~.jpa.DocumentProcessingState.java;
description add-field-description --onProperty salesOrderStatus --title "Status" --text "The status of this sales order.";
description add-field-description --onProperty salesOrderStatus --title "Status" --text "État de cette commande client." --locale fr;
enum enumerated-field --onProperty salesOrderStatus ;
display add-list-field --field salesOrderStatus;

field boolean --named cashed --primitive false;
description add-field-description --onProperty cashed --title "Cashed" --text "Indicates whether the order has been cashed or not.";
description add-field-description --onProperty cashed --title "Encaissé" --text "Indique si la commande a été encaissée ou non." --locale fr;
@/* default=false */;
display add-list-field --field cashed;

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this sales order.";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette commande client." --locale fr;
format add-number-type --onProperty amountBeforeTax --type CURRENCY ;
@/* Default=0, montant_ht */;
display add-list-field --field amountBeforeTax;

field number --named amountVAT --type java.math.BigDecimal;
description add-field-description --onProperty amountVAT --title "Amount VAT" --text "Total amount VAT for this sales order.";
description add-field-description --onProperty amountVAT --title "Montant TVA" --text "Montant total TVA pour cette commande client." --locale fr;
format add-number-type --onProperty amountVAT --type CURRENCY ;
@/* Default=0, montant_tva */;
display add-list-field --field amountVAT;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this sales order. The sum of all discounts.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Remise totale de la commande, c est-à-dire la somme des remise totales des  lignes de commande." --locale fr;
format add-number-type --onProperty amountDiscount --type CURRENCY ;
display add-list-field --field amountDiscount;

field number --named totalReturnAmount --type java.math.BigDecimal;
description add-field-description --onProperty totalReturnAmount --title "Total Return Amount" --text "Total Return Amount.";
description add-field-description --onProperty totalReturnAmount --title "Montant total retour" --text "Montant total retour." --locale fr;
format add-number-type --onProperty totalReturnAmount --type CURRENCY ;
display add-list-field --field totalReturnAmount;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this sales order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette commande client." --locale fr;
format add-number-type --onProperty amountAfterTax --type CURRENCY ;
display add-list-field --field amountAfterTax;

field custom --named salesOrderType --type ~.jpa.SalesOrderType;
description add-field-description --onProperty salesOrderType --title "Type" --text "The type of this sales order.";
description add-field-description --onProperty salesOrderType --title "Type" --text "Le type de cette commande client." --locale fr;
enum enumerated-field --onProperty salesOrderType;
display add-list-field --field salesOrderType;

field oneToMany --named salesOrderItems --fieldType ~.jpa.SalesOrderItem --inverseFieldName salesOrder;
description add-field-description --onProperty salesOrderItems --title "Sales Order Items" --text "Sales order items owned by the sales order";
description add-field-description --onProperty salesOrderItems --title "Lignes Inventaire" --text "Les lignes sous controlle de cette commande client" --locale fr;
association set-type --onProperty salesOrderItems --type COMPOSITION --targetEntity ~.jpa.SalesOrderItem;
association set-selection-mode --onProperty salesOrderItems --selectionMode TABLE;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole MANAGER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole CASHIER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole SALES;

cd ../SalesOrderItem.java;
description add-field-description --onProperty salesOrder --title "Sales Order" --text "The sales order containing this item";
description add-field-description --onProperty salesOrder --title "Commande client" --text "Commande client contenant cette ligne" --locale fr;
association set-type --onProperty salesOrder --type COMPOSITION --targetEntity ~.jpa.SalesOrder;


cd ~~;


@/* Invoice */;

@/* InvoiceType */;
java new-enum-type --named InvoiceType --package ~.jpa ;
enum add-enum-class-description --title "Invoice Type" --text "The type of this invoice.";
enum add-enum-class-description  --locale fr --title "Type de Facture" --text "Le type d une facture.";
java new-enum-const CASHDRAWER;
enum add-enum-constant-description --onConstant CASHDRAWER --title "Cashdrawer invoice" --text "Cashdrawer invoice";
enum add-enum-constant-description --locale fr --onConstant CASHDRAWER --title "Facture de caisse" --text "Facture de caisse";
java new-enum-const PROFORMA;
enum add-enum-constant-description --onConstant PROFORMA --title "Proforma Invoice" --text "Proforma Invoice";
enum add-enum-constant-description --locale fr --onConstant PROFORMA --title "Facture Proforma" --text "Facture proforma";
java new-enum-const VOUCHER;
enum add-enum-constant-description --onConstant VOUCHER --title "Vouche invoice" --text "Voucher invoice";
enum add-enum-constant-description --locale fr --onConstant VOUCHER --title "Facture d avoir" --text "Facture d avoir";

cd ~~;


@/* Entite Ligne Facture */;
entity --named CustomerInvoiceItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Invoice Item" --text "An invoice item.";
description add-class-description  --locale fr --title "Ligne Facture" --text "Une ligne facture.";

field string --named internalPic;
description add-field-description --onProperty internalPic --title "Local PIC" --text "The Local pic of referenced product.";
description add-field-description --onProperty internalPic --title "Cip MAison" --text "Le cip Maison du produit reference." --locale fr;
display add-toString-field --field internalPic;
display add-list-field --field internalPic;

field manyToOne --named article --fieldType ~.jpa.Article;
description add-field-description --onProperty article --title "Article" --text "The  Article";
description add-field-description --onProperty article --title "Article" --text "Article ." --locale fr;
association set-selection-mode --onProperty article --selectionMode FORWARD;
association set-type --onProperty article --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field article.articleName;
display add-list-field --field article.articleName;

field number --named purchasedQty --type java.math.BigDecimal;
description add-field-description --onProperty purchasedQty --title "Quantity Purchased" --text "The quantity purchased in this line.";
description add-field-description --onProperty purchasedQty --title "Quantité Achetée" --text "La quantité achetée dans cette ligne." --locale fr;
@/* Default=0 */;
display add-toString-field --field purchasedQty;
display add-list-field --field purchasedQty;

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit for product of this line.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix unitaire du produit de la ligne de facture" --locale fr;
format add-number-type --onProperty salesPricePU --type CURRENCY ;
display add-list-field --field salesPricePU;

field number --named totalSalesPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalSalesPrice --title "Total Sales Price" --text "The total sales price for product of this line.";
description add-field-description --onProperty totalSalesPrice --title "Prix de Vente Total" --text "Prix total du produit de la ligne de facture" --locale fr;
format add-number-type --onProperty totalSalesPrice --type CURRENCY ;
display add-list-field --field totalSalesPrice;


@/* Entité Facture */;
entity --named CustomerInvoice --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Customer Invoice" --text "An invoice.";
description add-class-description  --locale fr --title "Facture Client" --text "Une facture.";

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

field manyToOne --named customer --fieldType ~.jpa.Customer;
description add-field-description --onProperty customer --title "Customer" --text "The client referenced by this invoice";
description add-field-description --onProperty customer --title "Client" --text "Le client mentionné sur la facture" --locale fr;
association set-selection-mode --onProperty customer --selectionMode FORWARD;
association set-type --onProperty customer --type AGGREGATION --targetEntity ~.jpa.Customer;
display add-list-field --field customer.fullName;
constraint NotNull --onProperty customer ;
description add-notNull-message --onProperty customer --title "The customer must be selected" --text "The customer must be selected";
description add-notNull-message --onProperty customer --title "Le client doit être sélectionné" --text "Le client doit être sélectionné" --locale fr;

field manyToOne --named insurance --fieldType ~.jpa.Insurrance;
description add-field-description --onProperty insurance --title "Insurance" --text "The Insurance in charge";
description add-field-description --onProperty insurance --title "Assurance" --text "Assurance prenant la facture en charge" --locale fr;
association set-selection-mode --onProperty insurance --selectionMode FORWARD;
association set-type --onProperty insurance --type AGGREGATION --targetEntity ~.jpa.Insurrance;
display add-list-field --field insurance.customer.fullName;
display add-list-field --field insurance.insurer.fullName;

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

field manyToOne --named salesOrder --fieldType ~.jpa.SalesOrder;
description add-field-description --onProperty salesOrder --title "Sales Order" --text "The sales order generating of this invoice.";
description add-field-description --onProperty salesOrder --title "Commande Client" --text "Commande client originaire de la facture" --locale fr;
association set-selection-mode --onProperty salesOrder --selectionMode  COMBOBOX;
association set-type --onProperty salesOrder --type AGGREGATION --targetEntity ~.jpa.SalesOrder;
display add-list-field --field salesOrder.soNumber;

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

field number --named customerRestTopay --type java.math.BigDecimal;
description add-field-description --onProperty customerRestTopay --title "Customer Rest To pay" --text "Customer Rest To pay.";
description add-field-description --onProperty customerRestTopay --title "Rest A payer client" --text "Rest A payer client." --locale fr;
format add-number-type --onProperty customerRestTopay --type CURRENCY;
display add-list-field --field customerRestTopay;

field number --named insurranceRestTopay --type java.math.BigDecimal;
description add-field-description --onProperty insurranceRestTopay --title "Insurrance Rest To pay" --text "Insurrance Rest To pay.";
description add-field-description --onProperty insurranceRestTopay --title "Reste A payer client" --text "Reste A payer client." --locale fr;
format add-number-type --onProperty insurranceRestTopay --type CURRENCY;
display add-list-field --field insurranceRestTopay;

field boolean --named cashed --primitive false; 
description add-field-description --onProperty cashed --title "Cashed" --text "Sates if the invoice is cashed.";
description add-field-description --onProperty cashed --title "encaisseé" --text "Indique si la facture est encaissée ou pas." --locale fr;
@/* default=false */;
display add-list-field --field cashed;

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

field oneToMany --named invoiceItems --fieldType ~.jpa.CustomerInvoiceItem --inverseFieldName invoice;
description add-field-description --onProperty invoiceItems --title "Invoice Items" --text "The invoice items";
description add-field-description --onProperty invoiceItems --title "Lignes Facture" --text "Les ligne facture" --locale fr;
association set-type --onProperty invoiceItems --type COMPOSITION --targetEntity ~.jpa.CustomerInvoiceItem;
association set-selection-mode --onProperty invoiceItems --selectionMode TABLE;

cd ../CustomerInvoiceItem.java;
description add-field-description --onProperty invoice --title "Invoice" --text "The invoice containing this item";
description add-field-description --onProperty invoice --title "Facture" --text "Facture contenant cette ligne" --locale fr;
association set-type --onProperty invoice --type COMPOSITION --targetEntity ~.jpa.CustomerInvoice;

cd ~~ ;

@/* ================================== */;
@/* Prescriptions */;

@/* Entité Hospital */;
entity --named Hospital --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Hospital" --text "Hospital";
description add-class-description  --locale fr --title "Hopital" --text "Hopital";
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole MANAGER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole CASHIER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole SALES;

field string --named name;
description add-field-description --onProperty name --title "Hospital Name" --text "The name of this Hospital.";
description add-field-description --onProperty name --title "Nom de cet Hopital" --text "Le nom de cet hopital." --locale fr;
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "The hospital name is required" --text "The hospital name is required";
description add-notNull-message --onProperty name --title "Le nom de cet hopital est réquis" --text "Le nom de cet hopital est réquis" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

field string --named phone;
description add-field-description --onProperty phone --title "Phone" --text "The hospital Phone.";
description add-field-description --onProperty phone --title "Telephone" --text "Telephone" --locale fr;
display add-list-field --field phone;

field string --named street;
description add-field-description --onProperty street --title "Street" --text "The name of the street";
description add-field-description --onProperty street --title "Rue" --text "Nom de la rue" --locale fr;
display add-list-field --field street;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code oif this address";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de cette adresse" --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this address";
description add-field-description --onProperty city --title "Ville" --text "La localite de cette adresse" --locale fr;
display add-list-field --field city;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The zip code oif this address";
description add-field-description --onProperty country --title "Pays" --text "Le pays de cette adresse" --locale fr;

@/* Entité Prescriber */;
entity --named Prescriber --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Prescriber" --text "Prescriber";
description add-class-description  --locale fr --title "Prescripteur" --text "Prescripteur";

field string --named name;
description add-field-description --onProperty name --title "Prescriber Name" --text "The name of this Prescrip.";
description add-field-description --onProperty name --title "Nom du Prescripteur" --text "Le nom de ce Prescripteur." --locale fr;
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "The prescriber name is required" --text "The prescriber name is required";
description add-notNull-message --onProperty name --title "Le nom du prescripteur est réquis" --text "Le nom du prescripteur est réquis" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

field string --named phone;
description add-field-description --onProperty phone --title "Phone" --text "The Prescriber Phone.";
description add-field-description --onProperty phone --title "Telephone" --text "Telephone" --locale fr;
display add-list-field --field phone;

field string --named street;
description add-field-description --onProperty street --title "Street" --text "The name of the street";
description add-field-description --onProperty street --title "Rue" --text "Nom de la rue" --locale fr;
display add-list-field --field street;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code oif this address";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de cette adresse" --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this address";
description add-field-description --onProperty city --title "Ville" --text "La localite de cette adresse" --locale fr;
display add-list-field --field city;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The zip code oif this address";
description add-field-description --onProperty country --title "Pays" --text "Le pays de cette adresse" --locale fr;

@/* Entité Ordonnanciers */;
entity --named PrescriptionBook --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Prescription Blook" --text "Prescription book";
description add-class-description  --locale fr --title "Ordonnancier" --text "Ordonnancier";

field manyToOne --named prescriber --fieldType ~.jpa.Prescriber;
description add-field-description --onProperty prescriber --title "Prescriber" --text "The doctor who prescribed the order.";
description add-field-description --onProperty prescriber --title "Prescripteur" --text "Le medecin ayant prescrit de cet ordonnance." --locale fr;
association set-selection-mode --onProperty prescriber --selectionMode COMBOBOX;
association set-type --onProperty prescriber --type AGGREGATION --targetEntity ~.jpa.Prescriber;
display add-list-field --field prescriber.name;
constraint NotNull --onProperty prescriber;
description add-notNull-message --onProperty prescriber --title "The percriber must be selected" --text "The percriber must be selected";
description add-notNull-message --onProperty prescriber --title "Le prescripteur doit être sélectionnée" --text "Le prescripteur doit être sélectionnée" --locale fr;

field manyToOne --named hospital --fieldType ~.jpa.Hospital;
description add-field-description --onProperty hospital --title "Hospital" --text "The hospital subjet of this prescription.";
description add-field-description --onProperty hospital --title "Hopital" --text "Cet hopital ayant prescrit l ordonnance." --locale fr;
association set-selection-mode --onProperty hospital --selectionMode COMBOBOX;
association set-type --onProperty hospital --type AGGREGATION --targetEntity ~.jpa.Hospital;
display add-list-field --field hospital.name;
constraint NotNull --onProperty hospital;
description add-notNull-message --onProperty hospital --title "The hospital must be selected" --text "The hospital must be selected";
description add-notNull-message --onProperty hospital --title "Le hopital doit être sélectionnée" --text "Le hopital doit être sélectionnée" --locale fr;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "Originating agency";
description add-field-description --onProperty agency --title "Agency" --text "Agence originaire" --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "The originating agency of this prescription book must be selected" --text "The originating agency of this prescription book must be selected";
description add-notNull-message --onProperty agency --title "Le site de cet ordonnancier doit être sélectionné" --text "Le site de cet ordonnancier doit être sélectionné" --locale fr;

field manyToOne --named recordingAgent --fieldType ~.jpa.Login;
description add-field-description --onProperty recordingAgent --title "Recording Agent" --text "The user who recorded this prescription.";
description add-field-description --onProperty recordingAgent --title "Agent Saisie" --text "Utilisateur saisiessant cet ordonnance." --locale fr;
association set-selection-mode --onProperty recordingAgent --selectionMode COMBOBOX;
association set-type --onProperty recordingAgent --type AGGREGATION --targetEntity ~.jpa.Login;
display add-list-field --field recordingAgent.fullName;
constraint NotNull --onProperty recordingAgent;
description add-notNull-message --onProperty recordingAgent --title "The recording user must be selected" --text "The recording user must be selected";
description add-notNull-message --onProperty recordingAgent --title "La personne editant doit être sélectionné" --text "La personne editant doit être sélectionné" --locale fr;

field string --named prescriptionNumber;
description add-field-description --onProperty prescriptionNumber --title "Prescription Number" --text "The prescription number.";
description add-field-description --onProperty prescriptionNumber --title "Numéro de l Ordonnance" --text "Le numéro de l ordonnance." --locale fr;
display add-toString-field --field prescriptionNumber;
display add-list-field --field prescriptionNumber;

field manyToOne --named salesOrder --fieldType ~.jpa.SalesOrder;
description add-field-description --onProperty salesOrder --title "Sales Order" --text "The sales order containing this prescription.";
description add-field-description --onProperty salesOrder --title "Commande Client" --text "La commandeclient qui contient cet ordonnance." --locale fr;
association set-selection-mode --onProperty salesOrder --selectionMode FORWARD;
association set-type --onProperty salesOrder --type AGGREGATION --targetEntity ~.jpa.SalesOrder;
display add-list-field --field salesOrder.soNumber;

field temporal --type TIMESTAMP --named prescriptionDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty prescriptionDate --title "Prescription Date" --text "The prescription date.";
description add-field-description --onProperty prescriptionDate --title "Date de Prescription" --text "La date de prescription." --locale fr;
display add-list-field --field prescriptionDate;
format add-date-pattern --onProperty prescriptionDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date.";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie." --locale fr;
display add-list-field --field recordingDate;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 


cd ~~;


@/* Accounts Receivable */;

@/* Entité AvoirClient */;
entity --named CustomerVoucher --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Client Voucher" --text "Client voucher";
description add-class-description  --locale fr --title "Avoir Client" --text "Avoir client";
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole MANAGER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole CASHIER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole SALES;

field string --named voucherNumber;
description add-field-description --onProperty voucherNumber --title "Voucher Number" --text "The client voucher number.";
description add-field-description --onProperty voucherNumber --title "Numéro de cet Avoir" --text "Le numéro de l avoir du client." --locale fr;
display add-toString-field --field voucherNumber;
display add-list-field --field voucherNumber;

field manyToOne --named customerInvoice --fieldType ~.jpa.CustomerInvoice;
description add-field-description --onProperty customerInvoice --title "Customer Invoice" --text "The customer invoice associated with this voucher.";
description add-field-description --onProperty customerInvoice --title "Facure client" --text "La la facture client associe avec cet avoir." --locale fr;
association set-selection-mode --onProperty customerInvoice --selectionMode FORWARD;
association set-type --onProperty customerInvoice --type AGGREGATION --targetEntity ~.jpa.CustomerInvoice;
display add-list-field --field salesOrder.soNumber;

field number --named amount --type java.math.BigDecimal;
description add-field-description --onProperty amount --title "Amount" --text "Voucher amount.";
description add-field-description --onProperty amount --title "Montant" --text "Montant de cet avoir" --locale fr;
format add-number-type --onProperty amount --type CURRENCY;
display add-list-field --field amount;
constraint NotNull --onProperty amount;
description add-notNull-message --onProperty amount --title "The voucher amount is required" --text "The voucher amount is required";
description add-notNull-message --onProperty amount --title "Le montant de cet avoir est réquis" --text "Le montant de cet avoir est réquis" --locale fr;

field manyToOne --named customer --fieldType ~.jpa.Customer;
description add-field-description --onProperty customer --title "Customer" --text "Customer.";
description add-field-description --onProperty customer --title "Client" --text "Client." --locale fr;
association set-selection-mode --onProperty customer --selectionMode FORWARD;
association set-type --onProperty customer --type AGGREGATION --targetEntity ~.jpa.Customer;
display add-list-field --field customer.fullName;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "Agency.";
description add-field-description --onProperty agency --title "Agence" --text "Agence." --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;

field boolean --named canceled --primitive false; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the voucher was canceled or not.";
description add-field-description --onProperty canceled --title "Annulé" --text "Indique si l avoir  a été annulé ou non." --locale fr;
@/* default=false */;
display add-list-field --field canceled;

field manyToOne --named recordingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty recordingUser --title "User" --text "The user modifying this voucher.";
description add-field-description --onProperty recordingUser --title "Agent" --text "Agent de saisie ayant édité cet avoir." --locale fr;
association set-selection-mode --onProperty recordingUser --selectionMode COMBOBOX;
association set-type --onProperty recordingUser --type AGGREGATION --targetEntity ~.jpa.Login;
display add-list-field --field recordingUser;

field temporal --type TIMESTAMP --named modifiedDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty modifiedDate --title "Last Modified" --text "The last modification date.";
description add-field-description --onProperty modifiedDate --title "Derniere Edition" --text "La data de derniere edition de l avoir." --locale fr;
display add-list-field --field modifiedDate;
format add-date-pattern --onProperty modifiedDate --pattern "dd-MM-yyyy HH:mm"; 

field number --named amountUsed --type java.math.BigDecimal;
description add-field-description --onProperty amountUsed --title "Amount Used" --text "Amount used.";
description add-field-description --onProperty amountUsed --title "Montant Utilisé" --text "Montant utilisé." --locale fr;
format add-number-type --onProperty amountUsed --type CURRENCY;
display add-list-field --field amountUsed;

field boolean --named settled --primitive false; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the voucher is settled or not.";
description add-field-description --onProperty settled --title "Soldé" --text "Indique si l avoir est soldé ou non." --locale fr;
@/* default=false */;
display add-list-field --field settled;

field number --named restAmount --type java.math.BigDecimal;
description add-field-description --onProperty restAmount --title "Rest Amount" --text "The remaining credit on this voucher.";
description add-field-description --onProperty restAmount --title "Montant Restant" --text "Rest de l avoir client" --locale fr;
@/* Default=0 */;
display add-list-field --field restAmount;

field boolean --named voucherPrinted --primitive false;
description add-field-description --onProperty voucherPrinted --title "Printed" --text "Indicates whether the voucher is printed or not.";
description add-field-description --onProperty voucherPrinted --title "Imprimé" --text "Indique si l avoir est imprimé ou pas." --locale fr;
@/* default=false */;
display add-list-field --field voucherPrinted;


cd ~~;

@/* Enum type DocumentType State */;
java new-enum-type --named DocumentType --package ~.jpa ;
enum add-enum-class-description --title "Document Type" --text "The type of a document";
enum add-enum-class-description --title "Type du Document" --text "Type du document" --locale fr;
java new-enum-const CUSTUMER_INVOICE;
enum add-enum-constant-description --onConstant CUSTUMER_INVOICE --title "Customer Invoice" --text "Customer invoice";
enum add-enum-constant-description --onConstant CUSTUMER_INVOICE --title "Facture Client" --text "Facture client" --locale fr ;
java new-enum-const SUPPLIER_INVOICE;
enum add-enum-constant-description --onConstant SUPPLIER_INVOICE --title "Supplier Invoice" --text "Supplier invoice";
enum add-enum-constant-description --onConstant SUPPLIER_INVOICE --title "Facture Achat" --text "Facture achat" --locale fr ;
java new-enum-const DELIVERY_SLIP;
enum add-enum-constant-description --onConstant DELIVERY_SLIP --title "Delivery Slip" --text "Delivery slip";
enum add-enum-constant-description --onConstant DELIVERY_SLIP --title "Bordereau de Livraison" --text "Bordereau de livraison" --locale fr ;
java new-enum-const CASH_RECEIPT ;
enum add-enum-constant-description --onConstant CASH_RECEIPT --title "Cash Receipt" --text "Cash receipt";
enum add-enum-constant-description --onConstant CASH_RECEIPT --title "Reçu de Caisse" --text "Reçu de caisse" --locale fr ;
java new-enum-const CUSTUMER_VOUCHER;
enum add-enum-constant-description --onConstant CUSTUMER_VOUCHER --title "Customer Voucher" --text "Customer voucher";
enum add-enum-constant-description --onConstant CUSTUMER_VOUCHER --title "Avoir Client" --text "Avoir client" --locale fr ;
java new-enum-const BARCODE_REPORT;
enum add-enum-constant-description --onConstant BARCODE_REPORT --title "Barcode Report" --text "Barcode report";
enum add-enum-constant-description --onConstant BARCODE_REPORT --title "Listing des Codes Barres" --text "Listing des codes barres" --locale fr;
java new-enum-const ARTICLE_PCMT_REPORT;
enum add-enum-constant-description --onConstant ARTICLE_PCMT_REPORT --title "Periodical Article Procurement Report" --text "Periodical article procurement report";
enum add-enum-constant-description --onConstant ARTICLE_PCMT_REPORT --title "Rapport Périodique des Achats Articles" --text "Rapport périodique des achats articles" --locale fr;
java new-enum-const ARTICLE_SALES_REPORT;
enum add-enum-constant-description --onConstant ARTICLE_SALES_REPORT --title "Periodical Article Sales Report" --text "Periodical article sales report";
enum add-enum-constant-description --onConstant ARTICLE_SALES_REPORT --title "Rapport Périodique des Ventes Articles" --text "Rapport périodique des ventes articles" --locale fr;
java new-enum-const SECTION_SALES_REPORT;
enum add-enum-constant-description --onConstant SECTION_SALES_REPORT --title "Periodical Section Sales Report" --text "Periodical section sales report";
enum add-enum-constant-description --onConstant SECTION_SALES_REPORT --title "Rapport Périodique des Ventes par Rayon" --text "Rapport périodique des ventes par rayon" --locale fr;
java new-enum-const ARTICLE_PCMT_SALES_REPORT;
enum add-enum-constant-description --onConstant ARTICLE_PCMT_SALES_REPORT --title "Periodical Article Procurement and Sales Report" --text "Periodical article procurement and sales order";
enum add-enum-constant-description --onConstant ARTICLE_PCMT_SALES_REPORT --title "Rapport Périodique des Achats et Ventes Articles" --text "Rapport périodique des achats et ventes articles" --locale fr;
java new-enum-const CUSTOMER_SALES_REPORT;
enum add-enum-constant-description --onConstant CUSTOMER_SALES_REPORT --title "Periodical Customer Sales Report" --text "Periodical customer sales report";
enum add-enum-constant-description --onConstant CUSTOMER_SALES_REPORT --title "Rapport Périodique des Ventes Client" --text "Rapport périodique des ventes client" --locale fr;
java new-enum-const SUPPLIER_PCMT_REPORT;
enum add-enum-constant-description --onConstant SUPPLIER_PCMT_REPORT --title "Periodical Supplier Procurement Report" --text "Periodical supplier procurement report";
enum add-enum-constant-description --onConstant SUPPLIER_PCMT_REPORT --title "Rapport Périodique des Achats Fournisseur" --text "Rapport périodique des achats fournisseurs" --locale fr;
java new-enum-const STOCK_MVMT_REPORT;
enum add-enum-constant-description --onConstant STOCK_MVMT_REPORT --title "Periodical Stock Movement Report" --text "Periodical stock movement report";
enum add-enum-constant-description --onConstant STOCK_MVMT_REPORT --title "Rapport Périodique des Mouvements de Stock" --text "Rapport périodique des mouvements de stock" --locale fr;
java new-enum-const STOCK_MVMT_BY_ARTICLE;
enum add-enum-constant-description --onConstant STOCK_MVMT_BY_ARTICLE --title "Periodical Stock Movement Report by Article" --text "Periodical stock movement report by article";
enum add-enum-constant-description --onConstant STOCK_MVMT_BY_ARTICLE --title "Rapport Périodique des Mouvements de Stock par Article" --text "Rapport périodique des mouvements de stock par article" --locale fr;
java new-enum-const ARTICLE_REPORT;
enum add-enum-constant-description --onConstant ARTICLE_REPORT --title "Article Report" --text "Article report";
enum add-enum-constant-description --onConstant ARTICLE_REPORT --title "Listing des Articles" --text "Listing des articles" --locale fr;
java new-enum-const ARTICLE_REPORT_BY_SECTION;
enum add-enum-constant-description --onConstant ARTICLE_REPORT_BY_SECTION --title "Article Report by Section" --text "Article report by section";
enum add-enum-constant-description --onConstant ARTICLE_REPORT_BY_SECTION --title "Listing des Articles par Rayon" --text "Listing des articles par rayon" --locale fr;
java new-enum-const ARTICLE_WITH_VAT_REPORT;
enum add-enum-constant-description --onConstant ARTICLE_WITH_VAT_REPORT --title "Article with VAT Report" --text "Article with VAT report";
enum add-enum-constant-description --onConstant ARTICLE_WITH_VAT_REPORT --title "Listing des Articles à TVA" --text "Listing des articles à TVA" --locale fr;
java new-enum-const REVENUE_REPORT;
enum add-enum-constant-description --onConstant REVENUE_REPORT --title "Periodical Revenue Report" --text "Periodical revenue report";
enum add-enum-constant-description --onConstant REVENUE_REPORT --title "Rapport Périodique du Chiffre d Affaires" --text "Rapport périodique du chiffre d affaires" --locale fr;
java new-enum-const REVENUE_REPORT_BY_SALES_AGENT;
enum add-enum-constant-description --onConstant REVENUE_REPORT_BY_SALES_AGENT --title "Periodical Revenue Report by Sales Agent" --text "Periodical revenue report by sales agent";
enum add-enum-constant-description --onConstant REVENUE_REPORT_BY_SALES_AGENT --title "Rapport Périodique du Chiffre d Affaires par Vendeur" --text "Rapport périodique du chiffre d affaires par vendeur" --locale fr;
java new-enum-const CASH_STMT;
enum add-enum-constant-description --onConstant CASH_STMT --title "Periodical Cash Statement" --text "Periodical sash statement";
enum add-enum-constant-description --onConstant CASH_STMT --title "Rapport Périodique de Caisse" --text "Rapport périodique de caisse" --locale fr ;
java new-enum-const PRODUCT_DETAIL_REPORT;
enum add-enum-constant-description --onConstant PRODUCT_DETAIL_REPORT --title "Product Detail Configuration Report" --text "Product detail configuration report";
enum add-enum-constant-description --onConstant PRODUCT_DETAIL_REPORT --title "Listing des Relations de Décomposition" --text "Listing des relation de décomposition" --locale fr;
java new-enum-const OUT_OF_ORDER_REPORT;
enum add-enum-constant-description --onConstant OUT_OF_ORDER_REPORT --title "Out of Order Report" --text "Out of order report";
enum add-enum-constant-description --onConstant OUT_OF_ORDER_REPORT --title "Rapport des Produits en Rupture de Stock" --text "Rapport des produits en rupture de stock" --locale fr;
java new-enum-const DEBT_STMT;
enum add-enum-constant-description --onConstant DEBT_STMT --title "Debt Statement" --text "Customer debt statement";
enum add-enum-constant-description --onConstant DEBT_STMT --title "État Crédits" --text "État credit du client" --locale fr;
java new-enum-const DEBT_STMT_BY_CUSTOMER;
enum add-enum-constant-description --onConstant DEBT_STMT_BY_CUSTOMER --title "Debt Statement by Customer" --text "Debt statement by customer";
enum add-enum-constant-description --onConstant DEBT_STMT_BY_CUSTOMER --title "État Crédits par Client" --text "État credit par client" --locale fr;
java new-enum-const STOCK_APPRECIATION;
enum add-enum-constant-description --onConstant STOCK_APPRECIATION --title "Stock Appreciation" --text "Stock appreciation";
enum add-enum-constant-description --onConstant STOCK_APPRECIATION --title "Valorisation du Stock" --text "Valorisation du stock" --locale fr ;
java new-enum-const SECTION_REPORT;
enum add-enum-constant-description --onConstant SECTION_REPORT --title "Section Report" --text "Section report";
enum add-enum-constant-description --onConstant SECTION_REPORT --title "Listing des Rayons" --text "Listing des rayons" --locale fr ;
java new-enum-const CUSTOMER_REPORT;
enum add-enum-constant-description --onConstant CUSTOMER_REPORT --title "Customer Report" --text "Customer report";
enum add-enum-constant-description --onConstant CUSTOMER_REPORT --title "Listing des Clients" --text "Listing des clients" --locale fr ;
java new-enum-const INVENTORY_LIST;
enum add-enum-constant-description --onConstant INVENTORY_LIST --title "Periodical Inventory List" --text "Periodical inventory list";
enum add-enum-constant-description --onConstant INVENTORY_LIST --title "Listing Périodique des Inventaire" --text "Listing périodique des inventaires" --locale fr ;
java new-enum-const ALPHA_INVENTORY_STMT;
enum add-enum-constant-description --onConstant ALPHA_INVENTORY_STMT --title "Alphabetical Inventory Statements by Section by Agency" --text "Alphabetical inventory statements by section by agency";
enum add-enum-constant-description --onConstant ALPHA_INVENTORY_STMT --title "Fiche Alphabétique des Inventaires par Rayon par Agence" --text "Fiche alphabétique des inventaires par rayon par agence" --locale fr ;
java new-enum-const INVENTORY_REPORT;
enum add-enum-constant-description --onConstant INVENTORY_REPORT --title "Periodical Inventory Report" --text "Periodical inventory report";
enum add-enum-constant-description --onConstant INVENTORY_REPORT --title "Rapport Périodique des Inventaire" --text "Rapport périodique des inventaires" --locale fr ;
java new-enum-const PROCUREMENT_ORDER;
enum add-enum-constant-description --onConstant PROCUREMENT_ORDER --title "Procurement Order" --text "Procurement order";
enum add-enum-constant-description --onConstant PROCUREMENT_ORDER --title "Bon de Commande Achat" --text "Bon de commande achat" --locale fr ;

@/* Output Management */;

@/* Document */;
entity --named DocumentStore --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Documents" --text "Documents";
description add-class-description  --locale fr --title "Documents" --text "Documents";
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action NAV --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;

field string --named documentNumber;
description add-field-description --onProperty documentNumber --title "Document Number" --text "The document number.";
description add-field-description --onProperty documentNumber --title "Numéro du Document" --text "Le numéro du document." --locale fr;
display add-toString-field --field documentNumber;
display add-list-field --field documentNumber;

field custom --named documentType --type ~.jpa.DocumentType.java;
description add-field-description --onProperty documentType --title "Document Type" --text "The type of this document.";
description add-field-description --onProperty documentType --title "Type du Document" --text "Le type de ce document." --locale fr;
enum enumerated-field --onProperty documentType;
display add-list-field --field documentType;

field string --named documentLocation;
description add-field-description --onProperty documentLocation --title "Document Location" --text "The document location.";
description add-field-description --onProperty documentLocation --title "Lieu du Document" --text "Le lieu du document." --locale fr;
display add-toString-field --field documentLocation;
display add-list-field --field documentLocation;

field manyToOne --named recordingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty recordingUser --title "User" --text "The user modifying this document.";
description add-field-description --onProperty recordingUser --title "Agent" --text "Agent de saisie ayant édité ce document." --locale fr;
association set-selection-mode --onProperty recordingUser --selectionMode COMBOBOX;
association set-type --onProperty recordingUser --type AGGREGATION --targetEntity ~.jpa.Login;

field temporal --type TIMESTAMP --named modifiedDate; 
description add-field-description --onProperty modifiedDate --title "Last Modified" --text "The last modification date.";
description add-field-description --onProperty modifiedDate --title "Derniere Edition" --text "La data de derniere edition" --locale fr;
format add-date-pattern --onProperty modifiedDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named priodFrom; 
description add-field-description --onProperty priodFrom --title "Start Target Period" --text "The start of the target period.";
description add-field-description --onProperty priodFrom --title "Début Période Cible" --text "Le début de la période cible." --locale fr;
format add-date-pattern --onProperty priodFrom --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named periodTo; 
description add-field-description --onProperty periodTo --title "End Target Period" --text "The ent of the target period.";
description add-field-description --onProperty periodTo --title "Fin Période Cible" --text "La fin de la période cible." --locale fr;
format add-date-pattern --onProperty periodTo --pattern "dd-MM-yyyy HH:mm"; 

cd ~~;

@/* Document */;
entity --named DocumentArchive --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Document Archive" --text "Document Archive";
description add-class-description  --locale fr --title "Archive des Documents" --text "Archive des Documents";
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action NAV --roleEnum ~.jpa.AccessRoleEnum --toRole MANAGER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole MANAGER;

field string --named documentNumber;
description add-field-description --onProperty documentNumber --title "Document Number" --text "The document number.";
description add-field-description --onProperty documentNumber --title "Numéro du Document" --text "Le numéro du document." --locale fr;
display add-toString-field --field documentNumber;
display add-list-field --field documentNumber;

field custom --named documentType --type ~.jpa.DocumentType.java;
description add-field-description --onProperty documentType --title "Document Type" --text "The type of this document.";
description add-field-description --onProperty documentType --title "Type du Document" --text "Le type de ce document." --locale fr;
enum enumerated-field --onProperty documentType;
display add-list-field --field documentType;

field string --named documentLocation;
description add-field-description --onProperty documentLocation --title "Document Location" --text "The document location.";
description add-field-description --onProperty documentLocation --title "Lieu du Document" --text "Le lieu du document." --locale fr;
display add-toString-field --field documentLocation;
display add-list-field --field documentLocation;

field manyToOne --named recordingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty recordingUser --title "User" --text "The user modifying this document.";
description add-field-description --onProperty recordingUser --title "Agent" --text "Agent de saisie ayant édité ce document." --locale fr;
association set-selection-mode --onProperty recordingUser --selectionMode COMBOBOX;
association set-type --onProperty recordingUser --type AGGREGATION --targetEntity ~.jpa.Login;

field temporal --type TIMESTAMP --named modifiedDate; 
description add-field-description --onProperty modifiedDate --title "Last Modified" --text "The last modification date.";
description add-field-description --onProperty modifiedDate --title "Derniere Edition" --text "La data de derniere edition" --locale fr;
format add-date-pattern --onProperty modifiedDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named priodFrom; 
description add-field-description --onProperty priodFrom --title "Start Target Period" --text "The start of the target period.";
description add-field-description --onProperty priodFrom --title "Début Période Cible" --text "Le début de la période cible." --locale fr;
format add-date-pattern --onProperty priodFrom --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named periodTo; 
description add-field-description --onProperty periodTo --title "End Target Period" --text "The ent of the target period.";
description add-field-description --onProperty periodTo --title "Fin Période Cible" --text "La fin de la période cible." --locale fr;
format add-date-pattern --onProperty periodTo --pattern "dd-MM-yyyy HH:mm"; 

cd ~~;

repogen setup;

repogen new-repository --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest access-control --roleTable ~.jpa.RoleName.java --permissionTable ~.jpa.PermissionName.java --loginTable ~.jpa.Login.java;

cd ~~;

@/*  repotest setup */;

@/*  repotest create-test --packages src/main/java/org/adorsys/adpharma/ */;

cd ~~;

mvn clean install -DskipTests;













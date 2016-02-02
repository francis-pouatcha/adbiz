Inventory Processes
===================

Search, Display and Create Inventories
--------------------------------------
- Inventories can be searched by their status (closed, ongoing), by date.
- The search result is a list of inventories.
- Closed inventories can not be modifies.
- Ongoing inventories can be opened and processed.
- A "new button" allows the creation of a new inventory.

Inventory Date vs. Inventory Item Date
--------------------------------------
The inventory date is the date of the closing of the inventory. For example an inventory date can be on the 31.12.2012, but w start 
counting on the 30.12.2012 because our storage is too large.

The inventory item date it the moment where an inventory item has been counted. This is the time where the user enters the quantity in the application. The moment at which the user enters the inventory items quantity in very important for the correction of the stock value. Because while the inventory is being done, sales can be going on. This means that even though the inventory is closed later, at the moment the inventory is being closed, the stock value can have changed. That is why we use the moment of the declaration of the items stock value and not the moment of the closing for the correction of the stock value.

The inventory item date can be before or after the inventory date. The recorded stock movement between the inventory date and the 
inventory item date helps consolidate the inventory item to the inventory date.

Add Inventory Items
-------------------
Adding an inventory item requires following informations
- A section (this can be nested up to the lowest section in the chain)
- The cip
- The article lot
- The quantity
- The optional expiration date

 	- The user can search a product by name or cip and click on enter.
 	- The application displays a popup tp refine the selection if the user input has more than one result.
 	- The user select the target product and click on ok
 	- The application closes the popup and displays the product in the head line.
 	- The user enters the quantity and click on add.
 		- Even if the selected product is already in the inventory, the application creates a new inventory line 
 		
Validate the inventory
----------------------
- The application validates the inventory and displays all inventory item counted, sorted by section / article / person / inventory time
- The application also shows the stock movement between the inventory item date and the inventory date
- For section/article lot identical lines, the application displays color to attract the attention of the inventory manager

Manual Consolidation of Inventory Lines
---------------------------------------
For section/article lot identical lines, the application offers a possibility of discarding incorrect lines or even adding a new line with the correct time and quantity. In this case, the correct time will mostly be the inventory date.

Automatic Consolidation of Inventory Lines
------------------------------------------
For each section / article lot line, the application produces the final inventory line whose date is equivalent to the inventory date.

Closing an Inventory
--------------------
An inventory is said closed when a user can not add any additional item to that inventory. Consolidation can still take place.

Consolidation can take place on a closed inventory. This is generally necessary for conflicting lines.

Posting an Inventory
--------------------
Posting an inventory is the act of :
	- computing the value of the inventory,
	- modifying stock values
	- sending posting lines to the book keeping modules.

Printing an inventory
---------------------
- Printing all inventory lines
- Printing consolidated lines
- printing conflicting lines
- Printinh summary page

Archiving Inventories
---------------------
- Prunnning unconsolidated lines
- Moving consolidated lines to an archiving table.
 


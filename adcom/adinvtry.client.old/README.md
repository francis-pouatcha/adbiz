adstock
=======

Stock management of the adorsys commerce framework.


Inventory Processes
===================

Search, Display and Create Inventories
--------------------------------------

- Inventories can be searched by their status (closed, ongoing), by date.
- The search result is a list of inventories.
- Closed inventories can not be modifies.
- Ongoing inventories can be opened and processed.
- A "new button" allows the creation of a new inventory.

Add Inventory Items By Section
------------------------------

- The user clicks on the button "Add by Section"
	- The application shows a popup for the search and selection of a section.
	- The user navigates through the list of sections and and select the target section.
	- The application displays the list of products of the section in a pageable table.
	- The user selects products to be added to the inventory and click on add.
	- Or the user clicks on the button addall and all product of the selected section are added 
	- Finally the application displays the list of inventory items
- Enter the real quantity for each line
- Save the inventory
- Close the inventory
	- The application closes the inventory and modifies the stock values. 
	- The modification increases or reduces the stock value with the computed gap.
	
Add Inventory by Product Family
-------------------------------

- The user clicks on the button "Add by Product Family"
	- The application shows a popup for the search and selection of a section.
	- The user navigates through the list of families and select the target family.
	- The application displays the list of products of the family in a pageable table.
	- The user selects products to be added to the inventory and click on add.
	- Or the user clicks on the button add all and all product of the selected family are added to the inventory 
	- Finally the application displays the list of inventory items
- Enter the real quantity for each line
- Save the inventory
- Close the inventory
	- The application closes the inventory and modifies the stock values. 
	- The modification increases or reduces the stock value with the computed gap.
	
Add Inventory By Item
---------------------

- The user clicks on "Add by Item"
	- The application displays a head line for the selection of a product with following fields (cip, name, quantity)
 	- The user can search a product by name or cip and click on enter.
 	- The application displays a popup tp refine the selection if the user input has more than one result.
 	- The user select the target product and click on ok
 	- The application closes the popup and displays the product in the head line.
 	- The user enters the quantity and click on add.
 		- If the selected product is already in the inventory, the application promp the user with following enries ("Add To Existing", "Replace Existing", "Create New Line")
 		- If the user click on creat new line, the application prompts the user to enter the section.
 		- This approach will allow us to make an inventory for the same product located in different sections of the storage. 
- Save the inventory
- Close the inventory
	- The application closes the inventory and modifies the stock values. 
	- The modification increases or reduces the stock value with the computed gap.


Concept of Item Inventory Date
------------------------------
The moment at which the user enters the inventory items quantity in very important for the correction of the stock value. Because while the inventory is being done, sales can be going on. This means that even though the inventory is closed later, at the moment the inventory is being closed, the stock value can have changed. That is why we use the moment of the declaration of the items stock value and not the moment of the closing for the correction of the stock value.

Additional Functionality
------------------------

- Printing an inventory
- Archiving an inventory

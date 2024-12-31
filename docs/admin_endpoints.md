## Admin Endpoints

This controller handles the MVC views for managing items within the admin section of the application. It provides views for listing items, editing existing items, and creating new items.

### Methods

* **`listItems(Model model)`:**
    * Retrieves a list of all items from the service layer.
    * Adds the list of items to the `model` object.
    * Returns the "items/list" view, which renders the list of items.

* **`editItem(@PathVariable String id, Model model)`:**
    * Retrieves an item by its ID from the service layer.
    * If the item is found:
        * Adds the item to the `model` object.
        * Returns the "items/edit" view, which renders the edit form for the item.
    * If the item is not found:
        * Redirects to the item list page ("/admin/items").

* **`showCreateForm(Model model)`:**
    * Creates a new empty `Item` object.
    * Adds the empty `Item` object to the `model` object.
    * Returns the "items/create" view, which renders the form for creating a new item.

* **`handleError()`:**
    * This method is used as a generic error handler.
    * Returns the "error" view, which displays a generic error message. This method is typically called by other parts of the application in case of errors.

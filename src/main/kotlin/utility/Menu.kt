package utility

class Menu
{
    companion object{
        fun mainMenu ()
        {
            println("\nMain Menu:")
            println("Choose an option from bellow:")
            println("1. Create New category")
            println("2. Select an category")
            println("3. Delete an category")
            println("4. Print all category info")
            println("5. Print all items from every category")
            println("6. Exit.\n")
        }

        fun catMenu(catNo: Int)
        {
            println("\nCategory Menu - Category $catNo:")
            println("Choose an option from bellow:")
            println("1. Add an item")
            println("2. Remove an item")
            println("3. View an item details")
            println("4. Print all items")
            println("5. Exit.\n")
        }
    }
}
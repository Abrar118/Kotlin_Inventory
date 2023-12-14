import utility.CategoryManager
import utility.Menu
import java.util.*

class Manager
{
    private var choice: Int = 0
    private val scanner: Scanner = Scanner(System.`in`)
    private val inventory: Inventory = Inventory()

    suspend fun mainNavigation()
    {
        while (true)
        {
            Menu.mainMenu()
            print("Option: ")
            choice = scanner.nextInt()
            if(choice == 6)
            {
                scanner.close()
                break
            }

            when(choice)
            {
                1 -> inventory.addCategory()
                2 -> {
                    val selectedCat = inventory.selectCategory()
                    categoryNavigation(catNo = selectedCat)
                }
                3 -> inventory.deleteCategory()
                4 -> inventory.allCatInfo()
                5 -> inventory.printAllCats()
            }
        }
    }

    private suspend fun categoryNavigation(catNo: Int)
    {
        val categoryManager by lazy {
            CategoryManager(catNo)
        }

        while (true)
        {
            if(categoryManager.checkNull())
            {
                println("\n******* CATEGORY NOT FOUND *******\n")
                break
            }

            Menu.catMenu(catNo = catNo)
            print("Option: ")
            choice = scanner.nextInt()

            if(choice == 5) break

            when(choice)
            {
                1 -> categoryManager.addItem()
                2 -> categoryManager.removeItem()
                3 -> categoryManager.getItemInfo()
                4 -> categoryManager.printAllItem()
            }
        }
    }
}
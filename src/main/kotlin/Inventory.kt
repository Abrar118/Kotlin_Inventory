import com.mongodb.client.model.Filters
import database.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import utility.Category
import utility.Item
import java.util.*

class Inventory
{
    private var selectedCategory: Int = 0
    private var catCount = 0

    init {
        runBlocking {
            catCount = Connection.db().categories().estimatedDocumentCount().toInt()
        }
    }

    suspend fun addCategory()
    {
        val scanner = Scanner(System.`in`)
        println("Create a new category")
        print("Type of items: ")

        val type = scanner.nextLine()
        val catNo = ++catCount
        val items = mutableListOf<Item>()
        val newCategory = Category(type, catNo, items)

        Connection.db().categories().insertOne(newCategory)
    }

    fun selectCategory(): Int
    {
        val scanner = Scanner(System.`in`)
        print("Enter category number: ")
        selectedCategory = scanner.nextInt()
        return selectedCategory
    }

    suspend fun deleteCategory()
    {
        val scanner = Scanner(System.`in`)
        print("Enter category number: ")
        selectedCategory = scanner.nextInt()

        val filter = Filters.eq("categoryNo", selectedCategory)
        Connection.db().categories().deleteOne(filter = filter)
    }

    private suspend fun getCategories():List<Category>
    {
        val categories = Connection.db().categories().find().toList()
        return categories
    }

    suspend fun allCatInfo()
    {
        val categories = getCategories()
        println("\n-----------------------------------------------\n")
        categories.forEach {
            println("Category No: ${it.categoryNo}")
            println("Item Type: ${it.typeOfItems}")
            println("Number of items: ${it.items.size}\n")
        }
        println("\n-----------------------------------------------\n")
    }

    suspend fun printAllCats()
    {
        println("\n*****************************************************\n")
        val categories = getCategories()
        categories.forEach { itCat ->
            println("\n-----------------------------------------------\n")
            println("Category No: ${itCat.categoryNo}")
            println("Item Type: ${itCat.typeOfItems}")
            println("Number of items: ${itCat.items.size}\n")
            itCat.items.forEach{
                println("Item No: ${it.itemNo}")
                println("Item Name: ${it.itemName}")
                println("Amount: ${it.amount}\n")
            }
        }

        println("\n*****************************************************\n")
    }
}
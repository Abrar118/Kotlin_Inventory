package utility

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import database.Connection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import java.util.*

data class Category(
    val typeOfItems: String,
    val categoryNo: Int,
    val items: MutableList<Item>
)

data class Item(
    val itemName: String,
    val amount: Int,
    val itemNo: Int
)

class CategoryManager(categoryNo: Int)
{
    private var category: Category? = null
    private val scanner = Scanner(System.`in`)
    private var noOfItems = 0

    init {
        runBlocking {
            val filter = Filters.eq("categoryNo", categoryNo)
            category = Connection.db().categories().find(filter=filter).firstOrNull()
            if(category != null) noOfItems = category?.items?.size!!
        }
    }

    fun checkNull(): Boolean = category == null

    suspend fun addItem()
    {
        print("Name of the item: ")
        scanner.nextLine()
        val itemName = scanner.nextLine()
        val itemNo = ++noOfItems
        print("Amount: ")
        val amount = scanner.nextInt()

        val item = Item(itemName, amount, itemNo)

        category?.items?.add(item)

        val filter = Filters.eq("categoryNo", category?.categoryNo)
        val updateSet = Updates.push("items", item)

        Connection.db().categories().updateOne(filter = filter, update = updateSet)
    }

    suspend fun removeItem()
    {
        print("Item No: ")
        val itemNo = scanner.nextInt()
        val filter = Filters.eq("itemNo", itemNo)
        Connection.db().categories().deleteOne(filter = filter)
        category?.items?.removeIf{it.itemNo == itemNo}
    }

    fun getItemInfo()
    {
        print("Item No: ")
        val itemNo = scanner.nextInt()
        val item = category?.items?.find { it.itemNo == itemNo }

        if(item == null)
        {
            println("ITEM NOT FOUND")
            return
        }

        println("\nItem No: ${item.itemNo}")
        println("Item Name: ${item.itemName}")
        println("Amount: ${item.amount}\n")
    }

    fun printAllItem()
    {
        println("\n**********************************\n")
        category?.items?.forEach {
            println("\nItem No: ${it.itemNo}")
            println("Item Name: ${it.itemName}")
            println("Amount: ${it.amount}\n")
        }
        println("\n**********************************\n")
    }

}
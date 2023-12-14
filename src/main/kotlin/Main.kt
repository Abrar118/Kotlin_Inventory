import kotlinx.coroutines.runBlocking

fun main()
{
    val manager = Manager()
    println("----------Manage Your Inventory----------\n")
    runBlocking {
        manager.mainNavigation()
    }
    println("\n----------Thank You! See you soon----------")
}
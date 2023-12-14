package database

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.github.cdimascio.dotenv.dotenv
import utility.Category
import java.util.logging.Logger

class Connection
{
    //configuration
    private val env = dotenv()
    private val connectionURI = env["ATLAS_URI"] as String
    private val databaseName = env["DATABASE"] as String
    private val serverApi = ServerApi.builder().version(ServerApiVersion.V1).build()
    private val settings =
        ConnectionString(connectionURI)
            .let { MongoClientSettings.builder().applyConnectionString(it).serverApi(serverApi).build() }
    private val client = settings.let { MongoClient.create(it) }
    private val database = client.getDatabase(databaseName)
    val logger: Logger = Logger.getLogger(database.javaClass.toString())

    companion object {
        private var connectionInstance: Connection? = null

        fun db(): Connection
        {
            if(connectionInstance == null) connectionInstance = Connection()
            return connectionInstance!!
        }
    }

    fun categories() = database.getCollection<Category>("categories")
}

import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

data class Inventory(
    val id: Int?,
    val name: String?,
    val type: String?,
    val tags: List<String>,
    val purchasedAt: Long?,
    val placement: Placement
)

data class Placement(
    val id: Int?,
    val name: String?
)

fun functionOne(data: List<Inventory>) {
    val items = data.filter { it.placement.name?.contains("Meeting Room", true) == true }.map { it.name }
    println("Item di Meeting Room: $items")
}

fun functionTwo(data: List<Inventory>) {
    val items = data.filter { it.type?.contains("electronic", true) == true }.map { it.name }
    println("Item of Electronics: $items")
}

fun functionThree(data: List<Inventory>) {
    val items = data.filter { it.type?.contains("furniture", true) == true }.map { it.name }
    println("Item of Furniture: $items")
}

fun functionFour(data: List<Inventory>) {
    val items = data.filter { it.purchasedAt ?: Long.MAX_VALUE <= 1579107600000 && it.purchasedAt?: Long.MIN_VALUE > 1579021200000 }.map { it.name }
    println("Item Purchased at 16 Jan 2020: $items")
}

fun functionFive(data: List<Inventory>) {
    fun check(list: List<String>): Boolean {
        list.forEach {
            if (it.contains("Brown", true)) return true
        }
        return false
    }
    val users = data.filter { check(it.tags) }.map { it.name }
    println("Brown Item: $users")
}

fun main(args: Array<String>) {
    val data = JSONArray(JSON_DATA_STRING).map {
        it as JSONObject
        Inventory(
            id = it.getInt("inventory_id"),
            name = it.getString("name"),
            type = it.getString("type"),
            tags = it.getJSONArray("tags").map { tag -> tag as String },
            purchasedAt = it.getLong("purchased_at"),
            placement = Placement(
                id = it.getJSONObject("placement").getInt("room_id"),
                name = it.getJSONObject("placement").getString("name")
            )
        )
    }
    functionOne(data)
    functionTwo(data)
    functionThree(data)
    functionFour(data)
    functionFive(data)
}

val JSON_DATA_STRING = "[{\"inventory_id\":9382,\"name\":\"Brown Chair\",\"type\":\"furniture\",\"tags\":[\"chair\",\"furniture\",\"brown\"],\"purchased_at\":1579190471,\"placement\":{\"room_id\":3,\"name\":\"Meeting Room\"}},{\"inventory_id\":9380,\"name\":\"Big Desk\",\"type\":\"furniture\",\"tags\":[\"desk\",\"furniture\",\"brown\"],\"purchased_at\":1579190642,\"placement\":{\"room_id\":3,\"name\":\"Meeting Room\"}},{\"inventory_id\":2932,\"name\":\"LG Monitor 50 inch\",\"type\":\"electronic\",\"tags\":[\"monitor\"],\"purchased_at\":1579017842,\"placement\":{\"room_id\":3,\"name\":\"Lavender\"}},{\"inventory_id\":232,\"name\":\"Sharp Pendingin Ruangan 2PK\",\"type\":\"electronic\",\"tags\":[\"ac\"],\"purchased_at\":1578931442,\"placement\":{\"room_id\":5,\"name\":\"Dhanapala\"}},{\"inventory_id\":9382,\"name\":\"Alat Makan\",\"type\":\"tableware\",\"tags\":[\"spoon\",\"fork\",\"tableware\"],\"purchased_at\":1578672242,\"placement\":{\"room_id\":10,\"name\":\"Rajawali\"}}]"
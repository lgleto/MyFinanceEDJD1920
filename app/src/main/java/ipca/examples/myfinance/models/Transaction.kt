package ipca.examples.myfinance.models

import org.json.JSONObject
import java.util.*

enum class TransactionType(val value: String) {
    TRANSFERS("transferência"),
    PAYMENT("pagamento"),
    DEPOSIT("deposito")
}

class Transaction {
    var amount      : Double = 0.0
    var description : String = ""
    var date        : String = ""
    var type        : TransactionType = TransactionType.PAYMENT

    constructor(amount: Double, description: String, type: TransactionType) {
        this.amount = amount
        this.description = description
        this.type = type
    }

    constructor(jsonObject: JSONObject){
        amount = jsonObject.getDouble("amount")
        description = jsonObject.getString("description")
        date = jsonObject.getString("date")
        type = TransactionType.valueOf(jsonObject.getString("type"))
    }

    fun toJSON(): JSONObject {
        val obj = JSONObject()
        obj.put("amount"      , amount      )
        obj.put("description" , description )
        obj.put("date"        , date        )
        obj.put("type"        , type.value  )
        return obj
    }

}
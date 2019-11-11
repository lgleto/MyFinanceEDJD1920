package ipca.examples.myfinance.models

import com.google.firebase.database.DataSnapshot
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
        this.date = Date().toString()
    }

    constructor(dataSnapshot: DataSnapshot){
        amount = dataSnapshot.child("amount").toString().toDouble()
        description = dataSnapshot.child("description").toString()
        type = TransactionType.PAYMENT
        this.date = dataSnapshot.child("date").toString()
    }

    constructor(jsonObject: JSONObject){
        amount = jsonObject.getDouble("amount")
        description = jsonObject.getString("description")
        date = jsonObject.getString("date")
        val typeStr = jsonObject.getString("type")
        type = getTransType(typeStr)
    }

    fun getTransType (typeStr : String) : TransactionType {
        for (t in TransactionType.values()){
            if (t.value.equals(typeStr))
            {
                return t
            }
        }
        return TransactionType.PAYMENT
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
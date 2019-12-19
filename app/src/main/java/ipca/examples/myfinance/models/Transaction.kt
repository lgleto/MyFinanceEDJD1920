package ipca.examples.myfinance.models

import android.content.Context
import androidx.room.*
import com.google.firebase.database.DataSnapshot
import ipca.examples.myfinance.dateToString
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.util.*

enum class TransactionType(val value: String) {
    TRANSFERS("transferência"),
    PAYMENT("pagamento"),
    DEPOSIT("deposito")
}

@Entity
class Transaction {

    @PrimaryKey
    var uid         : Int    = 0
    var amount      : Double = 0.0
    var description : String = ""
    var date        : String = ""
    var type        : String = TransactionType.PAYMENT.value

    constructor(amount: Double, description: String, type: TransactionType) {
        this.uid = System.currentTimeMillis().toInt()
        this.amount = amount
        this.description = description
        this.type = type.value
        this.date = dateToString(Date())
    }

    constructor(dataSnapshot: DataSnapshot){
        amount =  dataSnapshot.child("amount").value.toString().toDouble()
        description = dataSnapshot.child("description").value.toString()
        type = getTransactionType(dataSnapshot.child("type").value.toString()).value
        this.date = dataSnapshot.child("date").value.toString()
    }

    fun getTransactionType(strTransaction: String) : TransactionType {
        for (t in TransactionType.values()){
            if (t.name.equals(strTransaction))
                return t
        }
        return TransactionType.TRANSFERS
    }

    constructor(jsonObject: JSONObject){
        amount = jsonObject.getDouble("amount")
        description = jsonObject.getString("description")
        date = jsonObject.getString("date")
        val typeStr = jsonObject.getString("type")
        type = getTransType(typeStr).value
    }

    constructor(uid: Int, amount: Double, description: String, date: String, type: String) {
        this.uid = uid
        this.amount = amount
        this.description = description
        this.date = date
        this.type = type
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
        obj.put("type"        , type        )
        return obj
    }

}

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction`")
    fun getAll(): List<Transaction>

    @Insert
    fun insert( user: Transaction)

    @Delete
    fun delete(user: Transaction)

}


class TransactionCoordinator {

    //callback :((name: String, room: Int) -> Unit)?

    companion object {
        fun insert(transaction: Transaction,
                    context: Context,
                    callback :(() -> Unit)?
        ){
            doAsync {
                AppDatabase.
                    getDatabase(context)?.
                    transactionDao()?.
                    insert(transaction!!)
                uiThread {
                    callback?.let {
                        it.invoke()
                    }
                }
            }

        }

        fun all(context: Context,
                   callback :((transitions : List<Transaction>?) -> Unit)?
        ){
            doAsync {
                val transactions = AppDatabase.
                    getDatabase(context)?.
                    transactionDao()?.
                    getAll()
                uiThread {
                    callback?.let {

                        it.invoke(transactions)
                    }
                }
            }

        }
    }



}

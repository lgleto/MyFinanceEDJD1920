package ipca.examples.myfinance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ipca.examples.myfinance.models.Transaction
import org.json.JSONObject

class TransactionDetailActivity : AppCompatActivity() {

    var transaction : Transaction? = null






    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_detail)
        intent.extras?.let{
            val strJson = it.getString(TRANSACTION)
            val jsonObject = JSONObject(strJson)
            transaction = Transaction(jsonObject)
        }


    }

    companion object{
        val TRANSACTION : String = "transaction"
    }
}

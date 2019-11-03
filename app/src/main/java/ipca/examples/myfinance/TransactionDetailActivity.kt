package ipca.examples.myfinance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ipca.examples.myfinance.models.Transaction
import kotlinx.android.synthetic.main.activity_transaction_detail.*
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
            updateView ()
        }
    }

    fun updateView (){
        transaction?.let{
            editTextAmount.setText("${it.amount} €")
            editTextDescription.setText(it.description)
            editTextDate.setText(it.date)
        }

    }

    companion object{
        val TRANSACTION : String = "transaction"
    }
}

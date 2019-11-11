package ipca.examples.myfinance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import ipca.examples.myfinance.models.Transaction
import ipca.examples.myfinance.models.TransactionType
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

        fabSave.setOnClickListener {

            if (transaction == null) {
                transaction = Transaction(
                    editTextAmount.text.toString().toDouble(),
                    editTextDescription.text.toString(),
                    TransactionType.PAYMENT)
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("users")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .child("transaction")

                myRef.push().setValue(transaction)
                this@TransactionDetailActivity.onBackPressed()
            }else {
                //update transaction
            }

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

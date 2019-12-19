package ipca.examples.myfinance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import ipca.examples.myfinance.models.AppDatabase
import ipca.examples.myfinance.models.Transaction
import ipca.examples.myfinance.models.TransactionCoordinator
import ipca.examples.myfinance.models.TransactionType
import kotlinx.android.synthetic.main.activity_transaction_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.util.*

class TransactionDetailActivity : AppCompatActivity() {

    var transaction : Transaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_detail)

        editTextDate.setText(dateToString(Date()))

        intent.extras?.let{
            val strJson = it.getString(TRANSACTION)
            val jsonObject = JSONObject(strJson)
            transaction = Transaction(jsonObject)
            updateView ()
        }


        ArrayAdapter.createFromResource(this@TransactionDetailActivity,
            R.array.transactions_array,
            android.R.layout.simple_spinner_item).also {arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTransactionType.adapter = arrayAdapter
        }



        fabSave.setOnClickListener {





            if (transaction == null) {
                var spinnerSelection = spinnerTransactionType.selectedItem.toString()
                var tType = getTransactionType(spinnerSelection)
                transaction = Transaction(
                    editTextAmount.text.toString().toDouble(),
                    editTextDescription.text.toString(),
                    tType)

                /*
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("users")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .child("transaction")

                myRef.push().setValue(transaction)

                 */



                TransactionCoordinator.insert(transaction!!,
                    this@TransactionDetailActivity) {
                    this@TransactionDetailActivity.onBackPressed()
                }


            }else {
                //update transaction
            }

        }
    }

    fun getTransactionType(strTransaction: String) : TransactionType {
        for (t in TransactionType.values()){
            if (t.value.equals(strTransaction))
                return t
        }
        return TransactionType.TRANSFERS
    }

    fun updateView (){
        transaction?.let{
            editTextAmount.setText("${it.amount} €")
            editTextDescription.setText(it.description)
            editTextDate.setText((it.date))
        }

    }

    companion object{
        val TRANSACTION : String = "transaction"
    }
}

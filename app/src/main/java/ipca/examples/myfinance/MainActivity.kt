package ipca.examples.myfinance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ipca.examples.myfinance.models.Transaction
import ipca.examples.myfinance.models.TransactionType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val transactions : MutableList<Transaction> = ArrayList<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transactions.add(Transaction(100.0,"Mesada", TransactionType.DEPOSIT))
        transactions.add(Transaction( 10.0,"Almoço", TransactionType.PAYMENT))
        transactions.add(Transaction(40.0,"Propinas", TransactionType.TRANSFERS))
        transactions.add(Transaction( 5.0,"Cafe", TransactionType.PAYMENT))

        title = "Balance: ${calculateAmount()} €"

        listViewTransaction.adapter = TransactionAdapter()
    }

    fun calculateAmount():Double{
        var total : Double = 0.0
        for (t in transactions){

            when (t.type){
                TransactionType.PAYMENT -> {
                    total -= t.amount
                }
                TransactionType.TRANSFERS -> {
                    total -= t.amount
                }
                TransactionType.DEPOSIT -> {
                    total += t.amount
                }
            }

        }
         return total
    }

    inner class TransactionAdapter : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val v : View = layoutInflater.inflate(R.layout.row_transaction,parent,false)
            val textViewDescription : TextView = v.findViewById(R.id.textViewDescription)
            val textViewAmount : TextView = v.findViewById(R.id.textViewAmount)
            val textViewTransactionType : TextView = v.findViewById(R.id.textViewTransactionType)

            textViewAmount.text = "${transactions.get(position).amount} €"
            textViewDescription.text = transactions.get(position).description
            textViewTransactionType.text = transactions.get(position).type.value

            v.setOnClickListener {
                val intent = Intent(this@MainActivity, TransactionDetailActivity::class.java)
                intent.putExtra(TransactionDetailActivity.TRANSACTION,
                    transactions.get(position).toJSON().toString())
                startActivity(intent)
            }

            return v
        }

        override fun getItem(position: Int): Any {
            return transactions.get(position)
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return transactions.size
        }

    }
}

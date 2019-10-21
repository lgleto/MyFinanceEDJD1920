package ipca.examples.myfinance.models

import java.util.*

enum class TransactionType(val value: String) {
    TRANSFERS("transfer"),
    PAYMENT("payment"),
    DEPOSIT("deposit")
}

class Transaction {
    var amount      : Double = 0.0
    var description : String = ""
    val date        : Date   = Date()
    var type        : TransactionType = TransactionType.PAYMENT
}
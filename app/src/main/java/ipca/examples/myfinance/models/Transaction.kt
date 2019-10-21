package ipca.examples.myfinance.models

import java.util.*

enum class TransactionType(val value: String) {
    TRANSFERS("transferência"),
    PAYMENT("pagamento"),
    DEPOSIT("deposito")
}

class Transaction {
    var amount      : Double = 0.0
    var description : String = ""
    val date        : Date   = Date()
    var type        : TransactionType = TransactionType.PAYMENT

    constructor(amount: Double, description: String, type: TransactionType) {
        this.amount = amount
        this.description = description
        this.type = type
    }

}
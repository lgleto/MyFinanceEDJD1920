package ipca.examples.myfinance.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import ipca.examples.myfinance.MainActivity

import ipca.examples.myfinance.R
import kotlinx.android.synthetic.main.activity_login.*
import java.util.prefs.Preferences

class LoginActivity : AppCompatActivity() {

    val TAG = "LoginActivity"

    private lateinit var auth: FirebaseAuth

    lateinit var usernameEditText : EditText
    lateinit var passwordEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        usernameEditText = findViewById<EditText>(R.id.username)
        passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)


        loginButton.setOnClickListener {
            login(usernameEditText.text.toString(),
                passwordEditText.text.toString())
        }

        val prefs = PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)
        val usernameStored = prefs.getString("username","")
        val passwordStored = prefs.getString("password","")
        val remember = prefs.getBoolean("remember",false)

        if (remember){
            usernameEditText.setText(usernameStored)
            passwordEditText.setText(passwordStored)
            checkBoxRemember.isChecked = true
        }


    }

    fun login(email:String, password:String ){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    if (checkBoxRemember.isChecked){
                        val prefs = PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)
                        prefs.edit().putString("username", usernameEditText.text.toString()).apply()
                        prefs.edit().putString("password", passwordEditText.text.toString()).apply()
                        prefs.edit().putBoolean("remember", true).apply()
                        prefs.edit().commit()

                    }else {
                        val prefs = PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)
                        prefs.edit().putString("username", "").apply()
                        prefs.edit().putString("password", "").apply()
                        prefs.edit().putBoolean("remember", false).apply()
                        prefs.edit().commit()
                    }

                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, getString(R.string.authentication_failed),
                        Toast.LENGTH_SHORT).show()

                }

                // ...
            }
    }
}

package ipca.examples.myfinance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import ipca.examples.myfinance.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    val TAG = "SplashActivity"
    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //auth = FirebaseAuth.getInstance()
    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.VISIBLE
        Timer("login",false).schedule(1000){

            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            /*
            val prefs = PreferenceManager.getDefaultSharedPreferences(this@SplashActivity)
            val usernameStored = prefs.getString("username","")
            val passwordStored = prefs.getString("password","")
            val remember = prefs.getBoolean("remember",false)

            if (remember){
                if (usernameStored!!.isNotEmpty() && passwordStored!!.isNotEmpty()){
                    auth.signInWithEmailAndPassword(usernameStored, passwordStored)
                        .addOnCompleteListener(this@SplashActivity) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information


                                val user = auth.currentUser
                                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                                startActivity(intent)


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)

                                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                                startActivity(intent)

                            }

                            // ...
                        }
                }
            }else{

                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            }


             */
        }
    }
}

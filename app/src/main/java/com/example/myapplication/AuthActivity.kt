package com.example.myapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val auth = FirebaseAuth.getInstance()

        val userEmail: EditText = findViewById(R.id.user_email_auth)
        val userPass: EditText = findViewById(R.id.user_password_auth)
        val button: Button = findViewById(R.id.button_auth)

        val linkToReq: TextView = findViewById(R.id.lin_to_req)

        linkToReq.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        button.setOnClickListener {
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty())
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_LONG).show()
            else {
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Аутентификация успешна
                            Toast.makeText(this, "Authorization is successful", Toast.LENGTH_LONG).show()

                            // Вы можете перейти на другую активность или выполнить другие действия
                            // например, перейти на главный экран приложения
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                            // Очистить поля ввода
                            userEmail.text.clear()
                            userPass.text.clear()
                        } else {
                            // Ошибка аутентификации, task.exception содержит информацию об ошибке
                            Toast.makeText(this, "Authorization is not successful", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

    }


}
package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userEmail: EditText = findViewById(R.id.user_email)
        val userPass: EditText = findViewById(R.id.user_password)
        val button: Button = findViewById(R.id.button_red)

        val linkToAuth: TextView = findViewById(R.id.lin_to_auth)

        linkToAuth.setOnClickListener {

            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty())
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_LONG).show()
            else {
                val auth = FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Регистрация успешна
                            val user = auth.currentUser
                            // Получите доступ к корневому узлу вашей базы данных
                            val database = FirebaseDatabase.getInstance()
                            val usersReference = database.getReference("users") // "users"
                            val userId = user?.uid
                            if (userId != null) {
                                // Создаем объект для сохранения в базе данных
                                val userData = User(email, pass, "AdditionalUserData")

                                // Записываем данные в базу данных
                                usersReference.child(userId).setValue(userData)

                                Toast.makeText(this, "User $email created", Toast.LENGTH_LONG)
                                    .show()
                            }

                                // Вы можете использовать объект user для получения информации о текущем пользователе
                            } else {
                                // Ошибка регистрации, task.exception содержит информацию об ошибке
                                Toast.makeText(
                                    this,
                                    "Authentication failed: ${task.exception?.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }


            }


        }

    }








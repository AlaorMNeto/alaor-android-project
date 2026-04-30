package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {
    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var bt_entrada: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)
        supportActionBar?.hide();
        IniciarComponentes();
        val linkFormCadastro = findViewById<TextView>(R.id.text_tela_cadastro)

        linkFormCadastro.setOnClickListener {
            val telaCadastro = Intent(this, FormCadastro::class.java)
            startActivity(telaCadastro)
        }

        bt_entrada.setOnClickListener(){
            val email = edit_email.text.toString()
            val senha = edit_senha.text.toString()
            if (email.isEmpty() || senha.isEmpty()) {
                val mensagemErro = "Campos não preenchidos, tente novamente"
                val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_LONG)
                snackbar.show()
            }else{
                AutenticarUsuario();
            }
        }

    }
    fun AutenticarUsuario(){
        val email = edit_email.text.toString()
        val senha = edit_senha.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                progressBar.visibility = View.GONE

                val user = FirebaseAuth.getInstance().currentUser
                val intent = Intent(this@FormLogin, TelaPrincipal::class.java)
                startActivity(intent)
                finish() //Finaliza a atividade atual para que o usuário não possa voltar para ela
            } else {
                task.exception?.message?.let { mensagemErro ->
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Erro ao autenticar usuário: $mensagemErro",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    fun IniciarComponentes() {
        edit_email = findViewById(R.id.edit_email_login)
        edit_senha = findViewById(R.id.edit_senha_login)
        bt_entrada = findViewById(R.id.bt_entrada)
        progressBar = findViewById(R.id.progress_bar)
    }
}

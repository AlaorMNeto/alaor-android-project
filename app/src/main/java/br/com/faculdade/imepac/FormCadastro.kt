package br.com.faculdade.imepac

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FormCadastro : AppCompatActivity() {

    // 1. Variáveis globais da classe
    private lateinit var edit_nome: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var btnCadastrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_cadastro)

        // 2. Configuração do ajuste de tela (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 3. Inicialização dos componentes
        edit_nome = findViewById(R.id.edit_nome)
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        btnCadastrar = findViewById(R.id.bt_cadastrar)

        // 4. Clique do botão Cadastrar
        btnCadastrar.setOnClickListener { view ->
            val nome = edit_nome.text.toString().trim()
            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show()
            } else {
                cadastrarUsuario(view)
            }
        }
    }

    // 5. Função para criar o usuário na Autenticação do Firebase
    private fun cadastrarUsuario(view: View) {
        val email = edit_email.text.toString().trim()
        val senha = edit_senha.text.toString().trim()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Se a conta foi criada, agora salvamos o Nome no Firestore
                    salvarDadosUsuario(view)
                } else {
                    val erro = task.exception?.message ?: "Erro ao cadastrar usuário"
                    Snackbar.make(view, erro, Snackbar.LENGTH_LONG).show()
                }
            }
    }

    // 6. Função para salvar informações adicionais no Firestore
    private fun salvarDadosUsuario(view: View) {
        val db = FirebaseFirestore.getInstance()
        val nome = edit_nome.text.toString().trim()
        val email = edit_email.text.toString().trim()
        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid

        if (usuarioID != null) {
            val usuarios = hashMapOf(
                "nome" to nome,
                "email" to email,
                "uid" to usuarioID
            )

            // Criamos um documento na coleção "Usuarios" usando o UID do usuário
            db.collection("Usuarios").document(usuarioID)
                .set(usuarios)
                .addOnSuccessListener {
                    Snackbar.make(view, "Cadastro realizado com sucesso!", Snackbar.LENGTH_LONG).show()
                    // Opcional: fechar a tela após sucesso
                    // finish()
                }
                .addOnFailureListener { e ->
                    Snackbar.make(view, "Erro ao salvar dados: ${e.message}", Snackbar.LENGTH_LONG).show()
                }
        } else {
            Snackbar.make(view, "Erro: Usuário não autenticado.", Snackbar.LENGTH_LONG).show()
        }
    }
}
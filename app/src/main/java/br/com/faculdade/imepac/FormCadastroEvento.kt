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
import com.google.firebase.firestore.FirebaseFirestore

class FormCadastroEvento : AppCompatActivity() {

    private lateinit var editNome: EditText
    private lateinit var editLocal: EditText
    private lateinit var editData: EditText
    private lateinit var editPreco: EditText
    private lateinit var btnSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_cadastro_evento)

        supportActionBar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialização dos Componentes
        editNome = findViewById(R.id.edit_nome_evento)
        editLocal = findViewById(R.id.edit_local_evento)
        editData = findViewById(R.id.edit_data_evento)
        editPreco = findViewById(R.id.edit_preco_evento)
        btnSalvar = findViewById(R.id.bt_salvar_evento)

        btnSalvar.setOnClickListener { view ->
            val nome = editNome.text.toString().trim()
            val local = editLocal.text.toString().trim()
            val data = editData.text.toString().trim()
            val preco = editPreco.text.toString().trim()

            if (nome.isEmpty() || local.isEmpty() || data.isEmpty() || preco.isEmpty()) {
                Snackbar.make(view, "Preencha todos os campos do evento!", Snackbar.LENGTH_LONG).show()
            } else {
                salvarDadosEvento(view)
            }
        }
    }

    private fun salvarDadosEvento(view: View) {
        val db = FirebaseFirestore.getInstance()

        val nome = editNome.text.toString().trim()
        val local = editLocal.text.toString().trim()
        val data = editData.text.toString().trim()
        val precoDouble = editPreco.text.toString().trim().toDoubleOrNull() ?: 0.0

        // Gerar uma referência com ID aleatório antecipadamente para termos a chave do documento
        val novoDocumentoRef = db.collection("Eventos").document()
        val eventoID = novoDocumentoRef.id

        val evento = hashMapOf(
            "id_evento" to eventoID,
            "nome" to nome,
            "local" to local,
            "data" to data,
            "preco" to precoDouble
        )

        // Salva os dados na coleção "Eventos" usando o ID gerado
        novoDocumentoRef.set(evento)
            .addOnSuccessListener {
                Snackbar.make(view, "Evento publicado com sucesso!", Snackbar.LENGTH_LONG).show()

                // Limpa os campos para o usuário poder cadastrar outro se quiser
                editNome.text.clear()
                editLocal.text.clear()
                editData.text.clear()
                editPreco.text.clear()
            }
            .addOnFailureListener { e ->
                Snackbar.make(view, "Erro ao publicar evento: ${e.message}", Snackbar.LENGTH_LONG).show()
            }
    }
}
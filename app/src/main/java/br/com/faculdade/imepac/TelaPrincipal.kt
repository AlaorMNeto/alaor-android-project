package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class TelaPrincipal : AppCompatActivity() {

    private lateinit var btnIrCadastroEvento: Button
    private lateinit var btnIrListaEventos: Button
    private lateinit var btnSair: Button // Declaração do novo botão

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_principal)

        supportActionBar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializando os componentes
        btnIrCadastroEvento = findViewById(R.id.btn_ir_cadastro_evento)
        btnIrListaEventos = findViewById(R.id.btn_ir_lista_eventos)
        btnSair = findViewById(R.id.btn_sair) // Link com o XML

        // Ação: Abrir cadastro de eventos
        btnIrCadastroEvento.setOnClickListener {
            val intent = Intent(this, FormCadastroEvento::class.java)
            startActivity(intent)
        }

        // Ação: Abrir lista de eventos
        btnIrListaEventos.setOnClickListener {
            // Descomente quando criar a activity de lista
            // val intent = Intent(this, ListaEventosActivity::class.java)
            // startActivity(intent)
        }

        // Ação: Desconectar do Firebase e Voltar para o Login
        btnSair.setOnClickListener {
            // 1. Faz o logout no Firebase
            FirebaseAuth.getInstance().signOut()

            // 2. Cria a rota para voltar para a tela de Login
            val intent = Intent(this, FormLogin::class.java)

            // 3. Limpa a pilha de telas do Android (evita que o usuário volte ao menu pelo botão físico do celular)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish() // Encerra de vez a TelaPrincipal
        }
    }
}
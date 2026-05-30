package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide() // Esconde a barra superior de título

        // Handler para segurar a tela por 3000 milissegundos (3 segundos)
        Handler(Looper.getMainLooper()).postDelayed({
            verificarUsuarioLogado()
        }, 3000)
    }

    private fun verificarUsuarioLogado() {
        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null) {
            // Se o usuário já está logado de outra vez, vai direto para o Menu/Dashboard
            // (Substitua 'FormLogin' por sua futura classe de Menu/Dashboard quando criá-la)
            val intent = Intent(this, TelaPrincipal::class.java)
            startActivity(intent)
        } else {
            // Se não está logado, vai para a tela de Login
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
        }
        finish() // Destrói a Splash para o usuário não voltar nela ao apertar o botão "voltar"
    }
}
package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPerfil : AppCompatActivity() {
    private lateinit var emailUser: TextView
    private lateinit var usuarioUser: TextView
    private lateinit var bt_sair: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_perfil)
        supportActionBar?.hide();
        IniciarComponentes();
        db = FirebaseFirestore.getInstance()
        bt_sair.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(this@TelaPerfil, FormLogin::class.java);
            startActivity(intent);
            finish();
        }
    }
    fun IniciarComponentes() {
        emailUser = findViewById(R.id.textEmailUser)
        usuarioUser = findViewById(R.id.textNomeUser)
        bt_sair = findViewById(R.id.bt_sair)
    }
}
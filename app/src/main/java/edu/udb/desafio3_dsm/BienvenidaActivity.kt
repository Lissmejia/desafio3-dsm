package edu.udb.desafio3_dsm

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class BienvenidaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        val logo = findViewById<ImageView>(R.id.imgLogo)
        logo.setImageResource(R.drawable.op)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}

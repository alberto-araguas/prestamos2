package com.aaraguas.ilernaprestamos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_agregar_usuario.*

class agregarUsuario : AppCompatActivity() {


    private lateinit var dbaseHelper: dbaseSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_usuario)
        val button: Button = findViewById(R.id.buttonSALIR)
        button.setOnClickListener {
            cerrarActvity()
        }
    }

    fun cerrarActvity (){

        val intent = Intent(this, UsuariosActivity::class.java).apply {

        }
        startActivity(intent)
    }


}
package com.aaraguas.ilernaprestamos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_agregar_usuario.*
import java.lang.Exception

class agregarUsuario : AppCompatActivity() {


    private lateinit var dbaseHelper: dbaseSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_usuario)

        dbaseHelper = dbaseSQLiteHelper(this)

        val button: Button = findViewById(R.id.buttonSALIR)
        button.setOnClickListener {
            cerrarActvity()
        }
        val button2: Button = findViewById(R.id.buttonINSERTAR)
        button2.setOnClickListener {
            insertarUsuario()
            cerrarActvity()
        }
    }

    private fun insertarUsuario() {
    try{
      val   telefono : String = et_Phone.text.toString()

      dbaseHelper.agregarUsuario(et_nombreusuario.text.toString(),et_direccionusuario.text.toString(),
                telefono.toInt(), et_correousuario.text.toString(),et_departamento.text.toString() )

    }catch (e : Exception){
        Toast.makeText(this, "RELLENAR ANTES TODOS LOS DATOS", Toast.LENGTH_LONG).show()

    }

    }

    fun cerrarActvity (){

        val intent = Intent(this, UsuariosActivity::class.java).apply {

        }
        startActivity(intent)
    }


}
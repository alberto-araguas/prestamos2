package com.aaraguas.ilernaprestamos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_familia.*
import kotlinx.android.synthetic.main.activity_agregar_lugar.*
import kotlinx.android.synthetic.main.activity_agregar_proveedor.*
import kotlinx.android.synthetic.main.activity_agregar_usuario.*
import kotlinx.android.synthetic.main.activity_agregar_usuario.et_Phone
import java.lang.Exception

class agregarLugar : AppCompatActivity() {

    private lateinit var dbaseHelper: dbaseSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_lugar)

        dbaseHelper = dbaseSQLiteHelper(this)

        val button: Button = findViewById(R.id.btSALIRlugar)
        button.setOnClickListener {
            cerrarActvity()
        }
        val button2: Button = findViewById(R.id.btINSERTARlugar)
        button2.setOnClickListener {
            insertarLugar()
            cerrarActvity()
        }
    }

    private fun insertarLugar() {
        if (et_nombrelugar.text.toString()==""){
            Toast.makeText(this,"FALTAN DATOS RELLENADOS", Toast.LENGTH_SHORT).show()
        }else {
            try {
                val telefono: String = et_Phonelugar.text.toString()

                dbaseHelper.agregarLugar(
                    et_nombrelugar.text.toString(), et_direccionlugar.text.toString(),
                    telefono.toInt(), et_correolugar.text.toString()
                )
            } catch (e: Exception) {
                Toast.makeText(this, "RELLENAR ANTES TODOS LOS DATOS", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cerrarActvity (){

        val intent = Intent(this, LugaresActivity::class.java).apply {

        }
        startActivity(intent)
    }
}
package com.aaraguas.ilernaprestamos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_familia.*
import kotlinx.android.synthetic.main.activity_agregar_lugar.*
import java.lang.Exception

class agregarFamilia : AppCompatActivity() {

    private lateinit var dbaseHelper: dbaseSQLiteHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_familia)

        dbaseHelper = dbaseSQLiteHelper(this)

        val button: Button = findViewById(R.id.btSALIRfam)
        button.setOnClickListener {
            cerrarActvity()
        }
        val button2: Button = findViewById(R.id.btINSERTARfam)
        button2.setOnClickListener {
            insertarFamilia()
            cerrarActvity()
        }

    }
    private fun insertarFamilia() {
    try{
        dbaseHelper.agregarFamilia(et_nombrefamilia.text.toString() )

    }catch (e:Exception){
        Toast.makeText(this, "RELLENAR ANTES TODOS LOS DATOS", Toast.LENGTH_LONG).show()
    }

    }

    fun cerrarActvity (){

        val intent = Intent(this, FamiliaActivity::class.java).apply {

        }
        startActivity(intent)
    }


}
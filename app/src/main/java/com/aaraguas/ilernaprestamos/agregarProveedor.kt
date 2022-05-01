package com.aaraguas.ilernaprestamos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_agregar_proveedor.*
import kotlinx.android.synthetic.main.activity_agregar_usuario.*
import kotlinx.android.synthetic.main.activity_agregar_usuario.et_Phone

class agregarProveedor : AppCompatActivity() {

    private lateinit var dbaseHelper: dbaseSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_proveedor)

        dbaseHelper = dbaseSQLiteHelper(this)

        val button: Button = findViewById(R.id.btSALIR)
        button.setOnClickListener {
            cerrarActvity()
        }
        val button2: Button = findViewById(R.id.btINSERTAR)
        button2.setOnClickListener {
            insertarProveedor()
            cerrarActvity()
        }
    }

    private fun insertarProveedor() {

        val   telefono : String = et_Phone.text.toString()

        dbaseHelper.agregarProveedor(et_nombreproveedor.text.toString(),et_direccionproveedor.text.toString(),
            telefono.toInt(), et_correoproveedor.text.toString(),et_contacto.text.toString() )

    }

    fun cerrarActvity (){

        val intent = Intent(this, ProveedoresActivity::class.java).apply {

        }
        startActivity(intent)
    }


}
package com.aaraguas.ilernaprestamos

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aaraguas.ilernaprestamos.Adapters.RecycleViewAdapterLugares
import com.aaraguas.ilernaprestamos.Adapters.RecyclerViewAdapterProveedores
import com.aaraguas.ilernaprestamos.databinding.ActivityProveedoresBinding

class ProveedoresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProveedoresBinding
    private lateinit var proveedoresDBHelper: dbaseSQLiteHelper
    private lateinit var db: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProveedoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        proveedoresDBHelper=dbaseSQLiteHelper(this)

        proveedoresDBHelper.agregarProveedor("PC COMPUTADORES SL","Avenida Del Pilar 11",974400400,"contacto@pccomputadoes.es","Adelardo Sanchez")


        db=proveedoresDBHelper.readableDatabase

        val cursor : Cursor = db.rawQuery("SELECT * FROM proveedores",null)

        val adaptador = RecyclerViewAdapterProveedores()
        adaptador.RecyclerViewAdapterProveedores(this, cursor)

        binding.listaProveedores.setHasFixedSize(true)
        binding.listaProveedores.layoutManager= LinearLayoutManager(this)
        binding.listaProveedores.adapter=adaptador
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_proveedores, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.opProveedorInsertar -> {

                true
            }
            R.id.opProveedorBorrar -> {
                true
            }
            R.id.opProveedorVolver -> {
                val intent = Intent(this, MainActivity::class.java).apply {

                }
                startActivity(intent)
                true
            }
            else -> {
                //cargaBusqueda(dbaseSQLiteHelper.CAMPO_ESTILO, item.title.toString())
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
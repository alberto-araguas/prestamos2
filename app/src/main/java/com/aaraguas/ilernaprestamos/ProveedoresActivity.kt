package com.aaraguas.ilernaprestamos

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aaraguas.ilernaprestamos.Adapters.RecycleViewAdapterLugares
import com.aaraguas.ilernaprestamos.Adapters.RecyclerViewAdapterProveedores
import com.aaraguas.ilernaprestamos.databinding.ActivityProveedoresBinding
import kotlinx.android.synthetic.main.layout_borra.*

class ProveedoresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProveedoresBinding
    private lateinit var proveedoresDBHelper: dbaseSQLiteHelper
    private lateinit var db: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProveedoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        proveedoresDBHelper=dbaseSQLiteHelper(this)

        //proveedoresDBHelper.agregarProveedor("TELEFONOS DEL NORTE SL","Calle Eroles 41",974000000,"gerencia@telefonos.es","Javier Samperiz")


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

    private fun borraProveedor(iden:String){
        proveedoresDBHelper.borraProveedor(iden)
        val intent = Intent(this, ProveedoresActivity::class.java).apply {

        }
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_proveedores, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.opProveedorInsertar -> {
                val intent = Intent(this, agregarProveedor::class.java).apply {

                }
                startActivity(intent)
                true
            }
            R.id.opProveedorBorrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Borrar")
                val dView = layoutInflater.inflate(R.layout.layout_borra, null)
                builder.setView(dView)
                builder.setPositiveButton(android.R.string.ok) {
                        dialogo, _ ->
                    borraProveedor( (dialogo as AlertDialog).etBorra.text.toString() )
                }
                builder.setNegativeButton(android.R.string.cancel, null)
                builder.show()
                true
            }
            R.id.opProveedorVolver -> {
                val intent = Intent(this, MainActivity::class.java).apply {

                }
                startActivity(intent)
                true
            }
            else -> {

                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
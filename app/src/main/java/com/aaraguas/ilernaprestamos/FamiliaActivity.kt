package com.aaraguas.ilernaprestamos

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.aaraguas.ilernaprestamos.Adapters.RecycleViewAdapterFamilia
import com.aaraguas.ilernaprestamos.databinding.ActivityFamiliaBinding
import kotlinx.android.synthetic.main.layout_borra.*


class FamiliaActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFamiliaBinding
    private lateinit var familiaDBHelper: dbaseSQLiteHelper
    private lateinit var db: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFamiliaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        familiaDBHelper=dbaseSQLiteHelper(this)

        //familiaDBHelper.agregarFamilia("Telefonos")


        db=familiaDBHelper.readableDatabase

        val cursor : Cursor = db.rawQuery("SELECT * FROM familia",null)

        val adaptador = RecycleViewAdapterFamilia()
        adaptador.RecycleViewAdapterFamilia(this, cursor)

        binding.listaFamilias.setHasFixedSize(true)
        binding.listaFamilias.layoutManager= LinearLayoutManager(this)
        binding.listaFamilias.adapter=adaptador
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_familia, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.opFamiliaInsertar -> {

                true
            }
            R.id.opFamiliaBorrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Borrar")
                val dView = layoutInflater.inflate(R.layout.layout_borra, null)
                builder.setView(dView)
                builder.setPositiveButton(android.R.string.ok) {
                        dialogo, _ ->
                    //borraUsuario( (dialogo as AlertDialog).etBorra.text.toString() )
                }
                builder.setNegativeButton(android.R.string.cancel, null)
                builder.show()
                true
            }
            R.id.opFamiliaVolver -> {
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
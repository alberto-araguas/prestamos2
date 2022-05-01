package com.aaraguas.ilernaprestamos

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.aaraguas.ilernaprestamos.Adapters.RecycleViewAdapterLugares
import com.aaraguas.ilernaprestamos.databinding.ActivityLugaresBinding


class LugaresActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLugaresBinding
    private lateinit var lugaresDBHelper: dbaseSQLiteHelper
    private lateinit var db: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLugaresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lugaresDBHelper=dbaseSQLiteHelper(this)

        //lugaresDBHelper.agregarLugar("Salon Multiusos","Plaza Mayor 4",974321123,"multiusos@miempresa.es")


        db=lugaresDBHelper.readableDatabase

        val cursor : Cursor = db.rawQuery("SELECT * FROM lugares",null)

        val adaptador = RecycleViewAdapterLugares()
        adaptador.RecycleViewAdapterLugares(this, cursor)

        binding.listaLugares.setHasFixedSize(true)
        binding.listaLugares.layoutManager= LinearLayoutManager(this)
        binding.listaLugares.adapter=adaptador
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_lugares, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.opLugarInsertar -> {

                true
            }
            R.id.opLugarBorrar -> {
                true
            }
            R.id.opLugarVolver -> {
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
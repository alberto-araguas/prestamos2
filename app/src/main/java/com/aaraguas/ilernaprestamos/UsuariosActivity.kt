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
import com.aaraguas.ilernaprestamos.Adapters.RecyclerViewAdapterUsuarios
import com.aaraguas.ilernaprestamos.databinding.ActivityUsuariosBinding


class UsuariosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsuariosBinding
    private lateinit var usuariosDBHelper: dbaseSQLiteHelper
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuariosDBHelper=dbaseSQLiteHelper(this)

        usuariosDBHelper.agregarUsuario("ALBERTO ARAGUAS GARCES","Calle del Palomar 7",616616616,"alberto@alberto.com","INFORMATICA")


        db=usuariosDBHelper.readableDatabase

        val cursor : Cursor = db.rawQuery("SELECT * FROM usuarios",null)

        val adaptador = RecyclerViewAdapterUsuarios()
        adaptador.RecyclerViewAdapterUsuarios(this, cursor)

        binding.listaUsuarios.setHasFixedSize(true)
        binding.listaUsuarios.layoutManager= LinearLayoutManager(this)
        binding.listaUsuarios.adapter=adaptador
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_usuarios, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.opUsuarioInsertar -> {

                true
            }
            R.id.opUsuarioBorrar -> {
                true
            }
            R.id.opUsuarioVolver -> {
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
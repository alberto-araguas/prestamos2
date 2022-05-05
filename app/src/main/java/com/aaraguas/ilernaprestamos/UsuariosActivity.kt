package com.aaraguas.ilernaprestamos

import android.app.Dialog
import android.content.DialogInterface
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
import com.aaraguas.ilernaprestamos.Adapters.RecyclerViewAdapterUsuarios
import com.aaraguas.ilernaprestamos.databinding.ActivityUsuariosBinding
import kotlinx.android.synthetic.main.layout_borra.*
import kotlinx.android.synthetic.main.layout_busca.*
import java.util.*


class UsuariosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsuariosBinding
    private lateinit var usuariosDBHelper: dbaseSQLiteHelper
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuariosDBHelper=dbaseSQLiteHelper(this)


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

    private fun borraUsuario(iden:String){
        usuariosDBHelper.borraUsuario(iden)
        val intent = Intent(this, UsuariosActivity::class.java).apply {

        }
        startActivity(intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.opUsuarioInsertar -> {
                val intent = Intent(this, agregarUsuario::class.java).apply {

                }
                startActivity(intent)
                true
            }
            R.id.opUsuarioBorrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Borrar")
                val dView = layoutInflater.inflate(R.layout.layout_borra, null)
                builder.setView(dView)
                builder.setPositiveButton(android.R.string.ok) {
                        dialogo, _ ->
                    borraUsuario( (dialogo as AlertDialog).etBorra.text.toString() )
                }
                builder.setNegativeButton(android.R.string.cancel, null)
                builder.show()
                true
            }
            R.id.opUsuarioVolver -> {
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
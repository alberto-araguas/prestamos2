package com.aaraguas.ilernaprestamos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.aaraguas.ilernaprestamos.Adapters.RecyclerViewAdapterPrestamos
import com.aaraguas.ilernaprestamos.databinding.ActivityMainBinding

import kotlinx.android.synthetic.main.layout_busca.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbaseHelper: dbaseSQLiteHelper
    private val adaptador = RecyclerViewAdapterPrestamos()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbaseHelper = dbaseSQLiteHelper(this)
        binding.rvDiscos.setHasFixedSize(true)
        binding.rvDiscos.layoutManager = LinearLayoutManager(this)

        binding.fbtnAgregar.setOnClickListener {
            val intentAgregar = Intent(this, AgregarActivity::class.java)
            startActivity(intentAgregar)
        }
    }

    override fun onResume() {
        super.onResume()
        cargaDiscos()
    }

    fun cargaDiscos() {
        val cursor = dbaseHelper.obtenerTodosDiscos(dbaseSQLiteHelper.TABLA_DISCOS, dbaseSQLiteHelper.CAMPO_TITULO)
        adaptador.RecyclerViewAdapterDiscos(this, cursor)
        binding.rvDiscos.adapter = adaptador
    }

    fun cargaBusqueda(campo: String, campoValor: String) {
        val cursor = dbaseHelper.obtenerCursor(dbaseSQLiteHelper.TABLA_DISCOS, campo,
            campoValor, campo)
        adaptador.RecyclerViewAdapterDiscos(this, cursor)
        binding.rvDiscos.adapter = adaptador
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opBuscar -> {
                val adaptSpBusca = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, arrayOf(
                        dbaseSQLiteHelper.CAMPO_TITULO.capitalize(),
                        dbaseSQLiteHelper.CAMPO_ARTISTA.capitalize()))
                adaptSpBusca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Buscar")
                val dView = layoutInflater.inflate(R.layout.layout_busca, null)
                builder.setView(dView)
                val dSpinner = dView.findViewById<Spinner>(R.id.spBusca)
                dSpinner.adapter = adaptSpBusca
                builder.setPositiveButton(android.R.string.ok) {
                        dialogo, _ ->
                    cargaBusqueda(
                        (dialogo as AlertDialog).spBusca.selectedItem.toString().toLowerCase(),
                        (dialogo as AlertDialog).etBusca.text.toString())
                }
                builder.setNegativeButton(android.R.string.cancel, null)
                builder.show()
                true
            }
            R.id.opMostrarTodos -> {
                cargaDiscos()
                true
            }
            R.id.opMostrar -> {
                true
            }
            R.id.opUsuario -> {
                val intent = Intent(this, UsuariosActivity::class.java).apply {

                }
                startActivity(intent)
                true
            }
            R.id.opProveedor -> {
                val intent = Intent(this, ProveedoresActivity::class.java).apply {

                }
                startActivity(intent)
                true
            }
            R.id.opLocales -> {
                val intent = Intent(this, LugaresActivity::class.java).apply {

                }
                startActivity(intent)
                true
            }
            R.id.opFamilia -> {
                val intent = Intent(this, FamiliaActivity::class.java).apply {

                }
                startActivity(intent)
                true
            }
            else -> {
                cargaBusqueda(dbaseSQLiteHelper.CAMPO_ESTILO, item.title.toString())
                true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbaseHelper.cerrarDB()
    }
}
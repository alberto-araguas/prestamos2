package com.aaraguas.ilernaprestamos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.aaraguas.ilernaprestamos.Adapters.RecyclerViewAdapterPrestamos
import com.aaraguas.ilernaprestamos.databinding.ActivityMainBinding

import kotlinx.android.synthetic.main.layout_busca.*
import java.time.Duration
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbaseHelper: dbaseSQLiteHelper
    private val adaptador = RecyclerViewAdapterPrestamos()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbaseHelper = dbaseSQLiteHelper(this)
        binding.rvPrestamos.setHasFixedSize(true)
        binding.rvPrestamos.layoutManager = LinearLayoutManager(this)

        binding.fbtnAgregar.setOnClickListener {
            val intentAgregar = Intent(this, AgregarActivity::class.java)
            startActivity(intentAgregar)
        }


    }

    override fun onResume() {
        super.onResume()
        cargaPrestamos()
    }

    fun cargaPrestamos() {
        val cursor = dbaseHelper.obtenerTodosPrestamos(dbaseSQLiteHelper.TABLA_PRESTAMOS, dbaseSQLiteHelper.CAMPO_CARACTERISTICAS)
        adaptador.RecyclerViewAdapterPrestamos(this, cursor)
        binding.rvPrestamos.adapter = adaptador
    }
    private fun cargaSoloPrestados() {
        val cursor = dbaseHelper.obtenerSoloPrestados()
        adaptador.RecyclerViewAdapterPrestamos(this, cursor)
        binding.rvPrestamos.adapter = adaptador
    }
    private fun cargaNoPrestados() {
        val cursor = dbaseHelper.obtenerNoPrestados()
        adaptador.RecyclerViewAdapterPrestamos(this, cursor)
        binding.rvPrestamos.adapter = adaptador
    }

    private fun cargaBusqueda(campo: String, campoValor: String) {
        val cursor = dbaseHelper.obtenerCursor(dbaseSQLiteHelper.TABLA_PRESTAMOS, campo,
            campoValor, campo)
        if (cursor.count ==0){
           Toast.makeText(this,"NO SE HA ENCONTRADO COINCIDENCIAS",Toast.LENGTH_SHORT).show()
            cargaPrestamos()
        }else {
            adaptador.RecyclerViewAdapterPrestamos(this, cursor)
            binding.rvPrestamos.adapter = adaptador
        }
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
                        dbaseSQLiteHelper.CAMPO_NOMBREEQUIPO.capitalize(),
                        dbaseSQLiteHelper.CAMPO_CARACTERISTICAS.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }))
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
                        (dialogo as AlertDialog).spBusca.selectedItem.toString()
                            .lowercase(Locale.getDefault()),
                        dialogo.etBusca.text.toString())
                }
                builder.setNegativeButton(android.R.string.cancel, null)
                builder.show()
                true
            }
            R.id.opPrestado -> {
                cargaSoloPrestados()
                true
            }
            R.id.opNoPrestado -> {
                cargaNoPrestados()
                true
            }

            R.id.opMostrarTodos -> {
                cargaPrestamos()
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
                cargaBusqueda(dbaseSQLiteHelper.CAMPO_FKFAMILIAEQUIPO, item.title.toString())
                true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbaseHelper.cerrarDB()
    }
}
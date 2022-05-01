package com.aaraguas.ilernaprestamos


import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.aaraguas.ilernaprestamos.databinding.ActivityAgregarBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AgregarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarBinding
    private lateinit var dbaseHelper: dbaseSQLiteHelper


    var proveedorArray = ArrayList<String>()
    var familiaArray = ArrayList<String>()
    var estados = arrayOf("NO PRESTADO", "PRESTADO")
    var lugaresArray = ArrayList<String>()
    var usuariosArray = ArrayList<String>()


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbaseHelper = dbaseSQLiteHelper(this)
        try {
            usuariosArray = dbaseHelper.cargarNombresUsuarios()
            lugaresArray = dbaseHelper.cargarNombresLugares()
            proveedorArray = dbaseHelper.cargarNombresProveedores()
            familiaArray = dbaseHelper.cargarNombresFamilias()
        }catch (e:Exception){
            Toast.makeText(this, "RELLENAR ANTES LOS DATOS DE USUARIOS", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "PROVEEDORES, LUGARES Y FAMILIA DE EQUIPOS", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }


        if (usuariosArray.isNotEmpty() && lugaresArray.isNotEmpty() &&
                proveedorArray.isNotEmpty() && familiaArray.isNotEmpty()) {

            binding = ActivityAgregarBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val adaptadorSpinnerProveedor = ArrayAdapter(
                this,
                R.layout.simple_spinner_item, proveedorArray
            )
            adaptadorSpinnerProveedor.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spUser2.adapter = adaptadorSpinnerProveedor

            val adaptadorSpinnerFamilia = ArrayAdapter(
                this,
                R.layout.simple_spinner_item, familiaArray
            )
            adaptadorSpinnerFamilia.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spFormato2.adapter = adaptadorSpinnerFamilia

            val adaptadorSpinnerEstado = ArrayAdapter(
                this,
                R.layout.simple_spinner_item, estados
            )
            adaptadorSpinnerEstado.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spEstado.adapter = adaptadorSpinnerEstado

            val adaptadorSpLugares = ArrayAdapter(
                this,
                R.layout.simple_spinner_item, lugaresArray
            )
            adaptadorSpLugares.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spLugar.adapter = adaptadorSpLugares

            val adaptadorSpUsuarios = ArrayAdapter(
                this,
                R.layout.simple_spinner_item, usuariosArray
            )
            adaptadorSpUsuarios.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spUser.adapter = adaptadorSpUsuarios

            val id = intent.getIntExtra("id", 0)

            if (id != 0) {
                val cursor = dbaseHelper.obtenerPorId(dbaseSQLiteHelper.TABLA_PRESTAMOS, id)
                binding.etEquipo.setText(cursor.getString(1))
                binding.etCaracteristicas.setText(cursor.getString(2))
                binding.tvAgregarFechaCompra.setText(cursor.getString(3))
                binding.spUser2.setSelection(
                    adaptadorSpinnerProveedor.getPosition(cursor.getString(4))
                )
                binding.spFormato2.setSelection(
                    adaptadorSpinnerFamilia.getPosition(cursor.getString(5))
                )
                binding.spEstado.setSelection(
                    adaptadorSpinnerEstado.getPosition(cursor.getString(6))
                )
                binding.tvFechaPrestamo.setText(cursor.getString(7))
                binding.spLugar.setSelection(
                    adaptadorSpLugares.getPosition(cursor.getString(8))
                )
                binding.spUser.setSelection(
                    adaptadorSpUsuarios.getPosition(cursor.getString(9))
                )


                binding.btnAceptar.setText("Modificar")
                dbaseHelper.cerrarDB()
            } else {
                true
            }


            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val fechaEscogida = Calendar.getInstance()
            val listenerFecha = DatePickerDialog.OnDateSetListener { datePicker, anyo, mes, dia ->
                fechaEscogida.clear()
                fechaEscogida.set(Calendar.YEAR, anyo)
                fechaEscogida.set(Calendar.MONTH, mes)
                fechaEscogida.set(Calendar.DAY_OF_MONTH, dia)
                binding.tvAgregarFechaCompra.text = formatter.format(fechaEscogida.time)
            }
            val listenerFechap = DatePickerDialog.OnDateSetListener { datePicker, anyo, mes, dia ->
                fechaEscogida.clear()
                fechaEscogida.set(Calendar.YEAR, anyo)
                fechaEscogida.set(Calendar.MONTH, mes)
                fechaEscogida.set(Calendar.DAY_OF_MONTH, dia)
                binding.tvFechaPrestamo.text = formatter.format(fechaEscogida.time)
            }
            binding.tvFechaPrestamo.setOnClickListener {
                DatePickerDialog(
                    this,
                    listenerFechap,
                    fechaEscogida.get(Calendar.YEAR),
                    fechaEscogida.get(Calendar.MONTH),
                    fechaEscogida.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            binding.tvAgregarFechaCompra.setOnClickListener {
                DatePickerDialog(
                    this,
                    listenerFecha,
                    fechaEscogida.get(Calendar.YEAR),
                    fechaEscogida.get(Calendar.MONTH),
                    fechaEscogida.get(Calendar.DAY_OF_MONTH)
                ).show()
            }



            binding.btnAceptar.setOnClickListener {
                if (binding.etEquipo.text.isNotBlank() &&
                    binding.etCaracteristicas.text.isNotBlank()
                ) {
                    if ((id != 0)) {
                        dbaseHelper.editarPrestamo(
                            id,
                            binding.etEquipo.text.toString(),
                            binding.etCaracteristicas.text.toString(),
                            binding.tvAgregarFechaCompra.text.toString(),
                            binding.spUser2.selectedItemPosition,
                            binding.spFormato2.selectedItemPosition,
                            checkEstado(binding.spEstado.selectedItemPosition),
                            binding.tvFechaPrestamo.text.toString(),
                            binding.spLugar.selectedItemPosition,
                            binding.spUser.selectedItemPosition
                        )
                    } else {
                        dbaseHelper.agregarPrestamo(
                            binding.etEquipo.text.toString(),
                            binding.etCaracteristicas.text.toString(),
                            binding.tvAgregarFechaCompra.text.toString(),
                            binding.spUser2.selectedItemPosition,
                            binding.spFormato2.selectedItemPosition,
                            checkEstado(binding.spEstado.selectedItemPosition),
                            binding.tvFechaPrestamo.text.toString(),
                            binding.spLugar.selectedItemPosition,
                            binding.spUser.selectedItemPosition
                        )
                    }
                    binding.etEquipo.text.clear()
                    binding.etCaracteristicas.text.clear()
                    binding.tvFechaPrestamo.text = ""
                    binding.tvAgregarFechaCompra.text = ""


                    Toast.makeText(
                        this,
                        "Guardado", Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        this,
                        "Rellenar Equipo y Caracteristicas", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }
    fun checkEstado (posicion: Int): Boolean {

        return posicion != 0

    }


}
package com.aaraguas.ilernaprestamos.Adapters


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.aaraguas.ilernaprestamos.*
import com.aaraguas.ilernaprestamos.databinding.ItemPrestamoBinding

class RecyclerViewAdapterPrestamos : RecyclerView.Adapter<RecyclerViewAdapterPrestamos.ViewHolder>() {

    lateinit var context: Context
    lateinit var cursor: Cursor

    fun RecyclerViewAdapterPrestamos(context: Context, cursor: Cursor) {
        this.context = context
        this.cursor = cursor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_prestamo, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)

        holder.id = cursor.getInt(0)
        holder.tvEquipo.text = cursor.getString(1)
        holder.tvCaracteristicas.text = cursor.getString(2)
        holder.tvEstado.text = cursor.getString(6)
        holder.tvUsuario.text = cursor.getString(9)
        holder.tvFecha.text = cursor.getString(7)
        holder.tvLugar.text = cursor.getString(8)

    }

    override fun getItemCount(): Int {
        if (cursor == null)
            return 0
        else
            return cursor.count
    }

    inner class ViewHolder: RecyclerView.ViewHolder, PopupMenu.OnMenuItemClickListener {
        var id : Int = 0

        val tvEquipo : TextView
        val tvCaracteristicas : TextView
        val tvEstado : TextView
        val tvUsuario : TextView
        val tvFecha : TextView
        val tvLugar : TextView
        var latitud: Double = 0.0
        var longitud: Double = 0.0

        constructor(view: View) : super(view) {
            val bindingItemsDisco = ItemPrestamoBinding.bind(view)

            tvEquipo = bindingItemsDisco.tvEquipoPrestamo
            tvCaracteristicas = bindingItemsDisco.tvCaract
            tvEstado = bindingItemsDisco.tvEstado
            tvUsuario = bindingItemsDisco.tvUser
            tvFecha = bindingItemsDisco.tvFecha
            tvLugar = bindingItemsDisco.tvLugar

            view.setOnClickListener{
                Toast.makeText(context,
                    "Mantenga pulsado para opciones\n ${tvEquipo.text}",
                    Toast.LENGTH_SHORT).show()
            }

            view.setOnLongClickListener {
                val popupMenu = PopupMenu(view.context, view)
                popupMenu.inflate(R.menu.menu_contextual)
                popupMenu.setOnMenuItemClickListener(this)
                popupMenu.show()
                true
            }

            tvLugar.setOnClickListener {
                val intentMapa = Intent(context, MapsActivity::class.java)
                    .apply {
                        putExtra("estudio", tvLugar.text.toString())
                        putExtra("latitud", latitud)
                        putExtra("longitud", longitud)
                    }
                context.startActivity(intentMapa)
                true
            }
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            return when (item!!.itemId) {
                R.id.opEditar -> {
                    val intentEditar = Intent(context, AgregarActivity::class.java)
                        .apply { putExtra("id", id) }
                    context.startActivity(intentEditar)
                    true
                }
                R.id.opBorrar -> {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Borrar")
                    builder.setMessage("¿Estás seguro de borrar '${tvEquipo.text}'?")
                    builder.setPositiveButton(android.R.string.ok) {
                        dialogo, which ->
                            val dbHelper = dbaseSQLiteHelper(context)
                            dbHelper.borrarDisco(id)
                            (context as MainActivity).cargaPrestamos()
                    }
                    builder.setNegativeButton(android.R.string.cancel, null)
                    builder.show()
                    true
                }
                else -> false
            }
        }


    }
}
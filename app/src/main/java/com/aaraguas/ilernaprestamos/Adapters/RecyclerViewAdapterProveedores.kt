package com.aaraguas.ilernaprestamos.Adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aaraguas.ilernaprestamos.R
import com.aaraguas.ilernaprestamos.databinding.ItemProveedoresBinding

class RecyclerViewAdapterProveedores
    : RecyclerView.Adapter<RecyclerViewAdapterProveedores.ViewHolder>(){

        lateinit var context : Context
        lateinit var cursor : Cursor

        inner class ViewHolder: RecyclerView.ViewHolder{

            val idProveedor: TextView
            val NombreProveedor: TextView
            val DireccionProveedor: TextView
            val TelefonoProveedor: TextView
            val CorreoProveedor: TextView
            val ContactoProveedor: TextView


            constructor(view: View) : super(view) {
                val bindingItemsRV = ItemProveedoresBinding.bind(view)

                idProveedor = bindingItemsRV.viewProveedorId
                NombreProveedor= bindingItemsRV.viewNombreProveedor
                DireccionProveedor= bindingItemsRV.viewDireccionProveedor
                TelefonoProveedor= bindingItemsRV.viewTelefonoProveedor
                CorreoProveedor= bindingItemsRV.viewCorreoProveedor
                ContactoProveedor= bindingItemsRV.viewContactoProveedor

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ViewHolder(inflater.inflate(R.layout.item_proveedores, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            cursor.moveToPosition(position)

            holder.idProveedor.text=cursor.getString(0)
            holder.NombreProveedor.text=cursor.getString(1)
            holder.DireccionProveedor.text=cursor.getString(2)
            holder.TelefonoProveedor.text=cursor.getString(3)
            holder.CorreoProveedor.text=cursor.getString(4)
            holder.ContactoProveedor.text=cursor.getString(5)

        }

        override fun getItemCount(): Int {
            if (cursor == null)
                return 0
            else
                return cursor.count
        }

        fun RecyclerViewAdapterProveedores(context : Context, cursor : Cursor) {
            this.context = context
            this.cursor = cursor


        }



}
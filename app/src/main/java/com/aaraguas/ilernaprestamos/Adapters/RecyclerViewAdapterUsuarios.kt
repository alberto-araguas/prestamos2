package com.aaraguas.ilernaprestamos.Adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aaraguas.ilernaprestamos.R
import com.aaraguas.ilernaprestamos.databinding.ItemUsuariosBinding


class RecyclerViewAdapterUsuarios : RecyclerView.Adapter<RecyclerViewAdapterUsuarios.ViewHolder>(){

        lateinit var context : Context
        lateinit var cursor : Cursor

        inner class ViewHolder: RecyclerView.ViewHolder{

            val idUser: TextView
            val NombreUser: TextView
            val DireccionUser: TextView
            val TelefonoUser: TextView
            val CorreoUser: TextView
            val DeptUser: TextView


            constructor(view: View) : super(view) {
                val bindingItemsRV = ItemUsuariosBinding.bind(view)

                idUser = bindingItemsRV.viewUsuarioId
                NombreUser= bindingItemsRV.viewNombreUsuario
                DireccionUser= bindingItemsRV.viewDireccionUsuario
                TelefonoUser= bindingItemsRV.viewTelefonoUsuario
                CorreoUser= bindingItemsRV.viewCorreoUsuario
                DeptUser= bindingItemsRV.viewDepartamientoUsuario
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ViewHolder(inflater.inflate(R.layout.item_usuarios, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            cursor.moveToPosition(position)

            holder.idUser.text=cursor.getString(0)
            holder.NombreUser.text=cursor.getString(1)
            holder.DireccionUser.text=cursor.getString(2)
            holder.TelefonoUser.text=cursor.getString(3)
            holder.CorreoUser.text=cursor.getString(4)
            holder.DeptUser.text=cursor.getString(5)

        }

        override fun getItemCount(): Int {
            if (cursor == null)
                return 0
            else
                return cursor.count
        }

        fun RecyclerViewAdapterUsuarios(context : Context, cursor : Cursor) {
            this.context = context
            this.cursor = cursor


        }



}
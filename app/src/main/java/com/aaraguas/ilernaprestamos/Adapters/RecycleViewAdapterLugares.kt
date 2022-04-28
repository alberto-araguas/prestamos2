package com.aaraguas.ilernaprestamos.Adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aaraguas.ilernaprestamos.R
import com.aaraguas.ilernaprestamos.databinding.ItemLugaresBinding

class RecycleViewAdapterLugares
    : RecyclerView.Adapter<RecycleViewAdapterLugares.ViewHolder>(){

    lateinit var context : Context
    lateinit var cursor : Cursor

        inner class ViewHolder: RecyclerView.ViewHolder{

            val idLugar: TextView
            val NombreLugar: TextView
            val DireccionLugar: TextView
            val TelefonoLugar: TextView
            val CorreoLugar: TextView


            constructor(view: View) : super(view) {
                val bindingItemsRV = ItemLugaresBinding.bind(view)

                idLugar = bindingItemsRV.viewLugarId
                NombreLugar= bindingItemsRV.viewNombreLugar
                DireccionLugar= bindingItemsRV.viewDireccionLugar
                TelefonoLugar= bindingItemsRV.viewTelefonoLugar
                CorreoLugar= bindingItemsRV.viewCorreoLugar

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_lugares, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)

        holder.idLugar.text=cursor.getString(0)
        holder.NombreLugar.text=cursor.getString(1)
        holder.DireccionLugar.text=cursor.getString(2)
        holder.TelefonoLugar.text=cursor.getString(3)
        holder.CorreoLugar.text=cursor.getString(4)

    }

    override fun getItemCount(): Int {
       if (cursor == null)
           return 0
        else
            return cursor.count
    }

    fun RecycleViewAdapterLugares(context : Context, cursor : Cursor) {
        this.context = context
        this.cursor = cursor


    }
}
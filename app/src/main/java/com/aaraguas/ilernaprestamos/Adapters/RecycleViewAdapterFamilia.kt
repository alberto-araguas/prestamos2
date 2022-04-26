package com.aaraguas.ilernaprestamos.Adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aaraguas.ilernaprestamos.R
import com.aaraguas.ilernaprestamos.databinding.ItemFamiliasBinding

class RecycleViewAdapterFamilia
    : RecyclerView.Adapter<RecycleViewAdapterFamilia.ViewHolder>(){

    lateinit var context : Context
    lateinit var cursor : Cursor

        inner class ViewHolder: RecyclerView.ViewHolder{

            val idFamilia: TextView
            val NombreFam: TextView


            constructor(view: View) : super(view) {
                val bindingItemsRV = ItemFamiliasBinding.bind(view)

                idFamilia = bindingItemsRV.viewFamiliaId
                NombreFam= bindingItemsRV.viewNombreFamilia
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_familias, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)

        holder.idFamilia.text=cursor.getString(0)
        holder.NombreFam.text=cursor.getString(1)
    }

    override fun getItemCount(): Int {
       if (cursor == null)
           return 0
        else
            return cursor.count
    }

    fun RecycleViewAdapterFamilia(context : Context, cursor : Cursor) {
        this.context = context
        this.cursor = cursor


    }
}
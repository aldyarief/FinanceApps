package com.example.financeapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.financeapps.R
import com.example.financeapps.data.TransaksinyaItem
import kotlinx.android.synthetic.main.item_dashboardmasuk.view.*
import java.text.NumberFormat
import java.util.*

class DashMasuk(var data: List<TransaksinyaItem?>?,
                private val deleteClick : OnDeleteTransClickListener) : RecyclerView.Adapter<DashMasuk.MasukHolder>()  {
    class MasukHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemJumlah = itemView.itemJml
        val itemKategori = itemView.itemKategori
        val itemTransid = itemView.itemTransid
        val menudelete = itemView.menudelete


        fun initialize(item: TransaksinyaItem?, action: OnDeleteTransClickListener) {
            menudelete.setOnClickListener{
                action.onDelete(item,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashMasuk.MasukHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboardmasuk, parent,false)
        val holder = MasukHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: DashMasuk.MasukHolder, position: Int) {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID )
        val jumlahnya = data?.get(position)?.jml!!.toInt()
        holder.itemJumlah.text = "${formatRupiah.format(jumlahnya)} (I)"
        holder.itemKategori.text ="${data?.get(position)?.tgl} - ${data?.get(position)?.namkat}"
        holder.itemTransid.text = data?.get(position)?.transId
        holder.initialize(data?.get(position),deleteClick)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}

interface OnDeleteTransClickListener {
    fun onDelete(item: TransaksinyaItem?, position: Int)
}
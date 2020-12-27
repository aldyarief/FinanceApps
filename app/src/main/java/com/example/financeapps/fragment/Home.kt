package com.example.financeapps.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.financeapps.R
import com.example.financeapps.adapter.DashKeluar
import com.example.financeapps.adapter.DashMasuk
import com.example.financeapps.adapter.OnDeleteKeluarClickListener
import com.example.financeapps.adapter.OnDeleteTransClickListener
import com.example.financeapps.data.ShowEarnings
import com.example.financeapps.data.ShowTrans
import com.example.financeapps.data.TransaksinyaItem
import com.example.financeapps.network.ConfigNetwork
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*


class Home : Fragment(), OnDeleteTransClickListener,OnDeleteKeluarClickListener {
    var server : String? = null
    var action : String? = null
    var total : TextView? = null
    var hasil : Int? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        server = "http://aldry.agustianra.my.id/"
        val fragmentview = inflater.inflate(R.layout.fragment_home, container, false);
        total = fragmentview.findViewById(R.id.total) as TextView;
        AmbilData()
        TransMasuk()
        TransKeluar()

        return fragmentview

    }

    fun AmbilData() {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        ConfigNetwork.getRetrofit(server!!).getTotalEarnings().enqueue(object :
            Callback<ShowEarnings> {

            override fun onResponse(call: Call<ShowEarnings>, response: Response<ShowEarnings>) {
                Log.d("response server", response.message())

                if (response.isSuccessful) {
                    val datanya = response.body()?.total
                    hasil = datanya!!.toInt()
                    total!!.text = "Total Keuntungan : ${formatRupiah.format(hasil)}"
                }
            }

            override fun onFailure(call: Call<ShowEarnings>, t: Throwable) {
                Log.d("response server", t.message!!)
            }
        })

    }

    private fun showData(datatrans: List<TransaksinyaItem?>?) {
        transmasuk.adapter = DashMasuk(datatrans,this)
    }

    fun TransMasuk() {
        action="masuk"
        ConfigNetwork.getRetrofit(server!!).getTrans(action!!).enqueue(object : Callback<com.example.financeapps.data.ShowTrans> {

            override fun onResponse(call: Call<ShowTrans>, response: Response<ShowTrans>) {
                Log.d("response server", response.message())

                if (response.isSuccessful){

                    val datakategori = response.body()?.transaksinya

                    showData(datakategori)
                }
            }

            override fun onFailure(call: Call<ShowTrans>, t: Throwable) {
                Log.d("response server", t.message!!)
            }
        })

    }
    private fun DataShow(datatrans: List<TransaksinyaItem?>?) {
        transkeluar.adapter = DashKeluar(datatrans,this)
    }

    fun TransKeluar() {
        action="keluar"
        ConfigNetwork.getRetrofit(server!!).getTrans(action!!).enqueue(object : Callback<com.example.financeapps.data.ShowTrans> {

            override fun onResponse(call: Call<ShowTrans>, response: Response<ShowTrans>) {
                Log.d("response server", response.message())

                if (response.isSuccessful){

                    val datakategori = response.body()?.transaksinya

                    DataShow(datakategori)
                }
            }

            override fun onFailure(call: Call<ShowTrans>, t: Throwable) {
                Log.d("response server", t.message!!)
            }
        })

    }


    companion object {
        fun newInstance(): Home{
            val fragment = Home()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onDelete(item: TransaksinyaItem?, position: Int) {
        TODO("Not yet implemented")
    }
}
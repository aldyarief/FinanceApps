package com.example.financeapps.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.financeapps.R
import com.example.financeapps.data.CrudKategori
import com.example.financeapps.network.ConfigNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Kategori : Fragment() {

    var server : String? = null
    var edkat: EditText? = null
    var btnSimpan : Button? = null
    var btnClose : Button? = null
    var btnShow : Button? = null
    var action : String? = null
    var income : RadioButton? = null
    var expenses : RadioButton? = null
    var kattrans : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        server = "http://aldry.agustianra.my.id/"
        val fragmentview = inflater.inflate(R.layout.fragment_kategori, container, false);
        server = "http://aldry.agustianra.my.id/"
        edkat = fragmentview.findViewById(R.id.namkat) as EditText
        btnSimpan = fragmentview.findViewById(R.id.SimpanBtn) as Button
        btnClose = fragmentview.findViewById(R.id.CloseBtn) as Button
        btnShow = fragmentview.findViewById(R.id.Showbtn) as Button
        income = fragmentview.findViewById(R.id.income) as RadioButton
        expenses = fragmentview.findViewById(R.id.expenses) as RadioButton
        val namakat: String? = getActivity()?.getIntent()?.getStringExtra("namakat")
        val idkat: String? = getActivity()?.getIntent()?.getStringExtra("idkat")
        action = getActivity()?.getIntent()?.getStringExtra("action")

        if (action.equals("editdata")) {
            action="editdata"

            if (idkat.equals("1") ){
                income!!.isChecked = true
                expenses!!.isChecked = false
            } else {
                expenses!!.isChecked = true
                income!!.isChecked = false
            }
            edkat!!.setText(namakat)

        } else {
            action=""
        }

        btnSimpan!!.setOnClickListener {
            val namkat = edkat!!.text.toString().trim { it <= ' ' }

            if (income!!.isChecked) {
                kattrans = 1.toString()
            } else if (expenses!!.isChecked) {
                kattrans = 2.toString()
            } else {
                Toast.makeText(getActivity(), "Akun tidak dipilih", Toast.LENGTH_SHORT).show()
                income!!.requestFocus()
            }

            val katid="-"
            if (action.equals("")) {
                action = "insertdata"
            }

            if (action.equals("insertdata")) {

                ConfigNetwork.getRetrofit(server!!).getInsertKat(action!!, namkat!!, kattrans!!, katid!!).enqueue(object :
                    Callback<com.example.financeapps.data.CrudKategori> {
                    override fun onResponse(call: Call<CrudKategori>, response: Response<CrudKategori>) {
                        Log.d("response server", response.message())

                        if (response.isSuccessful) {
                            val hasilnya = response.body()?.pesan
                            Toast.makeText(getActivity(), hasilnya, Toast.LENGTH_SHORT).show()
                            edkat!!.getText().clear()
                            action = ""

                        }
                    }

                    override fun onFailure(call: Call<CrudKategori>, t: Throwable) {
                        Log.d("response server", t.message!!)
                    }

                })
            } else if (action.equals("editdata")) {
                val namkat = edkat!!.text.toString().trim { it <= ' ' }
                if (income!!.isChecked) {
                    kattrans = 1.toString()
                } else if (expenses!!.isChecked) {
                    kattrans = 2.toString()
                } else {
                    Toast.makeText(getActivity(), "Akun tidak dipilih atau dipilih lebih dari satu!", Toast.LENGTH_SHORT).show()
                    income!!.requestFocus()
                }
                val katid=idkat
                ConfigNetwork.getRetrofit(server!!).getEditKat(action!!, namkat!!, kattrans!!, katid!!).enqueue(object :
                    Callback<com.example.financeapps.data.CrudKategori> {
                    override fun onResponse(call: Call<CrudKategori>, response: Response<CrudKategori>) {
                        Log.d("response server", response.message())

                        if (response.isSuccessful) {
                            val hasilnya = response.body()?.pesan
                            Toast.makeText(getActivity(), hasilnya, Toast.LENGTH_SHORT).show()
                            edkat!!.getText().clear()
                            action = ""

                        }
                    }

                    override fun onFailure(call: Call<CrudKategori>, t: Throwable) {
                        Log.d("response server", t.message!!)
                    }

                })
            }
        }

        return fragmentview

    }

    companion object {
        fun newInstance(): Kategori{
            val fragment = Kategori()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
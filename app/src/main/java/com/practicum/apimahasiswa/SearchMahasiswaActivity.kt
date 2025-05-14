package com.practicum.apimahasiswa

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.practicum.apimahasiswa.api.ApiConfig.apiService
import com.practicum.apimahasiswa.model.Mahasiswa
import com.practicum.apimahasiswa.model.MahasiswaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMahasiswaActivity : AppCompatActivity() {
    private lateinit var edtChecNrp: EditText
    private lateinit var btnSearch: Button
    private var progressBar: ProgressBar? = null
    private var tvNrp: TextView? = null
    private var tvNama: TextView? = null
    private var tvEmail: TextView? = null
    private var tvJurusan: TextView? = null
    private var mahasiswaList: List<Mahasiswa>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_mahasiswa)
        edtChecNrp = findViewById(R.id.edtChckNrp)
        btnSearch = findViewById(R.id.btnSearch)
        progressBar = findViewById(R.id.progressBar)
        tvNrp = findViewById(R.id.tvValNrp)
        tvNama = findViewById(R.id.tvValNama)
        tvEmail = findViewById(R.id.tvValEmail)
        tvJurusan = findViewById(R.id.tvValJurusan)
        mahasiswaList = ArrayList()
        btnSearch.setOnClickListener(View.OnClickListener { view: View? ->
            showLoading(true)
            val nrp = edtChecNrp.getText().toString()
            if (nrp.isEmpty()) {
                edtChecNrp.setError("Silahakan Isi nrp terlebih dahulu")
                showLoading(false)
            } else {
                val client = apiService.getMahasiswa(nrp)
                client?.enqueue(object : Callback<MahasiswaResponse?> {
                    override fun onResponse(call: Call<MahasiswaResponse?>, response: Response<MahasiswaResponse?>) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                showLoading(false)
                                mahasiswaList =

                                    response.body()!!.data
                                setData(mahasiswaList)
                            }
                        } else {
                            if (response.body() != null) {
                                Log.e("", "onFailure: " + response.message())
                            }
                        }
                    }

                    override fun onFailure(call: Call<MahasiswaResponse?>, t: Throwable) {
                        showLoading(false)
                        Log.e("Error Retrofit", "onFailure: " + t.message)
                    }
                })
            }
        })
    }
    private fun setData(mahasiswaList: List<Mahasiswa>?)
    {
        tvNrp!!.text = mahasiswaList!![0].nrp
        tvNama!!.text = mahasiswaList[0].nama
        tvEmail!!.text = mahasiswaList[0].email
        tvJurusan!!.text = mahasiswaList[0].jurusan
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar!!.visibility = View.VISIBLE
        } else {
            progressBar!!.visibility = View.GONE
        }
    }
}
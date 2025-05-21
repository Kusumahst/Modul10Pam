package com.practicum.apimahasiswa

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.apimahasiswa.api.ApiConfig.apiService
import com.practicum.apimahasiswa.model.Mahasiswa
import com.practicum.apimahasiswa.model.MahasiswaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMahasiswaActivity : AppCompatActivity() {

    private lateinit var edtCheckNrp: EditText
    private lateinit var btnSearch: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvNrp: TextView
    private lateinit var tvNama: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvJurusan: TextView
    private lateinit var cardSearchResult: CardView
    private lateinit var rvMahasiswa: RecyclerView
    private lateinit var mahasiswaAdapter: MahasiswaAdapter
    private var mahasiswaList: List<Mahasiswa> = ArrayList()

    companion object {
        private const val TAG = "SearchMahasiswaActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_mahasiswa)

        // Inisialisasi view
        edtCheckNrp = findViewById(R.id.edtChckNrp)
        btnSearch = findViewById(R.id.btnSearch)
        progressBar = findViewById(R.id.progressBar)
        tvNrp = findViewById(R.id.tvValNrp)
        tvNama = findViewById(R.id.tvValNama)
        tvEmail = findViewById(R.id.tvValEmail)
        tvJurusan = findViewById(R.id.tvValJurusan)
        cardSearchResult = findViewById(R.id.cardSearchResult)
        rvMahasiswa = findViewById(R.id.rvMahasiswa)

        // Setup RecyclerView
        mahasiswaAdapter = MahasiswaAdapter(mahasiswaList)
        rvMahasiswa.layoutManager = LinearLayoutManager(this)
        rvMahasiswa.adapter = mahasiswaAdapter

        // Load semua data mahasiswa di awal
        loadAllMahasiswa()

        btnSearch.setOnClickListener {
            val nrp = edtCheckNrp.text.toString().trim()
            if (nrp.isEmpty()) {
                edtCheckNrp.error = "Silakan isi NRP terlebih dahulu"
                return@setOnClickListener
            }
            searchMahasiswa(nrp)
        }
    }

    private fun searchMahasiswa(nrp: String) {
        showLoading(true)
        apiService.getMahasiswa(nrp)?.enqueue(object : Callback<MahasiswaResponse?> {
            override fun onResponse(call: Call<MahasiswaResponse?>, response: Response<MahasiswaResponse?>) {
                showLoading(false)
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()?.data
                    if (!data.isNullOrEmpty()) {
                        setData(data[0])
                        cardSearchResult.visibility = View.VISIBLE
                    } else {
                        cardSearchResult.visibility = View.GONE
                        Toast.makeText(this@SearchMahasiswaActivity, "Mahasiswa tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e(TAG, "Response gagal: ${response.message()}")
                    Toast.makeText(this@SearchMahasiswaActivity, "Gagal mendapatkan data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MahasiswaResponse?>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "Error Retrofit: ${t.message}")
                Toast.makeText(this@SearchMahasiswaActivity, "Koneksi gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadAllMahasiswa() {
        showLoading(true)
        apiService.getAllMahasiswa()?.enqueue(object : Callback<MahasiswaResponse?> {
            override fun onResponse(call: Call<MahasiswaResponse?>, response: Response<MahasiswaResponse?>) {
                showLoading(false)
                if (response.isSuccessful && response.body() != null) {
                    mahasiswaList = response.body()!!.data ?: ArrayList()
                    mahasiswaAdapter = MahasiswaAdapter(mahasiswaList)
                    rvMahasiswa.adapter = mahasiswaAdapter
                } else {
                    Toast.makeText(this@SearchMahasiswaActivity, "Gagal memuat data mahasiswa", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MahasiswaResponse?>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@SearchMahasiswaActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setData(mahasiswa: Mahasiswa) {
        tvNrp.text = mahasiswa.nrp
        tvNama.text = mahasiswa.nama
        tvEmail.text = mahasiswa.email
        tvJurusan.text = mahasiswa.jurusan
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

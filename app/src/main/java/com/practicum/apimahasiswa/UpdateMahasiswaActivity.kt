package com.practicum.apimahasiswa

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.practicum.apimahasiswa.api.ApiConfig.apiService
import com.practicum.apimahasiswa.model.AddMahasiswaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateMahasiswaActivity : AppCompatActivity() {

    private lateinit var edtNrp: EditText
    private lateinit var edtNama: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtJurusan: EditText
    private lateinit var btnUpdate: Button
    private lateinit var progressBar: ProgressBar
    private var mahasiswaId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_mahasiswa)

        edtNrp = findViewById(R.id.edtNrpUpdate)
        edtNama = findViewById(R.id.edtNamaUpdate)
        edtEmail = findViewById(R.id.edtEmailUpdate)
        edtJurusan = findViewById(R.id.edtJurusanUpdate)
        btnUpdate = findViewById(R.id.btnUpdate)
        progressBar = findViewById(R.id.progressBar)

        mahasiswaId = intent.getStringExtra("mahasiswa_id")
        edtNrp.setText(intent.getStringExtra("nrp"))
        edtNama.setText(intent.getStringExtra("nama"))
        edtEmail.setText(intent.getStringExtra("email"))
        edtJurusan.setText(intent.getStringExtra("jurusan"))

        btnUpdate.setOnClickListener {
            updateMahasiswa()
        }
    }

    private fun updateMahasiswa() {
        showLoading(true)
        val nrp = edtNrp.text.toString()
        val nama = edtNama.text.toString()
        val email = edtEmail.text.toString()
        val jurusan = edtJurusan.text.toString()

        if (mahasiswaId.isNullOrEmpty()) {
            Toast.makeText(this, "ID tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        val call = apiService.updateMahasiswa(mahasiswaId!!, nrp, nama, email, jurusan)
        call.enqueue(object : Callback<AddMahasiswaResponse> {
            override fun onResponse(call: Call<AddMahasiswaResponse>, response: Response<AddMahasiswaResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    Toast.makeText(this@UpdateMahasiswaActivity, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@UpdateMahasiswaActivity, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddMahasiswaResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@UpdateMahasiswaActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

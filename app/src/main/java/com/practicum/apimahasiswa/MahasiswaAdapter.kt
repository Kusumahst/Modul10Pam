package com.practicum.apimahasiswa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practicum.apimahasiswa.model.Mahasiswa

class MahasiswaAdapter(
    private val listMahasiswa: List<Mahasiswa>,
    private val onEditClick: (Mahasiswa) -> Unit,
    private val onDeleteClick: (Mahasiswa) -> Unit
) : RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder>() {

    inner class MahasiswaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNrp: TextView = itemView.findViewById(R.id.tvItemNrp)
        val tvNama: TextView = itemView.findViewById(R.id.tvItemNama)
        val tvJurusan: TextView = itemView.findViewById(R.id.tvItemJurusan)
        val tvEmail: TextView = itemView.findViewById(R.id.tvItemEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mahasiswa, parent, false)
        return MahasiswaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MahasiswaViewHolder, position: Int) {
        val mahasiswa = listMahasiswa[position]
        holder.tvNrp.text = "NRP: ${mahasiswa.nrp}"
        holder.tvNama.text = "Nama: ${mahasiswa.nama}"
        holder.tvJurusan.text = "Jurusan: ${mahasiswa.jurusan}"
        holder.tvEmail.text = "Email: ${mahasiswa.email}"

        holder.itemView.findViewById<Button>(R.id.btnEdit).setOnClickListener {
            onEditClick(mahasiswa)
        }
        holder.itemView.findViewById<Button>(R.id.btnDelete).setOnClickListener {
            onDeleteClick(mahasiswa)
        }
    }

    override fun getItemCount(): Int = listMahasiswa.size
}
package com.example.nativo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class EstadoListAdapter : ListAdapter<EstadoIBGE, EstadoListAdapter.EstadoViewHolder>(EstadoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_estado, parent, false)
        return EstadoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstadoViewHolder, position: Int) {
        val estado = getItem(position)
        holder.bind(estado)
        // Aqui você pode adicionar um onItemClicked lambda se precisar de navegação
        // a partir desta lista no futuro.
    }

    class EstadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.estadoNomeTextView)
        private val siglaTextView: TextView = itemView.findViewById(R.id.estadoSiglaTextView)

        fun bind(estado: EstadoIBGE) {
            nomeTextView.text = estado.nome
            siglaTextView.text = estado.sigla
        }
    }
}

class EstadoDiffCallback : DiffUtil.ItemCallback<EstadoIBGE>() {
    override fun areItemsTheSame(oldItem: EstadoIBGE, newItem: EstadoIBGE): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EstadoIBGE, newItem: EstadoIBGE): Boolean {
        return oldItem == newItem
    }
}
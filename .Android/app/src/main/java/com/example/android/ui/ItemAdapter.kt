package com.example.android.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.data.Item

/**
 * Adapter para a RecyclerView que exibe a lista de itens.
 *
 * @param onItemClicked Lambda que será executado quando um item da lista for clicado.
 */
class ItemAdapter(private val onItemClicked: (Item) -> Unit) :
    ListAdapter<Item, ItemAdapter.ItemViewHolder>(DiffCallback) {

    /**
     * ViewHolder descreve a view de um item e os metadados sobre sua posição na RecyclerView.
     */
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.item_name)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.item_description)

        fun bind(item: Item) {
            nameTextView.text = item.name
            descriptionTextView.text = item.description
        }
    }

    /**
     * Chamado quando a RecyclerView precisa de um novo ViewHolder para representar um item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(view)
    }

    /**
     * Chamado pela RecyclerView para exibir os dados na posição especificada.
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        // Define o OnClickListener para o item inteiro
        holder.itemView.setOnClickListener {
            onItemClicked(currentItem)
        }
        holder.bind(currentItem)
    }

    /**
     * DiffUtil calcula a diferença entre duas listas e fornece uma lista de operações
     * de atualização para o ListAdapter, o que torna a atualização da lista muito eficiente.
     */
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}

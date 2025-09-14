package com.example.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

// ou o pacote onde ele estiver

/**
 * Fragmento que exibe os detalhes de um único item.
 */
class ItemDetailFragment : Fragment() {

    // 1. RECEBENDO OS ARGUMENTOS DE FORMA SEGURA
    // Delega a inicialização dos argumentos para a biblioteca de navegação.
    // Isso garante que os argumentos definidos no nav_graph sejam recebidos corretamente.
    private val args: ItemDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla (cria) o layout para este fragmento.
        return inflater.inflate(R.layout.fragment_item_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. ENCONTRANDO AS VIEWS NO LAYOUT
        val nameTextView: TextView = view.findViewById(R.id.detail_item_name)
        val descriptionTextView: TextView = view.findViewById(R.id.detail_item_description)

        // 3. PREENCHENDO AS VIEWS COM OS DADOS RECEBIDOS
        // Acessa os argumentos recebidos da tela anterior (através de 'args')
        // e define o texto das TextViews.
        nameTextView.text = args.itemName
        descriptionTextView.text = args.itemDescription
    }
}
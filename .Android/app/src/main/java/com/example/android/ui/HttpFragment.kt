package com.example.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.android.R // Importante para referenciar o layout

/**
 * Fragmento responsável por buscar dados via HTTP e exibi-los.
 * Este é o "cérebro" que controla o layout definido em "fragment_http.xml".
 */
class HttpFragment : Fragment() {

    // Declaração das Views do layout para uso posterior
    private lateinit var municipiosTextView: TextView
    private lateinit var progressBar: ProgressBar

    /**
     * Este método é chamado para criar a view do fragmento.
     * É aqui que conectamos este código Kotlin com o layout XML.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla (cria) o layout definido em "fragment_http.xml"
        val view = inflater.inflate(R.layout.fragment_http, container, false)
        return view
    }

    /**
     * Este método é chamado logo após a view ter sido criada.
     * É o local ideal para encontrar as views pelo ID e configurar listeners.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Encontra as views no layout para que possamos manipulá-las
        municipiosTextView = view.findViewById(R.id.text_view_municipios)
        progressBar = view.findViewById(R.id.progress_bar)

        // TODO: Aqui virá a lógica para chamar a API com Retrofit no futuro.
        // Por enquanto, apenas exibimos uma mensagem padrão.
        municipiosTextView.text = "Tela HTTP pronta para buscar dados."
    }
}

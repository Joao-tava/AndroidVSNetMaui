package com.example.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.databinding.FragmentHomeBinding // 1. Importar a classe de ViewBinding

class HomeFragment : Fragment() {

    // 2. Declarar a variável para o binding (segura contra memory leaks)
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 3. Inflar o layout usando ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 4. Configurar o clique do botão (ou outro gatilho)
        // O ID 'button_navigate' é um exemplo. Troque pelo ID real do seu botão no 'fragment_home.xml'
        binding.buttonNavigate.setOnClickListener {
            // 5. A navegação acontece SOMENTE AQUI, quando o usuário clica.
            // A classe 'HomeFragmentDirections' agora existe porque o build está correto.
            val action = HomeFragmentDirections.actionNavigationHomeToItemListFragment()
            findNavController().navigate(action)
        }
    }

    // 6. Limpar a referência do binding para evitar vazamento de memória
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

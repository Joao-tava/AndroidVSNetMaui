package com.example.android.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.data.Item
import com.example.android.data.ItemListViewModel
import com.example.android.databinding.FragmentItemListBinding // 1. Importar a classe de ViewBinding

class ItemListFragment : Fragment() {

    // 2. Declarar a variável para o binding (será inicializada no onCreateView)
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemListViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[ItemListViewModel::class.java]
    }

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 3. Inflar o layout usando ViewBinding em vez de findViewById
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupAdapter() {
        itemAdapter = ItemAdapter { clickedItem ->
            navigateToDetail(clickedItem)
        }
    }

    private fun setupRecyclerView() {
        // 4. Acessar a RecyclerView de forma segura através do binding
        binding.recyclerView.adapter = itemAdapter
    }

    private fun setupListeners() {
        // Acessar o botão e o campo de texto através do binding
        binding.buttonToHttpScreen.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToHttpFragment()
            findNavController().navigate(action)
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchQuery(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeViewModel() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            // A lógica de submissão da lista permanece a mesma
            items?.let {
                itemAdapter.submitList(it)
            }
        }
    }

    private fun navigateToDetail(item: Item) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(
            itemId = item.id,
            itemName = item.name,
            itemDescription = item.description
        )
        findNavController().navigate(action)
    }

    // 5. Limpar a referência do binding para evitar vazamentos de memória
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

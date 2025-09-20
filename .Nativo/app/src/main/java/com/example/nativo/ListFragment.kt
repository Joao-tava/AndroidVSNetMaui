package com.example.nativo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button // Importação do Button adicionada
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    private val listViewModel: ListViewModel by viewModels()
    private lateinit var itemsAdapter: ItemListAdapter
    private lateinit var searchEditText: EditText
    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var buttonNavigateToIbge: Button // Declaração do botão

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        searchEditText = view.findViewById(R.id.searchEditText)
        itemsRecyclerView = view.findViewById(R.id.itemsRecyclerView)
        buttonNavigateToIbge = view.findViewById(R.id.buttonNavigateToIbge) // Inicialização do botão
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearch()
        setupNavigationButton()

        listViewModel.filteredItems.observe(viewLifecycleOwner) { items ->
            itemsAdapter.submitList(items)
        }
    }

    private fun setupRecyclerView() {
        itemsAdapter = ItemListAdapter { selectedItem ->
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(selectedItem)
            findNavController().navigate(action)
        }
        itemsRecyclerView.apply {
            adapter = itemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listViewModel.filterItems(s?.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupNavigationButton() {
        buttonNavigateToIbge.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToIbgeFragment()
            findNavController().navigate(action)
        }
    }
}

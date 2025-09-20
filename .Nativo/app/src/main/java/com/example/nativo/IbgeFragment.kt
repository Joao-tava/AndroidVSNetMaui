package com.example.nativo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class IbgeFragment : Fragment() {

    private val viewModel: IbgeViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private lateinit var adapter: EstadoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ibge, container, false)
        recyclerView = view.findViewById(R.id.ibgeRecyclerView)
        progressBar = view.findViewById(R.id.ibgeProgressBar)
        errorTextView = view.findViewById(R.id.ibgeErrorTextView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = EstadoListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.isVisible = isLoading
            recyclerView.isVisible = !isLoading && viewModel.errorMessage.value.isNullOrEmpty()
            errorTextView.isVisible = !isLoading && !viewModel.errorMessage.value.isNullOrEmpty()
        }

        viewModel.estados.observe(viewLifecycleOwner) { estados ->
            adapter.submitList(estados)
            recyclerView.isVisible = !viewModel.isLoading.value!! && viewModel.errorMessage.value.isNullOrEmpty()
            errorTextView.isVisible = !viewModel.isLoading.value!! && !viewModel.errorMessage.value.isNullOrEmpty() && estados.isNullOrEmpty()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorTextView.text = errorMessage
            errorTextView.isVisible = !viewModel.isLoading.value!! && !errorMessage.isNullOrEmpty()
            recyclerView.isVisible = !viewModel.isLoading.value!! && errorMessage.isNullOrEmpty()
        }
    }
}
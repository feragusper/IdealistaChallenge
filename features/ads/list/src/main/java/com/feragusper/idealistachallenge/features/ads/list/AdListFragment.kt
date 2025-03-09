package com.feragusper.idealistachallenge.features.ads.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.feragusper.idealistachallenge.features.ads.databinding.FragmentAdListBinding
import com.feragusper.idealistachallenge.libraries.ads.presentation.AdListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Fragment displaying a list of ads.
 */
@AndroidEntryPoint
class AdListFragment : Fragment() {

    private var _binding: FragmentAdListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdListViewModel by viewModels()
    private lateinit var adListAdapter: AdListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adListAdapter = AdListAdapter(
            ads = emptyList(),
            onClick = {
            },
            onFavoriteClick = { adId ->
                viewModel.toggleFavorite(adId)
            }
        )

        binding.recyclerAds.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adListAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { ads ->
                        adListAdapter.updateAds(ads)
                    }
                }

                launch {
                    viewModel.isLoading.collect { isLoading ->
                        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                    }
                }

                launch {
                    viewModel.error.collect { errorMessage ->
                        errorMessage?.let { showError(it) }
                    }
                }
            }
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
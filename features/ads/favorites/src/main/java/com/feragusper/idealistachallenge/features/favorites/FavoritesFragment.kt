package com.feragusper.idealistachallenge.features.favorites

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
import com.feragusper.idealistachallenge.features.favorites.databinding.FragmentFavoritesBinding
import com.feragusper.idealistachallenge.libraries.ads.presentation.AdListAdapter
import com.feragusper.idealistachallenge.libraries.ads.presentation.AdSummaryUi
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("ViewBinding is null")

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adsListAdapter: AdListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adsListAdapter = AdListAdapter(
            ads = emptyList(),
            onClick = {
            },
            onFavoriteClick = viewModel::toggleFavorite
        )

        binding.recyclerAds.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adsListAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collectLatest { updateUI(it) }
                }
                launch {
                    viewModel.isLoading.collectLatest {
                        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
                    }
                }
                launch {
                    viewModel.error.collectLatest { it?.let { handleError(it) } }
                }
            }
        }
    }

    private fun updateUI(ads: List<AdSummaryUi>) {
        binding.apply {
            val hasAds = ads.isNotEmpty()
            recyclerAds.visibility = if (hasAds) View.VISIBLE else View.GONE
            emptyStateContainer.visibility = if (hasAds) View.GONE else View.VISIBLE
            adsListAdapter.updateAds(ads)
        }
    }

    private fun handleError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

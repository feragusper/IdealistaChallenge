package com.feragusper.idealistachallenge.features.detail.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.feragusper.idealistachallenge.features.detail.presentation.databinding.FragmentAdDetailBinding
import com.feragusper.idealistachallenge.features.detail.presentation.ui.AdDetailUi
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdDetailFragment : Fragment() {

    private var _binding: FragmentAdDetailBinding? = null
    private val binding get() = _binding ?: error("Trying to access binding when view is null")
    private val viewModel: AdDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        observeViewModel()

        binding.favoriteButton.setOnClickListener { _ ->
            viewModel.toggleFavorite()
        }
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.apply {
            supportActionBar?.title = null
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { ad ->
                        ad?.let { updateUI(it) }
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

    private fun updateUI(ad: AdDetailUi) {
        binding.apply {
            context?.let {
                with(ad) {
                    textPrice.text = price
                    textTitle.text = title.format(it)
                    textSubtitle.text = subtitle.format(it)
                    textRooms.text = rooms.format(it)
                    textSize.text = size.format(it)
                    textBathrooms.text = bathrooms.format(it)
                    textDescription.text = description
                    textCharacteristics.text = characteristics.format(it)
                    textBuilding.text = building.format(it)
                    textEnergyCertification.text = energyCertification.format(it)
                    imageCarousel.images = images
                    textTotalPrice.text = totalPrice
                    textPricePerSquareMeter.text = pricePerSquareMeter.format(it)
                    textModificationDate.text = modificationDate.format(it)
                    favoriteButton.isChecked = isFavorite
                    setupFavoriteDate(favoriteDate?.format(it))
                    setupDescriptionExpansion(description.length)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setupScrollListener(title.format(it), price)
                    }
                }
            }
        }
    }

    private fun setupDescriptionExpansion(descriptionLength: Int) {
        with(binding) {
            val isExpandable = descriptionLength > 100
            btnExpandDescription.visibility = if (isExpandable) {
                View.VISIBLE
            } else {
                View.GONE
            }

            if (isExpandable) {
                var isExpanded = false
                val originalMaxLines = 5

                btnExpandDescription.setOnClickListener {
                    isExpanded = !isExpanded
                    textDescription.maxLines = if (isExpanded) {
                        Int.MAX_VALUE
                    } else {
                        originalMaxLines
                    }
                    btnExpandDescription.text = getString(
                        if (isExpanded) {
                            R.string.view_less
                        } else {
                            R.string.view_more
                        }
                    )
                }
            }
        }
    }

    private fun setupFavoriteDate(favoriteDate: String?) {
        binding.favoriteDate.apply {
            text = favoriteDate
            visibility = if (favoriteDate != null) View.VISIBLE else View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupScrollListener(title: String, subtitle: String) {
        binding.scrollView.post {
            (activity as? AppCompatActivity)?.supportActionBar?.title = title

            binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                val threshold = binding.textPrice.bottom
                (activity as? AppCompatActivity)?.supportActionBar?.apply {
                    this.subtitle = if (scrollY > threshold) subtitle else null
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
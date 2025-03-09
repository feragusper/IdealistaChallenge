package com.feragusper.idealistachallenge.features.ads.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.domain.usecase.GetAdsListUseCase
import com.feragusper.idealistachallenge.libraries.ads.domain.usecase.ToggleFavoriteUseCase
import com.feragusper.idealistachallenge.libraries.ads.presentation.AdSummaryUi
import com.feragusper.idealistachallenge.libraries.ads.presentation.mapper.AdSummaryUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Ad List screen.
 *
 * This ViewModel is responsible for managing the data and business logic for the Ad List screen.
 *
 * @param getAdsListUseCase Use case to fetch the list of ads.
 * @param adSummaryUiMapper Mapper to convert domain models to UI models.
 * @property toggleFavoriteUseCase Use case to toggle the favorite status of an ad.
 */
@HiltViewModel
class AdListViewModel @Inject constructor(
    getAdsListUseCase: GetAdsListUseCase,
    adSummaryUiMapper: AdSummaryUiMapper,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    /**
     * State flow representing whether the screen is loading.
     */
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)

    /**
     * State flow representing the error message.
     */
    val error: StateFlow<String?> = _error

    /**
     * State flow representing the list of ads.
     */
    val uiState: StateFlow<List<AdSummaryUi>> = getAdsListUseCase()
        .onStart {
            _isLoading.update { true }
            _error.update { null }
        }
        .map { result ->
            result.fold(
                onSuccess = { adsList ->
                    _isLoading.update { false }
                    _error.update { null }
                    adsList.sortedBy { it.operation == Ad.OperationType.SALE }
                        .map { adSummaryUiMapper.map(it) }
                },
                onFailure = { exception ->
                    _isLoading.update { false }
                    _error.update { exception.message }
                    emptyList()
                }
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    /**
     * Toggles the favorite status of an ad.
     *
     * @param adId The ID of the ad to toggle the favorite status for.
     */
    fun toggleFavorite(adId: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(adId)
                .onFailure { exception ->
                    _error.value = exception.message
                }
        }
    }
}



package com.feragusper.idealistachallenge.features.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feragusper.idealistachallenge.features.detail.presentation.ui.AdDetailUi
import com.feragusper.idealistachallenge.features.detail.presentation.ui.AdDetailUiMapper
import com.feragusper.idealistachallenge.libraries.ads.domain.usecase.GetAdDetailUseCase
import com.feragusper.idealistachallenge.libraries.ads.domain.usecase.ToggleFavoriteUseCase
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
 * ViewModel for the AdDetail screen.
 *
 * @param getAdDetailUseCase Use case to retrieve the ad detail.
 * @param mapper Mapper to convert domain models to UI models.
 * @property toggleFavoriteUseCase Use case to toggle the favorite status of an ad.
 */
@HiltViewModel
class AdDetailViewModel @Inject constructor(
    getAdDetailUseCase: GetAdDetailUseCase,
    mapper: AdDetailUiMapper,
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
     * State flow representing the ad detail.
     */
    val uiState: StateFlow<AdDetailUi?> = getAdDetailUseCase()
        .onStart {
            _isLoading.update { true }
            _error.update { null }
        }
        .map { result ->
            result.fold(
                onSuccess = { adDetail ->
                    _isLoading.update { false }
                    _error.update { null }
                    mapper.map(adDetail)
                },
                onFailure = { exception ->
                    _isLoading.update { false }
                    _error.update { exception.message }
                    null
                }
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    /**
     * Toggles the favorite status of the ad.
     *
     * This method is responsible for toggling the favorite status of the ad.
     */
    fun toggleFavorite() {
        val adId = uiState.value?.id ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(adId)
                .onFailure { exception ->
                    _error.update { exception.message }
                }
        }
    }
}

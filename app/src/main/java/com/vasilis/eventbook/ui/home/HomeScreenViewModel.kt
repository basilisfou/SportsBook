package com.vasilis.eventbook.ui.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasilis.domain.DomainResult
import com.vasilis.domain.usecases.GetSportsEventsUseCase
import com.vasilis.eventbook.managers.TimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */

typealias EventsBySport = Map<SportUiModel, SnapshotStateList<EventUiModel>>

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userCase: GetSportsEventsUseCase,
    private val timeUseCaseUserCase: TimeUseCase
) : ViewModel() {

    private val _sportEvents = mutableStateOf<EventsBySport>(mutableStateMapOf())
    val sportEvents: MutableState<EventsBySport> = _sportEvents

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        getSportsList()
    }

    private fun getSportsList() {
        _uiState.value = UiState.Loading
        userCase()
            .onEach { domainResult ->
                when (domainResult) {
                    is DomainResult.Success -> {
                        _uiState.value = UiState.Success
                        _sportEvents.value = domainResult.data.associateBy(
                            {
                                SportUiModel(
                                    sportName = it.sportName,
                                    categoryName = it.categoryName,
                                )
                            },
                            {
                                it.events.map { event ->
                                    EventUiModel(
                                        id = event.id,
                                        eventOpponent1 = event.eventOpponent1,
                                        eventOpponent2 = event.eventOpponent2,
                                        sportCategory = event.sportCategory,
                                        timeOfEvent = event.timeOfEvent
                                    )
                                }.toMutableStateList()
                            }
                        )
                    }
                    is DomainResult.Error -> {
                        _uiState.value = UiState.Error(domainResult.error)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun setFavorite(
        sport: SportUiModel,
        eventId: Int
    ) {
        _sportEvents
            .value[sport]
            ?.apply {
                find {
                    it.id == eventId
                }?.also {
                    Log.d("MOTHER", "${it.eventOpponent1} ${it.isFavorite}")
                    it.isFavorite.value = !it.isFavorite.value
                    Log.d("MOTHER", "${it.eventOpponent1} ${it.isFavorite}")
                }
            }?.apply {
                sortBy { eventToSort ->
                    eventToSort.isFavorite.value.not()
                }
                //toMutableStateList()
            }

    }
}

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    data class Error(var error: String) : UiState()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
}

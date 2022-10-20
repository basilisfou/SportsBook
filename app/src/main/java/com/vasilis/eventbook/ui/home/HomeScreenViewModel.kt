package com.vasilis.eventbook.ui.home

import android.os.CountDownTimer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userCase: GetSportsEventsUseCase,
    private val timeUseCaseUserCase: TimeUseCase
) : ViewModel() {

    private val _uiModel = mutableStateOf<List<SportUiModel>>(arrayListOf())
    val uiModel: MutableState<List<SportUiModel>> = _uiModel

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
                        _uiModel.value = domainResult.data.map {
                            SportUiModel(
                                sportName = it.sportName,
                                categoryName = it.categoryName,
                                events = mutableStateOf(
                                    it.events.map { event ->
                                        EventUiModel(
                                            id = event.id,
                                            eventOpponent1 = event.eventOpponent1,
                                            eventOpponent2 = event.eventOpponent2,
                                            sportCategory = event.sportCategory,
                                        ).also {  eventUiModel ->
                                            timer(
                                                timeUseCaseUserCase.timeDeltaTillFinish(event.timeOfEvent),
                                                changeValueListener = { timeString ->
                                                    eventUiModel.timeOfEvent.value = timeString
                                                }
                                            )
                                        }
                                    }
                                )
                            )
                        }.toMutableStateList()
                    }
                    is DomainResult.Error -> {
                        _uiState.value = UiState.Error(domainResult.error)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun setFavorite(
        event: EventUiModel
    ) {
        val sport = _uiModel.value.find { it.categoryName == event.sportCategory }

        sport?.events?.value
            ?.sortedBy { it.isFavorite.value }
            ?.find { event.id == it.id }
            ?.isFavorite?.value = !event.isFavorite.value
    }



    private fun timer(
        millis: Long?,
        changeValueListener: (String) -> Unit
    ) {
        millis?.let {
            object : CountDownTimer(it, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    changeValueListener.invoke(
                        getCountdownText(millisUntilFinished)
                    )
                }

                override fun onFinish() {}
            }.start()
        }?: kotlin.run {
            changeValueListener.invoke("Started")
        }
    }

    private fun getCountdownText(timeDelta: Long): String {
        return if (TimeUnit.MILLISECONDS.toDays(timeDelta) < 1) {
            String.format(Locale.getDefault(), "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(timeDelta),
                TimeUnit.MILLISECONDS.toMinutes(timeDelta) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDelta)),
                TimeUnit.MILLISECONDS.toSeconds(timeDelta) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDelta))
            )
        } else {
            String.format(Locale.getDefault(), "%dd %02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toDays(timeDelta),
                TimeUnit.MILLISECONDS.toHours(timeDelta) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(timeDelta)),
                TimeUnit.MILLISECONDS.toMinutes(timeDelta) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDelta)),
                TimeUnit.MILLISECONDS.toSeconds(timeDelta) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDelta))
            )
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

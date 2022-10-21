package com.vasilis.eventbook.ui.home

import com.vasilis.domain.DomainResult
import com.vasilis.domain.model.Event
import com.vasilis.domain.model.Sport
import com.vasilis.domain.usecases.GetSportsEventsUseCase
import com.vasilis.eventbook.base.TestCoroutineRule
import com.vasilis.eventbook.ui.coroutines.DispatcherProviderImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeScreenViewModelTest {

    private lateinit var SUT: HomeScreenViewModel

    @Mock
    private lateinit var userCase: GetSportsEventsUseCase

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @ExperimentalCoroutinesApi
    val testDispatcherProvider = DispatcherProviderImpl(
        mainThread = UnconfinedTestDispatcher(),
        backgroundThread = UnconfinedTestDispatcher(),
        default = UnconfinedTestDispatcher(),
        unconfined = UnconfinedTestDispatcher()
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        SUT = HomeScreenViewModel(userCase, testDispatcherProvider)
    }

    /**
     * TEST getSportsList
     */
    @Test
    fun `given get Sport List when usecase emits error then assert UiState is Type of Error`() =
        testCoroutineRule.runBlockingTest {
            //arrange
            Mockito.`when`(userCase.invoke()).thenReturn(
                flow {
                    emit(
                        DomainResult.Error(
                            ERROR
                        )
                    )
                }
            )
            //act
            SUT.getSportsList()
            //assert
            Assert.assertTrue(SUT.uiState.value is UiState.Error)
        }

    @Test
    fun `given get Sport List when use case emits error then assert UiState has the same error from Use case`() =
        testCoroutineRule.runBlockingTest {
            //arrange
            Mockito.`when`(userCase.invoke()).thenReturn(
                flow {
                    emit(
                        DomainResult.Error(
                            ERROR
                        )
                    )
                }
            )
            //act
            SUT.getSportsList()
            //assert
            Assert.assertEquals((SUT.uiState.value as UiState.Error).error, ERROR)
        }

    @Test
    fun `given get Sport List when use case emits Success then assert UiState is Success ui state`() =
        testCoroutineRule.runBlockingTest {
            //arrange
            Mockito.`when`(userCase.invoke()).thenReturn(
                flow {
                    emit(
                        DomainResult.Success(
                            data = arrayListOf()
                        )
                    )
                }
            )
            //act
            SUT.getSportsList()
            //assert
            Assert.assertTrue(SUT.uiState.value is UiState.Success)
        }

    @Test
    fun `given get Sport List when use case emits Success then assert the same data shall pass to the list of EventUiModel`() =
        testCoroutineRule.runBlockingTest {
            //arrange
            Mockito.`when`(userCase.invoke()).thenReturn(
                flow {
                    emit(
                        DomainResult.Success(
                            data = listOf(
                                Sport(
                                    SPORT_NAME,
                                    SPORT_CATEGORY,
                                    arrayListOf(
                                        Event(id = EVENT_ID),
                                        Event(id = EVENT_ID_2)
                                    )
                                )
                            )
                        )
                    )
                }
            )
            //act
            SUT.getSportsList()
            //assert
            Assert.assertTrue(SUT.sportEvents.value[SPORT_UI_MODEL]?.get(0)?.id == EVENT_ID)
            Assert.assertTrue(SUT.sportEvents.value[SPORT_UI_MODEL]?.get(1)?.id == EVENT_ID_2)
        }

    companion object {
        const val ERROR = "error"
        const val SPORT_NAME = "sportName"
        const val SPORT_CATEGORY = "categoryName"
        const val EVENT_ID = 6453
        const val EVENT_ID_2 = 3981

        val SPORT_UI_MODEL = SportUiModel(
            sportName = SPORT_NAME,
            categoryName = SPORT_CATEGORY
        )
    }
}
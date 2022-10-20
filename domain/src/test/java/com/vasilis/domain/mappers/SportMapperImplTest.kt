package com.vasilis.domain.mappers

import com.vasilis.domain.model.Event
import com.vasilis.domain.response.SportResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class SportMapperImplTest {
    /**
     * TEST method mapResponseToDomain
     */

    private lateinit var SUT: SportMapperImpl

    @Mock
    private lateinit var eventMapper: EventMapper

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        SUT = SportMapperImpl(eventMapper)
    }

    @Test
    fun `given transformation response to domain when sportEventResponse is null then assert domain response is an empty list`() {
        //arrange
        val response = null
        //act
        val result = SUT.mapResponseToDomain(response)
        //assert
        assertEquals(result.size, 0)
    }

    @Test
    fun `given transformation response to domain when sportEvent has id name then assert the same name shall pass to the domain`() {
        //arrange
        val sportName = "Sport Name"
        val categoryName = "category Name"
        val response = arrayListOf(
            SportResponse(
                i = categoryName,
                d = sportName,
                e = arrayListOf()
            )
        )
        //act
        val result = SUT.mapResponseToDomain(response)
        //assert
        assertEquals(result[0].sportName, sportName)
        assertEquals(result[0].categoryName, categoryName)
        assertEquals(result[0].events.size, 0)

    }

    @Test
    fun `given transformation response to domain when sportEvent response has a list with events assert the same list shall pass to domain`() {
        //arrange
        val id = 3
        val eventOpponent1 = "eventOpponent1"
        val eventOpponent2 = "eventOpponent2"
        val sportCategory = "sportCategory"
        val timeOfEvent = Date()

        val event = Event(
            id,
            eventOpponent1,
            eventOpponent2,
            timeOfEvent,
            sportCategory
        )



        Mockito.`when`(eventMapper.mapResponseToDomain(arrayListOf())).thenReturn(
            arrayListOf(
                event
            )
        )

        val response = arrayListOf(
            SportResponse(
                i = "",
                d = "",
                e = arrayListOf()
            )
        )
        //act
        val result = SUT.mapResponseToDomain(response)
        //assert
        assertEquals(result[0].events[0], event)

    }

    @Test
    fun `given transformation response to domain when second sportEvent is null then assert the default sport shall pass to the domain`() {
        //arrange
        val sportName = "Sport Name"
        val categoryName = "category Name"
        val response = arrayListOf(
            SportResponse(
                i = categoryName,
                d = sportName,
                e = arrayListOf()
            ),
            null
        )
        //act
        val result = SUT.mapResponseToDomain(response)
        //assert
        assertEquals(result[1].sportName, "")
        assertEquals(result[1].categoryName, "")
        assertEquals(result[1].events.size, 0)

    }
}
package com.vasilis.domain.mappers

import com.vasilis.domain.model.Event
import com.vasilis.domain.response.EventResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

@RunWith(Parameterized::class)
class EventMapperImplTest(
    private val paramOne: EventResponse,
    private val paramTwo: Event
) {

    private lateinit var SUT: EventMapperImpl

    @Before
    fun before() {
        SUT = EventMapperImpl()
    }

    /**
     * TEST method timeDeltaTillFinish
     */

    @Test
    fun `given transformation response to domain when eventResponse is null then assert domain response is an empty list`() {
        //arrange
        val response = null
        //act
        val result = SUT.mapResponseToDomain(response)
        //assert
        assertEquals(result.size, 0)
    }

    @Test
    fun `given transformation response to domain when eventResponse is null then assert paramOne equals to paramTwo`() {
        //act
        val result = SUT.mapResponseToDomain(arrayListOf(paramOne))
        //assert
        assertEquals(result[0], paramTwo)
    }

    @Test
    fun `given transformation response to domain when second eventResponse is null then assert second event is Default Event`() {
        //act
        val result = SUT.mapResponseToDomain(arrayListOf(paramOne,null))
        //assert
        assertEquals(result[1], Event())
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    EventResponse(
                        d = "Medeama SC - Dreams FC",
                        i = "0",
                        si = "FOOT",
                        sh = "Medeama SC - Dreams FC",
                        tt = 1668925680L
                    ),
                    Event(
                        id = 0,
                        eventOpponent1 = "Medeama SC",
                        eventOpponent2 = "Dreams FC",
                        timeOfEvent = Date(1668925680L * 1000),
                        sportCategory = "FOOT"
                    )
                )
            )
        }
    }
}
package com.vasilis.eventbook.managers

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*


internal class TimeUseCaseTest{

    private lateinit var SUT : TimeUseCase

    private val now = Date()

    @Before
    fun before(){
        SUT = TimeUseCase(now)
    }

    /**
     * TEST method timeDeltaTillFinish
     */
    @Test
    fun `given time of event is 1 hour later from Now then assert time delta is 3600000`() {
        //arrange
        val timeOfEvent = addHoursToJavaUtilDate(now, 1)
        //act
        val result = SUT.timeDeltaTillFinish(timeOfEvent)
        //assert
        Assert.assertEquals(result , 3600000L)
    }

    @Test
    fun `given time of event is 1 hour before from Now then assert time delta is null`() {
        //arrange
        val timeOfEvent = addHoursToJavaUtilDate(now, -1)
        //act
        val result = SUT.timeDeltaTillFinish(timeOfEvent)
        //assert
        Assert.assertEquals(result , null)
    }

    @Test
    fun `given time of event is null then assert result is null`() {
        //arrange
        val timeOfEvent = addHoursToJavaUtilDate(now, -1)
        //act
        val result = SUT.timeDeltaTillFinish(timeOfEvent)
        //assert
        Assert.assertEquals(result , null)
    }

    @Test
    fun `given time of event is equals with Now then assert time delta is null`() {
        //arrange
        val timeOfEvent = now
        //act
        val result = SUT.timeDeltaTillFinish(timeOfEvent)
        //assert
        Assert.assertEquals(result , null)
    }

    private fun addHoursToJavaUtilDate(date: Date, hours: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.HOUR_OF_DAY, hours)
        return calendar.time
    }
}
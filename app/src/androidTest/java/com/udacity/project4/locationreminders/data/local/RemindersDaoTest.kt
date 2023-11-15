package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.udacity.project4.locationreminders.data.dto.ReminderDTO

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {

//    TODO: Add testing implementation to the RemindersDao.kt
@get:Rule
var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: RemindersDatabase

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()
    }
    @After
    fun closeDb() = database.close()

    @Test
    fun insertReminderAndGetById(){
        runBlockingTest{
            val reminder = ReminderDTO("title","description","location",150.0,150.0,"id")
            database.reminderDao().saveReminder(reminder)
            val loaded = database.reminderDao().getReminderById(reminder.id)
            Assert.assertThat<ReminderDTO>(loaded as ReminderDTO, notNullValue())
            Assert.assertThat(loaded.id, `is`(reminder.id))
            Assert.assertThat(loaded.title, `is`(reminder.title))
            Assert.assertThat(loaded.description, `is`(reminder.description))
            Assert.assertThat(loaded.location, `is`(reminder.location))
            Assert.assertThat(loaded.latitude, `is`(reminder.latitude))
            Assert.assertThat(loaded.longitude, `is`(reminder.longitude))

        }
    }
    @Test
    fun getReminderByIdNull() = runBlockingTest {
        val reminderId = "does not exist"
        val loaded = database.reminderDao().getReminderById(reminderId)
        Assert.assertNull(loaded)
    }
}
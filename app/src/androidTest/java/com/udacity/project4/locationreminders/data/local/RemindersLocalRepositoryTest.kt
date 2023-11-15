package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.FakeReminderDao
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Medium Test to test the repository
@MediumTest
class RemindersLocalRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()



    val list =  listOf<ReminderDTO>(ReminderDTO("title", "description","location",(-360..360).random().toDouble(),(-360..360).random().toDouble()))

    private val reminder1 = list[0]
    private lateinit var fakeRemindersDao: FakeReminderDao
    private lateinit var remindersLocalRepository: RemindersLocalRepository

    @Before
    fun setup() {
        fakeRemindersDao = FakeReminderDao()
        remindersLocalRepository = RemindersLocalRepository(
            fakeRemindersDao, Dispatchers.Unconfined
        )
    }


    @Test
    fun getReminderByIdThatExistsInLocalCache() = runBlockingTest {
        assertThat((remindersLocalRepository.getReminder(reminder1.id) as? Result.Error)?.message).isEqualTo(
            "Reminder not found!")
        fakeRemindersDao.remindersServiceData[reminder1.id] = reminder1
        val loadedReminder = (remindersLocalRepository.getReminder(reminder1.id) as? Result.Success)?.data
        Assert.assertThat<ReminderDTO>(loadedReminder as ReminderDTO, CoreMatchers.notNullValue())
        Assert.assertThat(loadedReminder.id, CoreMatchers.`is`(reminder1.id))
        Assert.assertThat(loadedReminder.title, CoreMatchers.`is`(reminder1.title))
        Assert.assertThat(loadedReminder.description, CoreMatchers.`is`(reminder1.description))
        Assert.assertThat(loadedReminder.location, CoreMatchers.`is`(reminder1.location))
        Assert.assertThat(loadedReminder.latitude, CoreMatchers.`is`(reminder1.latitude))
        Assert.assertThat(loadedReminder.longitude, CoreMatchers.`is`(reminder1.longitude))
    }

    @Test
    fun getReminderByIdThatDoesNotExistInLocalCache() = runBlockingTest {
        val message = (remindersLocalRepository.getReminder(reminder1.id) as? Result.Error)?.message
        Assert.assertThat<String>(message, CoreMatchers.notNullValue())
        assertThat(message).isEqualTo("Reminder not found!")

    }


}


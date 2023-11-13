package com.udacity.project4.locationreminders.reminderslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var remindersListViewModel: RemindersListViewModel
    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var list: List<ReminderDTO>
    private lateinit var firstReminder:ReminderDTO

    @Before
    fun getReady(){
        list = listOf<ReminderDTO>(ReminderDTO("title","description","location",37.422131,-122.084801))
        firstReminder = list[0]
        fakeDataSource = FakeDataSource()
        remindersListViewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(),fakeDataSource)
    }


    @Test
    fun check_loading() {
        mainCoroutineRule.pauseDispatcher()
        remindersListViewModel.loadReminders()
        Assert.assertThat(remindersListViewModel.showLoading.getOrAwaitValue(), CoreMatchers.`is`(true))
        mainCoroutineRule.resumeDispatcher()
        Assert.assertThat(remindersListViewModel.showLoading.getOrAwaitValue(), CoreMatchers.`is`(false))

    }
    @Test
    fun returnError() {
        fakeDataSource = FakeDataSource()
        remindersListViewModel =
            RemindersListViewModel(ApplicationProvider.getApplicationContext(), fakeDataSource)
        remindersListViewModel.loadReminders()
        Assert.assertThat(remindersListViewModel.showSnackBar.getOrAwaitValue(), `is`("Reminder not found!")
        )
    }

}
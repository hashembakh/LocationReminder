package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import kotlin.Exception

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var reminders:MutableList<ReminderDTO> = mutableListOf()) : ReminderDataSource {

//    TODO: Create a fake data source to act as a double to the real data source

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        try{
            return Result.Success(reminders)
        }catch (e:Exception){
            return Result.Error(e.localizedMessage)
        }
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders?.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        //TODO("return the reminder with the id")
        try {
            val filteredReminders = reminders.filter {
                it.id == id
            }
            val reminder = filteredReminders.get(0)
            return Result.Success(reminder)

        }catch (e:Exception){
            return Result.Error(
                e.localizedMessage
            )
        }
    }

    override suspend fun deleteAllReminders() {
        reminders.clear()
    }


}
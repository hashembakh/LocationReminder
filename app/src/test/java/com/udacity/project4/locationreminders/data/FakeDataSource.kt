package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import java.lang.Exception

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(var reminders:MutableList<ReminderDTO>? = mutableListOf()) : ReminderDataSource {

//    TODO: Create a fake data source to act as a double to the real data source

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        reminders?.let{
            return Result.Success(ArrayList(it))
        }
        return Result.Error(
            "not found"
        )
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders?.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        //TODO("return the reminder with the id")
        val filteredReminders = reminders?.filter {
            it.id == id
        }
        val reminder = filteredReminders?.get(0)
        if(reminder != null ){
            return Result.Success(reminder)
        }
        return Result.Error(
            "reminder not found"
        )
    }

    override suspend fun deleteAllReminders() {
        reminders?.clear()
    }


}
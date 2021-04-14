package com.adityabrian.githubapp.Preference

import android.content.Context
import com.adityabrian.githubapp.Data.Model.Reminder

class ReminderPreference(context: Context) {

    companion object {
        const val PREFERENCE_NAME = "reminder_pref"
        private const val REMINDER = "isRemind"
    }

    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    /** fun dari setRemindernya*/
    fun setReminder(value: Reminder) {
        val editor = preference.edit()
        /** kita put boolean , masukan data yang kita masukan, REMINDER adalah keynya*/
        editor.putBoolean(REMINDER, value.isReminded)
        editor.apply()
        /** jangan lupa buat getternya*/
    }

    fun getReminder(): Reminder {
        val model = Reminder()
        model.isReminded = preference.getBoolean(REMINDER, false)
        return model
    }
}
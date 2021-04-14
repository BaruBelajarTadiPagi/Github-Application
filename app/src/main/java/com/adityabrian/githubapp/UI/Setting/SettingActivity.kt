package com.adityabrian.githubapp.UI.Setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adityabrian.githubapp.Data.Model.Reminder
import com.adityabrian.githubapp.Preference.ReminderPreference
import com.adityabrian.githubapp.Reciever.AlarmReceiver
import com.adityabrian.githubapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** kita buat reminder preferencenya*/
        val reminderPreference = ReminderPreference(this)
        if (reminderPreference.getReminder().isReminded) {
            binding.Switch1.isChecked = true
            /** apa bila reminderPreference nya sudah di setting maka binding.switch1 nya aktif atau true*/
        } else {
            binding.Switch1.isChecked = false
        }

        /** kita instansi juga alarmReceiver*/
        alarmReceiver = AlarmReceiver()

        binding.Switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                saveReminder(true)
                /** yang akan menyimpan data state nya ke SharedPreference*/
                alarmReceiver.setRepeatingAlarm(this, "RepeatingAlarm", "08:18", "Github reminder")
            }else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(state: Boolean) {
        val reminderPreference = ReminderPreference(this)
        reminder = Reminder()

        reminder.isReminded = state
        reminderPreference.setReminder(reminder)
    }
}
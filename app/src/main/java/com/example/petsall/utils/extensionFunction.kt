package com.example.petsall.utils

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.widget.DatePicker
import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusManager
import androidx.core.content.ContextCompat
import java.time.LocalTime
import java.util.*

fun checkLocationPermission(context: Context): Boolean {
    val coarsePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
    val finePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
    return coarsePermission == PackageManager.PERMISSION_GRANTED && finePermission == PackageManager.PERMISSION_GRANTED
}
val permissions = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_COARSE_LOCATION

    )

fun generateAvailableTimes(startTime: String, endTime: String): List<String> {
    val availableTimes = mutableListOf<String>()

    var currentTime = LocalTime.parse(startTime)
    val endTimeObj = LocalTime.parse(endTime)

    while (currentTime.isBefore(endTimeObj)) {
        val nextTime = currentTime.plusHours(1)
        availableTimes.add("$currentTime - $nextTime")
        currentTime = nextTime
    }

    return availableTimes
}

fun datePicker(date: MutableState<String>,context: Context,focusManager: FocusManager):DatePickerDialog {
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    return DatePickerDialog(
        context, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            date.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            focusManager.clearFocus()
        }, mYear, mMonth, mDay
    )
}
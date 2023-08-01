package com.example.petsall.utils

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.widget.DatePicker
import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusManager
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
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

fun generateAvailableTimes(dateString: String, startTime: String, endTime: String): List<String> {
    val availableTimes = mutableListOf<String>()

    val currentDate = LocalDate.now()
    val currentTime = LocalTime.now()

    val dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val selectedDate = LocalDate.parse(dateString, dateFormatter)
    val startTimeObj = LocalTime.parse(startTime)
    val endTimeObj = LocalTime.parse(endTime)


    val adjustedStartTime = if (selectedDate == currentDate && startTimeObj.isBefore(currentTime)) {
        currentTime
    } else {
        startTimeObj
    }

    var currentAdjustedTime = adjustedStartTime

    // Ajustar los minutos al siguiente múltiplo de 60
    val minutesToAdd = (60 - currentAdjustedTime.minute) % 60
    currentAdjustedTime = currentAdjustedTime.plusMinutes(minutesToAdd.toLong())

    while (currentAdjustedTime.isBefore(endTimeObj) || currentAdjustedTime == endTimeObj) {
        val nextTime = currentAdjustedTime.plusHours(1)

        // Verificar si el siguiente intervalo supera el tiempo de fin por menos de 2 minutos
        val timeDifference = ChronoUnit.MINUTES.between(currentAdjustedTime, nextTime)
        if (timeDifference >= 2 && currentAdjustedTime != endTimeObj) {
            val formattedTime = currentAdjustedTime.format(timeFormatter)
            val formattedNextTime = nextTime.format(timeFormatter)
            availableTimes.add("$formattedTime - $formattedNextTime")
        }

        currentAdjustedTime = nextTime
    }

    return availableTimes
}

fun datePicker(date: MutableState<String>, context: Context, focusManager: FocusManager): DatePickerDialog {
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
            val formattedMonth = String.format("%02d", mMonth + 1) // Formatear el mes con dos dígitos
            date.value = "$mDayOfMonth/$formattedMonth/$mYear"
            focusManager.clearFocus()
        }, mYear, mMonth, mDay
    )
}

fun convertStringToTimestamp(dateString: String): Timestamp? {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return try {
        val date = dateFormat.parse(dateString)
        Timestamp(Date(date?.time ?: (20 / 10 / 2023)))
    } catch (e: Exception) {
        null
    }
}

fun convertTimestampToString(timestamp: Timestamp): String {
    val date = timestamp.toDate()
    val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    return dateFormat.format(date)
}
fun convertTimestampToString2(timestamp: Timestamp): String {
    val date = timestamp.toDate()
    val dateFormat = SimpleDateFormat("d 'de' MMMM 'del' yyyy 'a las' HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}


fun convertDateTimeToTimestamp(dateString: String, timeString: String): Timestamp? {
    val dateTimeString = "$dateString $timeString"
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return try {
        val date = dateFormat.parse(dateTimeString)
        Timestamp(Date(date?.time ?: (20 / 10 / 2022)))
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

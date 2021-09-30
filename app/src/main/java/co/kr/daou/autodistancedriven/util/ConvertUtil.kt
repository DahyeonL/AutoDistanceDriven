package co.kr.daou.autodistancedriven.util

import android.content.Context
import android.location.Geocoder
import android.location.Location
import co.kr.daou.autodistancedriven.feature.driving.adapter.DrivingListDetailRecyclerviewAdapter
import java.text.SimpleDateFormat
import java.util.*

class ConvertUtil {
    fun convertDateTimeToDate(date : Date?) : String{
        val dateFormat = SimpleDateFormat("MM월 dd일 HH시 mm분", Locale.KOREA)
        return dateFormat.format(date)
    }
    fun convertDoubleToString(double : Double?) : String{
        if (double != null) {
            return if(double<1000) String.format("%.1f",double)+ M
            else return String.format("%.1f",double/1000) + KM
        }
        return "표시할 값이 없습니다."
    }
    fun convertStringToTimeString(string : String) : String{
        return "${string[0]}${string[1]}시 ${string[3]}${string[4]}분 ${string[6]}${string[7]}초"
    }
    fun convertTimeToString(string : String?) : String{
        return convertStringToTimeString(string!!) + TIME
    }
    fun convertStartTimeToString(date : Date?) : String{
        return STARTTIME + convertDateTimeToDate(date)
    }
    fun convertFinishTimeToString(date : Date?) : String{
        return FINISHTIME + convertDateTimeToDate(date)
    }
    fun convertTimeToString(time : Long): String{
        val hour = time/3600
        val minute = (time-hour*3600)/60
        val second = time-hour*3600-minute*60
        return "${String.format("%02d", hour)}:${String.format("%02d", minute)}:${String.format("%02d", second)}"
    }
    fun convertStartPointToString(string : String?) : String{
        return STARTPOINT + string
    }
    fun convertFinishPointToString(string : String?) : String{
        return FINISHPOINT + string
    }

    companion object{
        const val M = "M"
        const val KM = "KM"
        const val TIME = " 소요"
        const val STARTTIME = "출발 시간 : "
        const val FINISHTIME = "도착 시간 : "
        const val STARTPOINT = "출발지 : "
        const val FINISHPOINT = "도착지 : "
    }
}
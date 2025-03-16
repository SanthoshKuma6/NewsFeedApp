//package com.task.newsfeedapp.model
//
//import androidx.room.TypeConverter
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//class Converters {
//    private val gson = Gson()
//
//    @TypeConverter
//    fun fromMultimediaList(value: List<Multimedia>): String {
//        return gson.toJson(value)
//
//    }
//
//
//
//    @TypeConverter
//    fun toMultimediaList(multimediaString: String?): List<ArticleResponse.Legacy.Multimedia>? {
//        if (multimediaString.isNullOrEmpty()) return emptyList()
//        val listType = object : TypeToken<List<ArticleResponse.Legacy.Multimedia>>() {}.type
//        return gson.fromJson(multimediaString, listType)
//    }
//
//
//
//}
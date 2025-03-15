package com.task.newsfeedapp.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.task.newsfeedapp.model.RoomModel

@Dao
interface RoomInterface {
    @Insert
     fun insertArticle(movie: List<RoomModel>)

    @Query("SELECT * FROM articles ")
    fun getArticle(): List<RoomModel>

}
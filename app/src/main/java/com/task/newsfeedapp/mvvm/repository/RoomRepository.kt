package com.task.newsfeedapp.mvvm.repository

import com.task.newsfeedapp.dao.RoomDao
import com.task.newsfeedapp.model.RoomModel
import kotlinx.coroutines.flow.Flow


class RoomRepository(private val roomDao: RoomDao?) {

    fun insert(saveData: List<RoomModel>) {
        roomDao?.roomInterface()?.insertArticle(saveData)
    }


    fun getList(): List<RoomModel> {
        return roomDao?.roomInterface()!!.getArticle()
    }

}
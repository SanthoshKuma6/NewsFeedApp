package com.task.newsfeedapp.mvvm

import com.task.newsfeedapp.dao.RoomDao
import com.task.newsfeedapp.model.RoomModel


class RoomRepository(private val roomDao: RoomDao?) {

    fun insert(saveData: List<RoomModel>) {
        roomDao?.roomInterface()?.insertArticle(saveData)
    }


    fun getList(): List<RoomModel> {
        return roomDao?.roomInterface()!!.getArticle()
    }

}
package com.task.newsfeedapp.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.task.newsfeedapp.model.RoomModel
import com.task.newsfeedapp.resource.RoomResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomViewModel (
    private val roomRepository : RoomRepository) : ViewModel() {

    fun insert(saveData: List<RoomModel>)  {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                roomRepository.insert(saveData)
            }
        }
    }

    private val _articles = MutableStateFlow<RoomResource<List<RoomModel>>>(RoomResource.Loading())
    val articles: StateFlow<RoomResource<List<RoomModel>>> = _articles

    fun getList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _articles.value = RoomResource.Loading() // Optional, show loading initially
                try {
                    val result = roomRepository.getList() // Fetch the data
                    _articles.value = RoomResource.Success(result)
                } catch (e: Exception) {
                    _articles.value = RoomResource.Error(e.message ?: "Unknown error")
                }
            }

        }
    }

}
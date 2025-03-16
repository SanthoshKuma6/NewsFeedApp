package com.task.newsfeedapp.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.newsfeedapp.model.RoomModel
import com.task.newsfeedapp.mvvm.repository.RoomRepository
import com.task.newsfeedapp.resource.RoomResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomViewModel (
    private val roomRepository : RoomRepository
) : ViewModel() {

    fun insert(saveData: List<RoomModel>)  {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (roomRepository.insert(saveData).equals(false)){
                    Log.d("RoomTAG", "Insert failed")
                } else{
                    roomRepository.insert(saveData)
                    Log.d("RoomTAG", "insert: $saveData")

                }
            }
        }
    }

    private val _articles = MutableStateFlow<RoomResource<List<RoomModel>>>(RoomResource.Loading())
    val articles: StateFlow<RoomResource<List<RoomModel>>> = _articles

    fun getList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _articles.value = RoomResource.Loading()
                try {
                    val result = roomRepository.getList()
                    if (result.isNotEmpty()){
                        _articles.value=RoomResource.Success(result)
                        Log.d("RoomTAG", "getList: $result")
                    } else{
                        _articles.value=RoomResource.Loading()
                        _articles.value=RoomResource.Error(result.toString())
                        Log.d("RoomTAG", "getList: ")
                    }
                } catch (e: Exception) {
                    _articles.value = RoomResource.Error(e.message ?: "Unknown error")
                    Log.d("RoomTAG", "${e.message}: ")

                }
            }

        }
    }

}
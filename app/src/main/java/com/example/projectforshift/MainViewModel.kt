package com.example.projectforshift

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectforshift.data.models.Card
import com.example.projectforshift.data.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {
    private val _allInfo = MutableLiveData<Card>()
    val allInfo: LiveData<Card>
        get() = _allInfo

    fun getAllInfo() {
        viewModelScope.launch {
            repository.getAllCardInfo().let {
                if (it.isSuccessful) {
                    _allInfo.postValue(it.body())
                } else {
                    Log.d("checkData", "Failed to load info: ${it.errorBody()}")
                }
            }
        }
    }
}
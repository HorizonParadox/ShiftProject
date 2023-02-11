package com.example.projectforshift

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectforshift.data.models.Card
import com.example.projectforshift.data.network.ApiRepository
import com.example.projectforshift.db.HistoryDataBase
import com.example.projectforshift.db.model.HistoryModel
import com.example.projectforshift.db.repository.HistoryRealization
import com.example.projectforshift.db.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApiRepository, application: Application) : ViewModel() {
    private val _allInfo = MutableLiveData<Card>()
    val allInfo: LiveData<Card>
        get() = _allInfo

    fun getAllInfo(bin: String) {
        viewModelScope.launch {
            repository.getAllCardInfo(bin).let {
                if (it.isSuccessful) {
                    _allInfo.postValue(it.body())
                } else {
                    Toast.makeText(context, "Failed to load info", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val context = application
    lateinit var dbRepository: HistoryRepository
    fun initDataBase(){
        val daoRoute = HistoryDataBase.getInstance(context).getDao()
        dbRepository = HistoryRealization(daoRoute)
    }
    fun getAllHistory(): LiveData<List<HistoryModel>> {
        return dbRepository.historyList
    }
    suspend fun addNewCardNumber(cardItem: HistoryModel) = dbRepository.insert(cardItem)
}
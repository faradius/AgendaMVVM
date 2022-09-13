package com.alex.agendamvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.agendamvvm.data.config.PersonalApp.Companion.db
import com.alex.agendamvvm.data.model.Personal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    val personalList = MutableLiveData<List<Personal>?>()
    var personalSearch = MutableLiveData<String>()

    fun start(){
        viewModelScope.launch {
            personalList.value = withContext(Dispatchers.IO){
                db.personalDao().getAll()
            }
        }
    }

    fun buscarPersonal() {
        viewModelScope.launch {
            personalList.value = withContext(Dispatchers.IO){
                db.personalDao().getByName(personalSearch.value!!)
            }
        }
    }
}
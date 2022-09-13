package com.alex.agendamvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.agendamvvm.data.config.PersonalApp.Companion.db
import com.alex.agendamvvm.data.model.Personal
import com.alex.agendamvvm.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormViewModel:ViewModel() {
    var id = MutableLiveData<Long>()
    var nombre = MutableLiveData<String>()
    var apellidos = MutableLiveData<String>()
    var telefono = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var edad = MutableLiveData<Int>()
    var operacion = Constants.OPERATION_INSERT
    var resultadoOperacion = MutableLiveData<Boolean>()

    init {
        edad.value = 15
    }

    fun saveUser(){
        if (validateInfo()){
            var mPersonal = Personal(0,nombre.value!!,apellidos.value!!,email.value!!,telefono.value!!,edad.value!!)
            when(operacion){
                Constants.OPERATION_INSERT ->{
                    viewModelScope.launch {
                        val result = withContext(Dispatchers.IO){
                            db.personalDao().insert(arrayListOf(mPersonal))
                        }
                        resultadoOperacion.value = result.isNotEmpty()
                    }

                }
                Constants.OPERATION_EDIT ->{
                    mPersonal.idEmpleado = id.value!!
                    viewModelScope.launch {
                        val result = withContext(Dispatchers.IO){
                            db.personalDao().update(mPersonal)
                        }
                        resultadoOperacion.value = (result>0)
                    }
                }
            }
        }else{
            resultadoOperacion.value = false
        }

    }

    fun cargarDatos() {
        viewModelScope.launch {
            var persona = withContext(Dispatchers.IO){
                db.personalDao().getById(id.value!!)
            }
            nombre.value = persona.nombre
            apellidos.value = persona.apellidos
            telefono.value = persona.telefono
            email.value = persona.email
            edad.value = persona.edad
        }
    }

    private fun validateInfo():Boolean{
        return !(nombre.value.isNullOrEmpty()) ||
                apellidos.value.isNullOrEmpty() ||
                email.value.isNullOrEmpty() ||
                telefono.value.isNullOrEmpty() ||
                edad.value!! <= 0 || edad.value!! >= 100
    }

    fun eliminarPersonal() {
        var mPersonal = Personal(id.value!!,"","","","",0)
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                db.personalDao().delete(mPersonal)
            }
            resultadoOperacion.value = (result>0)
        }

    }
}
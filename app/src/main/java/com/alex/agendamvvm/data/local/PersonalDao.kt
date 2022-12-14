package com.alex.agendamvvm.data.local

import androidx.room.*
import com.alex.agendamvvm.data.model.Personal

@Dao
interface PersonalDao {
    @Query("SELECT * FROM Personal")
    suspend fun getAll():List<Personal>

    @Query("SELECT * FROM Personal WHERE idEmpleado = :id")
    suspend fun getById(id:Long):Personal

    @Query("SELECT * FROM Personal WHERE nombre LIKE '%'|| :name || '%' OR apellidos LIKE '%'|| :name || '%'")
    suspend fun getByName(name:String):List<Personal>

    @Insert
    suspend fun insert(personas:List<Personal>):List<Long>

    @Update
    suspend fun update(personal: Personal):Int

    @Delete
    suspend fun delete(personal: Personal):Int
}
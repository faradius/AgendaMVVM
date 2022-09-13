package com.alex.agendamvvm.data.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alex.agendamvvm.data.local.PersonalDao
import com.alex.agendamvvm.data.model.Personal

@Database(
    entities = [Personal::class],
    version = 1
)
abstract class AgendaDB : RoomDatabase(){

    abstract fun personalDao():PersonalDao
}
package com.alex.agendamvvm.data.config

import android.app.Application
import androidx.room.Room

class PersonalApp: Application() {
    companion object{
        lateinit var db:AgendaDB
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            AgendaDB::class.java,
            "AgendaDB"
        ).build()
    }
}
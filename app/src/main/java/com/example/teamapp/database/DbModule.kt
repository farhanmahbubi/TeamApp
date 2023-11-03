package com.example.teamapp.database

import android.content.Context
import androidx.room.Room
import com.example.teamapp.detail.Detaill

class DbModule(private val context: Context) {
    private val db = Room.databaseBuilder(context, AppDatabase::class.java, "usergithub.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.userDao()
}
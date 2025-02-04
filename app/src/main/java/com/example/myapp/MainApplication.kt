package com.example.myapp

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapp.database.TodoItemDatabase

class MainApplication : Application(){

    companion object{
        lateinit var todoItemDatabase : TodoItemDatabase
    }

    override fun onCreate(){
        super.onCreate()
      todoItemDatabase = Room.databaseBuilder(
            applicationContext,
            TodoItemDatabase::class.java,
            TodoItemDatabase.NAME
      )
          .build()
    }


}
/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SleepNight::class], version= 1, exportSchema = false )
abstract class SleepDatabase: RoomDatabase(){
    //declare an abstract value of sleepDatabaseDao
    abstract var sleepDatabaseDao: SleepDatabaseDao

    //declare a companion object
    companion object{

        //declare a @volatile INSTANCE variable
        //INSTANCE will keep a reference to the database meaning we don't repeatedly open connections to the db which is expensive
        //changes made to one thread of INSTANCE are visible to all threads
        @Volatile
        private var INSTANCE: SleepDatabase? = null

        //define getinstance method with a synchronised block
        fun getInstance(context: Context): SleepDatabase{
            //synchronised ensures that when multiple threads ask for access only one thread can enter the block of code ensuring the db gets initialized once
            synchronized(this){
                var instance = INSTANCE

                //check if database already exists
                //if not use Room.DatabaseBuilder to create it
                if(instance == null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SleepDatabase::class.java,
                            "sleep_history_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }




}
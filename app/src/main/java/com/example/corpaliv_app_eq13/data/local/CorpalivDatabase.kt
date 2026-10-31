
package com.example.corpaliv_app_eq13.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SolicitudEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CorpalivDatabase : RoomDatabase() {

    abstract fun solicitudDao(): SolicitudDao

    companion object {
        @Volatile
        private var INSTANCE: CorpalivDatabase? = null

        fun obtenerInstancia(context: Context): CorpalivDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CorpalivDatabase::class.java,
                    "corpaliv_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

package com.example.corpaliv_app_eq13.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {

    @Insert
    suspend fun insertar(solicitud: SolicitudEntity)

    @Query("SELECT * FROM solicitudes ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<SolicitudEntity>>

    @Query("DELETE FROM solicitudes WHERE id = :id")
    suspend fun eliminar(id: Int)
}

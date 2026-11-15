package com.example.corpaliv_app_eq13.data.remote

import com.example.corpaliv_app_eq13.data.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // Método GET para obtener todos los posts
    @GET("posts")
    suspend fun getPosts(): List<Post>

    // Método GET para obtener un post específico por su ID
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post

    // Método POST para crear un nuevo post en el servidor
    @POST("posts")
    suspend fun createPost(@Body post: Post): Post

    // Método PUT para actualizar un post existente
    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: Post): Post

    // Método DELETE para eliminar un post
    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>
}

package com.example.corpaliv_app_eq13.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv_app_eq13.data.model.Post
import com.example.corpaliv_app_eq13.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PostUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class PostViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, successMessage = null) }
            try {
                val result = repository.getPosts()
                _uiState.update {
                    it.copy(
                        posts = result,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al cargar publicaciones: ${e.localizedMessage ?: "Sin conexión"}"
                    )
                }
            }
        }
    }

    fun createPost(title: String, body: String) {
        if (title.isBlank() || body.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Título y contenido no pueden estar vacíos") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val newPost = repository.createPost(Post(userId = 1, title = title, body = body))
                _uiState.update {
                    it.copy(
                        posts = listOf(newPost) + it.posts,
                        isLoading = false,
                        successMessage = "Publicación enviada exitosamente (Simulada con ID ${newPost.id})"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al crear: ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            try {
                repository.deletePost(id)
                _uiState.update {
                    it.copy(
                        posts = it.posts.filter { post -> post.id != id },
                        successMessage = "Publicación #$id eliminada exitosamente"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error al eliminar: ${e.localizedMessage}")
                }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }
}

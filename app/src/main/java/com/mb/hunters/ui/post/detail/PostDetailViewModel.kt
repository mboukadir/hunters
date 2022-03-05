package com.mb.hunters.ui.post.detail

import androidx.lifecycle.SavedStateHandle
import com.mb.hunters.common.dispatcher.DispatchersProvider
import com.mb.hunters.data.repository.post.PostRepository
import com.mb.hunters.ui.Screen
import com.mb.hunters.ui.base.BaseViewModel
import com.mb.hunters.ui.post.state.PostDetailContentState
import com.mb.hunters.ui.post.state.PostDetailContentStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mapper: PostDetailContentStateMapper,
    private val postRepository: PostRepository,
    dispatchersProvider: DispatchersProvider
) : BaseViewModel(dispatchersProvider = dispatchersProvider) {

    private val postId: Long =
        requireNotNull(savedStateHandle.get<Long>(Screen.Post.POST_ID_KEY))

    private val _postDetailUiState = MutableStateFlow<PostDetailUiState>(PostDetailUiState.Loading)
    val postDetailUiState: StateFlow<PostDetailUiState> = _postDetailUiState

    init {
        viewModelScope.launch {
            runCatching {
                mapper.map(postRepository.getPost(id = postId))
            }.fold(
                onSuccess = {
                    _postDetailUiState.value = PostDetailUiState.Success(it)
                },
                onFailure = {
                    if (it !is CancellationException) {
                        Timber.e(it)
                        _postDetailUiState.value = PostDetailUiState.Failed(it.message.orEmpty())
                    }
                }
            )
        }
    }

    sealed class PostDetailUiState {
        object Loading : PostDetailUiState()
        data class Success(val contentState: PostDetailContentState) : PostDetailUiState()
        data class Failed(val message: String) : PostDetailUiState()
    }
}

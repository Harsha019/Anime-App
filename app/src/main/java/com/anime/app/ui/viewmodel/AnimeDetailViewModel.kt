package com.anime.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anime.app.data.model.response.AnimeDetailResponse
import com.anime.app.domain.repository.AnimeRepository
import com.anime.app.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeDetailViewModel(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _detail = MutableStateFlow<Resource<AnimeDetailResponse>>(Resource.Loading())
    val detail = _detail.asStateFlow()

    fun loadAnimeDetail(id: Int) {
        viewModelScope.launch {
            _detail.value = Resource.Loading()
            _detail.value = repository.getAnimeDetail(id)
        }
    }
}

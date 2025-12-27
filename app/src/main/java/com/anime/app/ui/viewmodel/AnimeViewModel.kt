package com.anime.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anime.app.data.db.AnimeEntity
import com.anime.app.domain.repository.AnimeRepository
import com.anime.app.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _anime = MutableStateFlow<Resource<List<AnimeEntity>>>(Resource.Loading())
    val anime = _anime.asStateFlow()

    fun loadAnime() {
        viewModelScope.launch {
            _anime.value = Resource.Loading()
            _anime.value = repository.fetchTopAnime()
        }
    }
}


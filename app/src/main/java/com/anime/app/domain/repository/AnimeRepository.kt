package com.anime.app.domain.repository

import com.anime.app.data.db.AnimeEntity
import com.anime.app.data.model.response.AnimeDetailResponse
import com.anime.app.utils.Resource

interface AnimeRepository {

    suspend fun fetchTopAnime(): Resource<List<AnimeEntity>>

    suspend fun getAnimeDetail(id: Int): Resource<AnimeDetailResponse>
}

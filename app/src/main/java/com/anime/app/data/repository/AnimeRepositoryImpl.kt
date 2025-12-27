package com.anime.app.data.repository

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.anime.app.data.api.AnimeApiService
import com.anime.app.data.db.AnimeDao
import com.anime.app.data.db.AnimeEntity
import com.anime.app.data.model.response.AnimeDetailResponse
import com.anime.app.domain.repository.AnimeRepository
import com.anime.app.utils.NetworkUtils
import com.anime.app.utils.Resource

class AnimeRepositoryImpl(
    private val api: AnimeApiService,
    private val dao: AnimeDao,
    private val context: Context
) : AnimeRepository {

    override suspend fun fetchTopAnime(): Resource<List<AnimeEntity>> {
        return try {
            val cached = dao.getAllAnime()
            if (cached.isNotEmpty()) {
                if (!NetworkUtils.isOnline(context)) {
                    return Resource.Success(cached)
                }
            } else {
                if (!NetworkUtils.isOnline(context)) {
                    return Resource.Error("No internet connection and no cached data available")
                }
            }

            val response = api.getTopAnime()
            val entities = response.data.map { dto ->
                AnimeEntity(
                    id = dto.malId,
                    title = dto.title ?: "N/A",
                    episodes = dto.episodes,
                    rating = dto.score,
                    imageUrl = dto.images?.jpg?.imageUrl
                )
            }

            dao.clearAnime()
            dao.insertAnime(entities)
            Resource.Success(dao.getAllAnime())

        } catch (e: Exception) {
            val cached = runCatching { dao.getAllAnime() }.getOrDefault(emptyList())
            if (cached.isNotEmpty()) Resource.Success(cached)
            else Resource.Error("Failed to load anime: ${e.message ?: "Unknown error"}")
        }
    }

    override suspend fun getAnimeDetail(id: Int): Resource<AnimeDetailResponse> {
        return try {
            Resource.Success(api.getAnimeDetail(id))
        } catch (e: Exception) {
            Resource.Error("Unable to load details: ${e.message ?: "Unknown error"}")
        }
    }
}

package com.anime.app.data.model.response

import com.google.gson.annotations.SerializedName

data class TopAnimeResponse(
    val data: List<AnimeDto>
)

data class AnimeDto(
    @SerializedName("mal_id") val malId: Int,
    val title: String?,
    val episodes: Int?,
    val score: Double?,
    val images: ImagesDto?
)

data class ImagesDto(
    val jpg: JpgDto?
)

data class JpgDto(
    @SerializedName("image_url") val imageUrl: String?
)

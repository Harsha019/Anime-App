package com.anime.app.data.model.response

import com.google.gson.annotations.SerializedName

data class AnimeDetailResponse(
    @SerializedName("data")
    val data: AnimeDetailDto
)

data class AnimeDetailDto(
    @SerializedName("mal_id")
    val malId: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("synopsis")
    val synopsis: String?,

    @SerializedName("episodes")
    val episodes: Int?,

    @SerializedName("score")
    val score: Double?,

    @SerializedName("images")
    val images: ImagesDto,

    @SerializedName("genres")
    val genres: List<GenreDto>?,

    @SerializedName("trailer")
    val trailer: TrailerDto?
)

data class GenreDto(
    @SerializedName("name")
    val name: String
)

data class TrailerDto(
    @SerializedName("url")
    val url: String?,

    @SerializedName("youtube_id")
    val youtubeId: String?,

    @SerializedName("embed_url")
    val embedUrl: String?
)

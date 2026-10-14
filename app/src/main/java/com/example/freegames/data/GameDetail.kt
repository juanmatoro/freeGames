package com.example.freegames.data

import com.google.gson.annotations.SerializedName

data class GameDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("short_description") val shortDescription: String,
    @SerializedName("description") val description: String?,
    @SerializedName("genre") val genre: String?,
    @SerializedName("platform") val platform: String?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("developer") val developer: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("freetogame_profile_url") val profileUrl: String?,
    @SerializedName("minimum_system_requirements") val systemRequirements: SystemRequirements?,
    @SerializedName("screenshots") val screenshots: List<Screenshot>?
)

data class SystemRequirements(
    @SerializedName("os") val os: String?,
    @SerializedName("processor") val processor: String?,
    @SerializedName("memory") val memory: String?,
    @SerializedName("graphics") val graphics: String?,
    @SerializedName("storage") val storage: String?
)

data class Screenshot(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String?
)

package com.example.pokedexapp.network.models

import com.squareup.moshi.JsonClass

data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<String>,
    val weight: String,
    val height: String,
    val hp: Int,
    val imageUrl: String,
    val description: String,
)
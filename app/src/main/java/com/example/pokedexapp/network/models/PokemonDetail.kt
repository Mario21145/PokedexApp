package com.example.pokedexapp.network.models

data class PokemonDetail(
    val id: Int,
    val name: String,
    val types: List<String>,
    val weight: String,
    val height: String,
    val hp: Int
)
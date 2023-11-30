package com.example.pokedexapp.network.models


data class PokemonResponse(
    val generation: Int,
    val pokemon: List<Pokemon>
)
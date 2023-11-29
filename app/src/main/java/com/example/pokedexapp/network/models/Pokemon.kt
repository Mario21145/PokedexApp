package com.example.pokedexapp.network.models

data class pokemon(
    val generation: Int,
    val pokemon: List<PokemonDetail>
)
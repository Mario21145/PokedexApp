package com.example.pokedexapp.network.services

import com.example.pokedexapp.network.models.PokemonResponse
import com.example.pokedexapp.network.models.Pokemon
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header


const val BASE_URL = "https://raw.githubusercontent.com/Mario21145/PokemonData/main/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface PokedexApiService{
    @GET("pokemon.json")
    suspend fun getPokemonList() : PokemonResponse
}

object PokemonApi {
    val retrofitService : PokedexApiService by lazy {
        retrofit.create(PokedexApiService::class.java) }
}
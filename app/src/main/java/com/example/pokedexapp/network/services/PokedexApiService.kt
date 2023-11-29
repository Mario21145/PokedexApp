package com.example.pokedexapp.network.services

import com.example.pokedexapp.network.models.pokemon
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val BASE_URL = "https://github.com/brunomoreirazup/kanto-pokedex-json/blob/master"


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

//val gson = Gson()
//val pokemonData: pokemon = gson.fromJson(yourJsonString, pokemon::class.java)

interface PokedexApiService{
    @GET("/pokedex.json")
    suspend fun getData() : List<pokemon>
}

object PokemonApi {
    val retrofitService : PokedexApiService by lazy {
        retrofit.create(PokedexApiService::class.java) }
}
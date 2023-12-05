package com.example.pokedexapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.network.models.Pokemon
import com.example.pokedexapp.network.services.PokemonApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

enum class PokemonApiStatus {DEFAULT, LOADING, DONE, ERROR }

class ViewModelPokedex : ViewModel() {

    private var _status = MutableLiveData<PokemonApiStatus>().apply {
        value = PokemonApiStatus.DEFAULT
    }
    val status: LiveData<PokemonApiStatus> = _status

    private val _pokemons = MutableLiveData<List<Pokemon>>().apply {
        listOf<Pokemon>()
    }
    var pokemons: MutableLiveData<List<Pokemon>> = _pokemons

    private val _currentPokemon = mutableListOf<Pokemon>()
    val currentPokemon = _currentPokemon


    fun setPokemon(pokemon : Pokemon){
        _currentPokemon.add(pokemon)
    }

    init {
        getPokemons()
    }


    fun getPokemons() {
        runBlocking{
            viewModelScope.launch {
                _status.value = PokemonApiStatus.LOADING
                try {
                    _pokemons.value = PokemonApi.retrofitService.getPokemonList().pokemon
                    _status.value = PokemonApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = PokemonApiStatus.ERROR
                    _pokemons.value = listOf()
                }
            }
        }
    }

}




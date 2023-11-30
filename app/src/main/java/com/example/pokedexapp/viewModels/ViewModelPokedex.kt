package com.example.pokedexapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SAVED_STATE_REGISTRY_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.network.models.Pokemon
import com.example.pokedexapp.network.services.PokemonApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

enum class PokemonApiStatus { LOADING, DONE, ERROR }



class ViewModelPokedex : ViewModel() {

    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus> = _status

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>> = _pokemons

    private val _currentPokemon = mutableListOf<Pokemon>()
    val currentPokemon = _currentPokemon

    init {
        getPokemons()
    }

    fun setPokemon(pokemon : Pokemon){
        _currentPokemon.add(pokemon)
    }

    private fun getPokemons() {
        viewModelScope.launch {
            _status.value = PokemonApiStatus.LOADING

            Log.d(
                "ResponseViewModelResult",
                PokemonApi.retrofitService.getPokemonList().pokemon.toString()
            )

            try {
                _pokemons.value = PokemonApi.retrofitService.getPokemonList().pokemon
                _status.value = PokemonApiStatus.DONE
            } catch (e: Exception) {
                _status.value = PokemonApiStatus.ERROR
            }
        }
    }

}




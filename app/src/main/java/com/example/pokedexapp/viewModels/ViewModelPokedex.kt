package com.example.pokedexapp.viewModels

import android.content.ContentProvider
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.network.models.Pokemon
import com.example.pokedexapp.network.services.PokemonApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

enum class PokemonApiStatus {DEFAULT, LOADING, DONE, ERROR }
enum class NetworkStatus{DEFAULT ,ERROR , CONNECTED}


class ViewModelPokedex : ViewModel() {

    private var _status = MutableLiveData<PokemonApiStatus>().apply {
        value = PokemonApiStatus.DEFAULT
    }
    val status: LiveData<PokemonApiStatus> = _status

    private var _statusNetwork = MutableLiveData<NetworkStatus>().apply {
        value = NetworkStatus.DEFAULT
    }
    val statusNetwork: LiveData<NetworkStatus> = _statusNetwork


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

    fun setStatus(status: PokemonApiStatus){
        _status.value = status
    }


    fun getPokemons() {
        runBlocking{
            viewModelScope.launch {
                _status.value = PokemonApiStatus.LOADING
                while (_status.value != PokemonApiStatus.DONE){
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

}




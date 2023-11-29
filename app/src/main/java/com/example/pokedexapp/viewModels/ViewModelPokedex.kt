package com.example.pokedexapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SAVED_STATE_REGISTRY_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.network.models.pokemon
import com.example.pokedexapp.network.services.PokemonApi
import kotlinx.coroutines.launch

enum class PokemonApiStatus{LOADING , DONE , ERROR}

class ViewModelPokedex : ViewModel() {

    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus> = _status

    private val _pokemons = MutableLiveData<List<pokemon>>()
    val pokemons: LiveData<List<pokemon>> = _pokemons

    private fun getMarsPhotos() {
        viewModelScope.launch {
            _status.value = PokemonApiStatus.LOADING
            try {
                _pokemons.value = PokemonApi.retrofitService.getData()
                _status.value = PokemonApiStatus.DONE
            } catch (e: Exception) {
                _status.value = PokemonApiStatus.ERROR

            }

        }
    }

}
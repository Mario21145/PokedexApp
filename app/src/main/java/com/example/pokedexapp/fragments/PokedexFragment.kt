package com.example.pokedexapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedexapp.R
import com.example.pokedexapp.adapters.PokedexItemAdapter
import com.example.pokedexapp.databinding.CustomNavBarBinding
import com.example.pokedexapp.databinding.FragmentHomeBinding
import com.example.pokedexapp.databinding.FragmentPokedexBinding
import com.example.pokedexapp.viewModels.ViewModelPokedex
import kotlinx.coroutines.launch

class PokedexFragment : Fragment() {

    private val sharedViewModel: ViewModelPokedex by activityViewModels()
    private lateinit var binding: FragmentPokedexBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Pokedex"
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokedex, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = this@PokedexFragment
        }
        Log.d("responsePokedexFragment", sharedViewModel.pokemons.value.toString())

        sharedViewModel.pokemons.observe(viewLifecycleOwner, Observer { _ ->
            binding.PokemonsRecyclerView.adapter =  PokedexItemAdapter(sharedViewModel) { selectedPokemon ->
                sharedViewModel.setPokemon(selectedPokemon)
                findNavController().navigate(R.id.action_pokedexFragment_to_pokedexItemFragment)
            }
        })


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("OnBackPressed", "Back key pressed in Fragment.")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.backFab.setOnClickListener {
            findNavController().navigate(R.id.action_pokedexFragment_to_homeFragment)
        }

    }
}


package com.example.pokedexapp.fragments

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pokedexapp.R
import com.example.pokedexapp.adapters.PokedexItemAdapter
import com.example.pokedexapp.databinding.FragmentPokedexBinding
import com.example.pokedexapp.viewModels.PokemonApiStatus
import com.example.pokedexapp.viewModels.ViewModelPokedex
import java.util.Locale
import kotlin.properties.Delegates


class PokedexFragment : Fragment() {

    private val sharedViewModel: ViewModelPokedex by activityViewModels()
    private lateinit var binding: FragmentPokedexBinding
    private var isExpanded by Delegates.notNull<Boolean>()

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

        val bounceAnimatorIcon = ObjectAnimator.ofFloat(
            binding.status, // For network icon error
            "translationY",
            0f,
            -30f,
            0f,
            15f,
            0f
        )
        bounceAnimatorIcon.duration = 1200

        if (sharedViewModel.pokemons.value!!.isEmpty()) {
            binding.PokemonsRecyclerView.visibility = View.GONE
            binding.status.visibility = View.VISIBLE
            bounceAnimatorIcon.start()
        } else {
            binding.status.visibility = View.GONE
        }

        sharedViewModel.pokemons.observe(viewLifecycleOwner, Observer { _ ->
            binding.PokemonsRecyclerView.adapter =
                PokedexItemAdapter(sharedViewModel) { selectedPokemon ->
                    if (sharedViewModel.status.value == PokemonApiStatus.DONE) {
                        sharedViewModel.setPokemon(selectedPokemon)
                        findNavController().navigate(R.id.action_pokedexFragment_to_pokedexItemFragment)
                    }
                }
        })


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.getPokemons()
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)


        binding.backFab.setOnClickListener {
            sharedViewModel.getPokemons()
            findNavController().navigate(R.id.action_pokedexFragment_to_homeFragment)
        }


        binding.searchPokemonBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    for (i in sharedViewModel.pokemons.value!!) {

                        if (i.id.toString().toLowerCase(Locale.ROOT).contains(query)) {
                            sharedViewModel.pokemons.value = listOf(i)
                        }

                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        binding.searchPokemonBox.setOnCloseListener {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            sharedViewModel.getPokemons()
            true
        }

    }
}




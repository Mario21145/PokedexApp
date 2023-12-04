package com.example.pokedexapp.fragments

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pokedexapp.R
import com.example.pokedexapp.adapters.PokedexItemAdapter
import com.example.pokedexapp.databinding.FragmentPokedexBinding
import com.example.pokedexapp.viewModels.PokemonApiStatus
import com.example.pokedexapp.viewModels.ViewModelPokedex
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
            binding.PokemonsRecyclerView.adapter =  PokedexItemAdapter(sharedViewModel) { selectedPokemon ->
                    if(sharedViewModel.status.value == PokemonApiStatus.DONE){
                        sharedViewModel.setPokemon(selectedPokemon)
                        findNavController().navigate(R.id.action_pokedexFragment_to_pokedexItemFragment)
                    }
            }
        })







        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)




        binding.backFab.setOnClickListener {
            findNavController().navigate(R.id.action_pokedexFragment_to_homeFragment)
        }




        binding.searchFab.setOnClickListener{
            val editText = binding.searchPokemonBox
            editText.visibility = if (editText.visibility == View.GONE) View.VISIBLE else View.GONE

            val bounceEditTextSearchPokemon= ObjectAnimator.ofFloat(
                binding.searchPokemonBox, // For pokemon Image
                "translationX",
                0f,
                30f,
                0f,
                -30f,
                0f
            )
             bounceEditTextSearchPokemon.duration = 500
             bounceEditTextSearchPokemon.start()

            val rotationSearchButton = ObjectAnimator.ofFloat(
                binding.searchFab,
                "rotation",
                0f,
                360f,
            )
            rotationSearchButton.duration = 500
            rotationSearchButton.start()
        }

        val adapter = PokedexItemAdapter(sharedViewModel) { selectedPokemon ->
            if (sharedViewModel.status.value == PokemonApiStatus.DONE) {
                sharedViewModel.setPokemon(selectedPokemon)
                findNavController().navigate(R.id.action_pokedexFragment_to_pokedexItemFragment)
            }
        }

        binding.searchPokemonBox.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.filter.filter(binding.searchPokemonBox.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })




    }



}




package com.example.pokedexapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pokedexapp.R
import com.example.pokedexapp.viewModels.PokemonApiStatus
import com.example.pokedexapp.viewModels.ViewModelPokedex
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {


    private val sharedViewModel: ViewModelPokedex by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonCountTextView: TextView = view.findViewById(R.id.pokemonCount)

        sharedViewModel.pokemons.observe(viewLifecycleOwner, Observer { pokemons ->
            if (pokemons.isNullOrEmpty()) {
                pokemonCountTextView.text = "0"
            } else {
                pokemonCountTextView.text = pokemons.size.toString()
            }
        })

        sharedViewModel.status.observe(viewLifecycleOwner, Observer {
                val button = view.findViewById<Button>(R.id.goPokedex)
                button.setOnClickListener {
                    findNavController().navigate(R.id.action_homeFragment_to_pokedexFragment)
                }

        })


        view.findViewById<FloatingActionButton>(R.id.infoFab).setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            val customView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog_info , null)
            builder.setView(customView)

            val dialog = builder.create()

            val btnCancel : Button = customView.findViewById(R.id.dismissDialog)

            btnCancel.setOnClickListener{
                dialog.dismiss()
            }

            dialog.show()
        }


    }

}
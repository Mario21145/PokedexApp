package com.example.pokedexapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pokedexapp.R
import com.example.pokedexapp.databinding.CustomNavBarBinding
import com.example.pokedexapp.databinding.FragmentHomeBinding
import com.example.pokedexapp.viewModels.ViewModelPokedex

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goPokedex.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pokedexFragment)
        }
    }

}
package com.example.pokedexapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.pokedexapp.R
import com.example.pokedexapp.databinding.CustomNavBarBinding
import com.example.pokedexapp.databinding.FragmentHomeBinding
import com.example.pokedexapp.databinding.FragmentPokedexBinding
import com.example.pokedexapp.viewModels.ViewModelPokedex
import kotlinx.coroutines.launch

class PokedexFragment : Fragment() {

    private val sharedViewModel: ViewModelPokedex by activityViewModels()
    private lateinit var binding: FragmentPokedexBinding
    private lateinit var customNavbarBinding: CustomNavBarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokedex, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customTopBar = view.findViewById<RelativeLayout>(R.id.customNavbar)

        customTopBar.findViewById<ImageButton>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }

    }
}
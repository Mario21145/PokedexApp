package com.example.pokedexapp.fragments.items

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pokedexapp.R
import com.example.pokedexapp.databinding.FragmentPokedexItemFinalBinding
import com.example.pokedexapp.databinding.FragmentPokedexItemPreviewBinding


class PokedexItemFragment : Fragment() {

    private lateinit var binding: FragmentPokedexItemPreviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokedex_item_preview, container, false)
        return binding.root
    }

}
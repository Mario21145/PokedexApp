package com.example.pokedexapp.fragments.items

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.pokedexapp.R
import com.example.pokedexapp.databinding.FragmentPokedexBinding
import com.example.pokedexapp.databinding.FragmentPokedexItemFinalBinding
import com.example.pokedexapp.viewModels.ViewModelPokedex

class PokedexInfoItemFragment : Fragment() {

    private val sharedViewModel: ViewModelPokedex by activityViewModels()
    private lateinit var binding: FragmentPokedexItemFinalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(false)
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokedex_item_final, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = sharedViewModel.currentPokemon
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                data.clear()
                Log.d("OnBackPressed", "Back key pressed in Fragment.")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)



        val typesText = data[0].types.joinToString("\n")
        if(data.isNotEmpty()){
            binding.PokemonImage.load(data[0].imageUrl){
                crossfade(true)
                placeholder(R.drawable.placeholder_item_loading)
                error(R.drawable.image_error)
            }
            binding.PokemonName.text = data[0].name
            binding.PokemonId.text = data[0].id.toString()
            binding.descriptionText.text = data[0].description
            binding.weightText.text = data[0].weight
            binding.heightText.text = data[0].height
            binding.hpText.text = data[0].hp.toString()
            binding.typeText.text = typesText
        }
        val pokemonSummary = getString(
            R.string.pokemon_details,
            data[0].id.toString(),
            data[0].name.toString(),
            typesText,
            data[0].weight.toString(),
            data[0].height.toString(),
            data[0].hp.toString(),
            data[0].description.toString(),
        )





        binding.backArrow.setOnClickListener{
            data.clear()
            findNavController().popBackStack()
        }

        binding.shareFab.setOnClickListener{
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, pokemonSummary)

            val chooserIntent = Intent.createChooser(shareIntent, "Share via")

            if (shareIntent.resolveActivity(requireContext().packageManager) != null) {
                requireContext().startActivity(chooserIntent)
            } else {
                Toast.makeText(context, "Error during the sharing of data", Toast.LENGTH_SHORT).show()
            }
        }



        //Animations
        val bounceAnimatorImage = ObjectAnimator.ofFloat(
            binding.PokemonImage, // For pokemon Image
            "Y",
            0f,
            -30f,
            0f,
            -15f,
            0f
        )
        bounceAnimatorImage.duration = 1500
        bounceAnimatorImage.start()

        val rotatorAnimator = ObjectAnimator.ofFloat(
            binding.shareFab, // For floating action bar share
            "Rotation",
            0f,
            -40f,
            0f,
            10f,
            0f
        )

        rotatorAnimator.duration = 1200
        rotatorAnimator.start()



    }

}
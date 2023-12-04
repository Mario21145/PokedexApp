package com.example.pokedexapp.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedexapp.R
import com.example.pokedexapp.network.models.Pokemon
import com.example.pokedexapp.viewModels.ViewModelPokedex
import java.util.Locale


class PokedexItemAdapter(
    private val viewModel: ViewModelPokedex,
    private val clickListener: (Pokemon) -> Unit
) :
    RecyclerView.Adapter<PokedexItemAdapter.PokemonviewHolder>() , Filterable {

    private val pokemonData: List<Pokemon> = viewModel.pokemons.value ?: emptyList()

    private var filteredPokemonData: List<Pokemon> = pokemonData

    class PokemonviewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val imageView = view!!.findViewById<ImageView>(R.id.PokemonImagePreview)
        val id = view!!.findViewById<TextView>(R.id.PokemonIdPreview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonviewHolder {
        val bind = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_pokedex_item_preview, parent, false)
        return PokemonviewHolder(bind)
    }

    override fun getItemCount(): Int {
        return pokemonData.size
    }

    override fun onBindViewHolder(holder: PokemonviewHolder, position: Int) {

        val pokemonItem = pokemonData[position]
        val pokemonImage = pokemonItem.imageUrl

        holder.imageView.load(pokemonImage) {
            crossfade(true)
            placeholder(R.drawable.placeholder_item_loading)
            error(R.drawable.image_error)
        }

        holder.imageView.setOnClickListener {
            clickListener(pokemonItem)
        }

        holder.id.text = pokemonItem.id.toString()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (query.isNullOrBlank()) {
                    filteredPokemonData = pokemonData
                } else {
                    filteredPokemonData = pokemonData.filter {
                        it.id.toString().contains(viewModel.filteredPokemon.value.toString())
                    }
                }

                filterResults.values = filteredPokemonData
                filterResults.count = filteredPokemonData.size
                return filterResults
            }

            override fun publishResults(query: CharSequence?, results: FilterResults?) {
                filteredPokemonData = results?.values as? List<Pokemon> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }


}

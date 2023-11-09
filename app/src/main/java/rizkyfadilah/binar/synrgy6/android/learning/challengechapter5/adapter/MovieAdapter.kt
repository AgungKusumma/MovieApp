package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.databinding.ItemMovieBinding
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.databinding.ItemMovieLoadingBinding
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network.ApiConfig
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network.MovieResponse
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.home.HomeFragmentDirections
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.utils.LoadingResultHandler

class MovieAdapter(
    private val list: MutableList<LoadingResultHandler<MovieResponse.Results>> = mutableListOf(),
) : RecyclerView.Adapter<ViewHolder>() {
    fun setListMovie(data: List<LoadingResultHandler.Content<MovieResponse.Results>>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun setLoading(data: List<LoadingResultHandler.Loading<MovieResponse.Results>>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : ViewHolder(binding.root) {
        fun bind(data: MovieResponse.Results) {
            val baseUrlImg = ApiConfig.BASE_URL_IMG
            val posterPath = data.posterPath
            val imgUrl = baseUrlImg + posterPath

            Glide.with(itemView)
                .load(imgUrl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(binding.ivMovie)

            itemView.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailHomeFragment(data.id)
                Navigation.findNavController(itemView).navigate(action)
            }
        }
    }

    inner class LoadingViewHolder(private val binding: ItemMovieLoadingBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            "Loading...".also { binding.tvTitle.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            0 -> {
                LoadingViewHolder(
                    ItemMovieLoadingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            1 -> {
                MovieViewHolder(
                    ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            else -> throw RuntimeException("No View...")
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position].data
        when (holder) {
            is MovieViewHolder -> {
                data?.let {
                    holder.bind(data)
                }
            }

            is LoadingViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is LoadingResultHandler.Loading -> 0
            is LoadingResultHandler.Content -> 1
        }
    }
}
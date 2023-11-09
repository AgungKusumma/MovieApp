package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.R
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.databinding.FragmentDetailHomeBinding
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserPreference
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.ViewModelFactory
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.dataStore
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network.ApiConfig
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network.ApiService
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network.MovieRepository
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.utils.LoadingResultHandler
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.utils.NetworkResult

class DetailHomeFragment : Fragment() {
    private lateinit var binding: FragmentDetailHomeBinding
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }
    private val apiService: ApiService by lazy {
        ApiConfig.instanceAPI
    }
    private val movieRepository: MovieRepository by lazy {
        MovieRepository(apiService)
    }
    private val mainDataViewModel: MainDataViewModel by viewModels {
        MainDataViewModel.Factory(movieRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }

    private fun setupData() {
        homeViewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                val destination =
                    DetailHomeFragmentDirections.actionDetailHomeFragmentToLoginFragment()
                findNavController().navigate(destination)
            }
        }

        val movieId = DetailHomeFragmentArgs.fromBundle(requireArguments()).movieId

        mainDataViewModel.detailDataMovie(movieId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    (result.data)?.let {
                        val baseUrlImg = ApiConfig.BASE_URL_IMG
                        val posterPath = it.posterPath
                        val imgUrl = baseUrlImg + posterPath

                        binding.apply {
                            Glide.with(requireActivity())
                                .load(imgUrl)
                                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                                .into(ivMovie)

                            tvTitle.text = it.originalTitle
                            tvAuthor.text = it.title
                            tvDate.text = it.releaseDate
                            tvOverview.text = it.overview
                        }
                        LoadingResultHandler.Content(it)
                    }
                }

                is NetworkResult.Loading -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.tv_loading),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Error -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.tv_movie_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.e("ERROR_MOVIE", result.message.orEmpty())
                }
            }
        }
    }
}
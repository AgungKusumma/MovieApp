package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.R
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.adapter.MovieAdapter
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.databinding.FragmentHomeBinding
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserPreference
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.ViewModelFactory
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.dataStore
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network.ApiConfig
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network.ApiService
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.network.MovieRepository
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.utils.LoadingResultHandler
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.utils.NetworkResult

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }
    private val adapter by lazy {
        MovieAdapter()
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
        setupAction()
    }

    private fun setupData() {
        binding.rvMovie.adapter = adapter

        homeViewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                val destination = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                findNavController().navigate(destination)
            }
            binding.tvUsername.text = getString(R.string.tv_home_welcome, user.name)
        }

        mainDataViewModel.dataMovie.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    binding.rvMovie.visibility = View.VISIBLE
                    binding.tvEmpty.visibility = View.GONE
                    (result.data)?.results?.let { list ->
                        adapter.setListMovie(list.map {
                            LoadingResultHandler.Content(it)
                        })
                    }
                }

                is NetworkResult.Loading -> {
                    adapter.setLoading(listOf(LoadingResultHandler.Loading()))
                }

                is NetworkResult.Error -> {
                    binding.rvMovie.visibility = View.GONE
                    binding.tvEmpty.visibility = View.VISIBLE
                    Snackbar.make(
                        binding.root,
                        result.message.orEmpty(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setupAction() {
        binding.ivProfile.setOnClickListener {
            val destination = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            findNavController().navigate(destination)
        }
    }
}
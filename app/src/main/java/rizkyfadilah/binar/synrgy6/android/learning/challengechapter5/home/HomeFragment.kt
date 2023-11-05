package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.R
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.databinding.FragmentHomeBinding
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserModel
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserPreference
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var user: UserModel
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
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

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        homeViewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                val destination = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                findNavController().navigate(destination)
            }
        }
    }

    private fun setupAction() {
        binding.ivProfile.setOnClickListener {
            homeViewModel.logout()
            Toast.makeText(context, getString(R.string.logout_success), Toast.LENGTH_LONG).show()
        }
    }
}
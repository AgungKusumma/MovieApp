package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.login

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
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.databinding.FragmentLoginBinding
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserModel
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserPreference
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var user: UserModel
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        loginViewModel.getUser().observe(viewLifecycleOwner) { user ->
            this.user = user
            if (user.isLogin) {
                val destination = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController().navigate(destination)
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etLoginEmail.text.toString()
                val password = etLoginPassword.text.toString()
                when {
                    email.isEmpty() -> etLoginEmail.error = getString(R.string.tv_login_email_hint)
                    password.isEmpty() -> etLoginPassword.error = getString(R.string.tv_login_password_hint)
                    email != user.email -> etLoginEmail.error = getString(R.string.tv_wrong_email)
                    password != user.password -> etLoginPassword.error = getString(R.string.tv_wrong_password)
                    else -> {
                        loginViewModel.login()

                        Toast.makeText(context, getString(R.string.login_success), Toast.LENGTH_LONG).show()

                        val destination = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        findNavController().navigate(destination)
                    }
                }
            }

            tvCreateAccount.setOnClickListener {
                val destination = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(destination)
            }
        }
    }
}
package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.R
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.databinding.FragmentProfileBinding
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserModel
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserPreference
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.ViewModelFactory
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.dataStore
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.home.HomeViewModel
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.register.RegisterViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
        setupAction()
    }

    private fun setupData() {
        homeViewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                val destination = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
                findNavController().navigate(destination)
            }
            binding.apply {
                etProfileUsername.setText(user.name)
                etProfileEmail.setText(user.email)
                etProfilePassword.setText(user.password)
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            ivSettingLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

            btnUpdate.setOnClickListener {
                val name = etProfileUsername.text.toString()
                val email = etProfileEmail.text.toString()
                val password = etProfilePassword.text.toString()
                when {
                    name.isEmpty() -> etProfileUsername.error =
                        getString(R.string.tv_register_username_hint)

                    email.isEmpty() -> etProfileEmail.error =
                        getString(R.string.tv_login_email_hint)

                    password.isEmpty() -> etProfilePassword.error =
                        getString(R.string.tv_login_password_hint)

                    else -> {
                        registerViewModel.saveUser(UserModel(name, email, password, true))
                        Toast.makeText(
                            context,
                            getString(R.string.update_success),
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                }
            }

            btnLogout.setOnClickListener {
                homeViewModel.logout()
                Toast.makeText(context, getString(R.string.logout_success), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}
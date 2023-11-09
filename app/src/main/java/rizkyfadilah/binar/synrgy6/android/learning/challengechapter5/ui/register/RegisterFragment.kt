package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.R
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.databinding.FragmentRegisterBinding
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserModel
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserPreference
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.ViewModelFactory
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.dataStore

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnRegister.setOnClickListener {
                val name = etRegisterUsername.text.toString()
                val email = etRegisterEmail.text.toString()
                val password = etRegisterPassword.text.toString()
                val confirmPassword = etRegisterConfirmPassword.text.toString()
                when {
                    name.isEmpty() -> etRegisterUsername.error =
                        getString(R.string.tv_register_username_hint)

                    email.isEmpty() -> etRegisterEmail.error =
                        getString(R.string.tv_login_email_hint)

                    password.isEmpty() -> etRegisterPassword.error =
                        getString(R.string.tv_login_password_hint)

                    confirmPassword.isEmpty() -> etRegisterConfirmPassword.error =
                        getString(R.string.tv_register_confirm_password_hint)

                    password != confirmPassword -> {
                        etRegisterPassword.error = getString(R.string.tv_wrong_validation_password)
                        etRegisterConfirmPassword.error = getString(R.string.tv_wrong_validation_password)
                    }

                    else -> {
                        registerViewModel.saveUser(UserModel(name, email, password, false))
                        Toast.makeText(
                            context,
                            getString(R.string.register_success),
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                }
            }

            tvAlreadyHaveAnAccount.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.home.HomeViewModel
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.login.LoginViewModel
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.register.RegisterViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
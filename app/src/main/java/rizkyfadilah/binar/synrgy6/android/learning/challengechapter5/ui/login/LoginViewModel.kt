package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserModel
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.model.UserPreference

class LoginViewModel(private val pref: UserPreference): ViewModel() {
    fun getUser(): LiveData<UserModel>{
        return pref.getUser().asLiveData()
    }

    fun login(){
        viewModelScope.launch {
            pref.login()
        }
    }
}
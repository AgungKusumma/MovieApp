package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
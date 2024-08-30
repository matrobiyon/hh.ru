package tj.example.effectivemobile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import tj.example.effectivemobile.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var hasEnteredAccount = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Navigation view getting from layout
        val bottomNavView = binding.bottomNavView
        // Getting my Nav Host and setting to it a Nav Controller
        val navFragment =
            supportFragmentManager.findFragmentById(binding.fragmentsContainer.id) as NavHostFragment
        val navController = navFragment.navController


        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.search -> {
                    checkIfEntered(
                        hasEnteredAccount,
                        navController,
                        R.id.searchFragment
                    )
                }
                R.id.favourite -> {
                    checkIfEntered(hasEnteredAccount, navController, R.id.favouriteFragment)
                }

                else -> false
            }
        }
    }
}


fun checkIfEntered(
    hasEnteredAccount: Boolean,
    navController: NavController,
    fragmentId: Int
): Boolean {

    if (hasEnteredAccount) {
        navController.popBackStack(navController.currentDestination?.id ?: fragmentId, true)
        navController.navigate(fragmentId)
    } else {
        navController.popBackStack(navController.currentDestination?.id ?: R.id.authFragment, true)
        navController.navigate(R.id.authFragment)
    }
    return true
}

package uz.pdp.provalyutakurslari

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mxn.soul.flowingdrawer_core.ElasticDrawer
import uz.pdp.provalyutakurslari.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            drawerlayout.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL)

        }


        setupSmoothBottomMenu()
    }


    private fun setupSmoothBottomMenu() {
        navController = findNavController(R.id.nav_host_fragment_container)
//        setupActionBarWithNavController(navController)
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.my_menu)
        val menu = popupMenu.menu

        binding.bottomNavigation.setupWithNavController(menu, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun openMenu() {
        binding.apply {
            drawerlayout.openMenu()
            share.setOnClickListener {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://t.me/Allaberganov_portfolio");
                startActivity(
                    Intent.createChooser(
                        shareIntent,
                        "https://t.me/Allaberganov_portfolio"
                    )
                )

                drawerlayout.closeMenu()
            }
            info.setOnClickListener {
                Toast.makeText(this@MainActivity, "Hozircha ma'lumotlar yoq ))", Toast.LENGTH_SHORT)
                    .show()
                drawerlayout.closeMenu()
            }
        }
    }

}

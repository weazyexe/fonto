package dev.weazyexe.fonto.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import dev.weazyexe.fonto.ui.features.NavGraphs
import dev.weazyexe.fonto.ui.theme.FontoTheme

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            FontoTheme {
                val homeNavController = rememberNavController()
                DestinationsNavHost(
                    navGraph = NavGraphs.home,
                    modifier = Modifier.fillMaxSize(),
                    navController = homeNavController
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FontoTheme {
        Greeting("Android")
    }
}
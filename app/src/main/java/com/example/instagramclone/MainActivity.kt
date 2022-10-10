package com.example.instagramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instagramclone.auth.LoginScreen
import com.example.instagramclone.auth.ProfileScreen
import com.example.instagramclone.auth.SignupScreen
import com.example.instagramclone.main.FeedScreen
import com.example.instagramclone.main.MyPostsScreen
import com.example.instagramclone.main.NotificationMessage
import com.example.instagramclone.main.SearchScreen
import com.example.instagramclone.ui.theme.InstagramCloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   InstagramApp()
                }
            }
        }
    }
}

sealed class DestinationScreens(val route: String){
    object Signup: DestinationScreens("signup")
    object Login: DestinationScreens("login")
    object Feed: DestinationScreens("feed")
    object Search: DestinationScreens("search")
    object MyPosts: DestinationScreens("myposts")
    object Profile: DestinationScreens("profile")
}

@Composable
fun InstagramApp(){
    //in order to validate the view model
    val vm = hiltViewModel<IgViewModel>()
    val navController = rememberNavController()

    NotificationMessage(vm = vm)
    
    NavHost(navController = navController, startDestination = DestinationScreens.Signup.route) {
        composable(DestinationScreens.Signup.route) {
            SignupScreen(navController = navController, vm = vm)
        }

        composable(DestinationScreens.Login.route){
            LoginScreen(navController = navController, vm = vm)
        }

        composable(DestinationScreens.Feed.route){
            FeedScreen(navController = navController, vm = vm)
        }

        composable(DestinationScreens.Search.route){
            SearchScreen(navController = navController, vm = vm)
        }

        composable(DestinationScreens.MyPosts.route){
            MyPostsScreen(navController = navController, vm = vm)
        }

        composable(DestinationScreens.Profile.route){
            ProfileScreen(navController = navController, vm = vm)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InstagramCloneTheme {
       InstagramApp()
    }
}
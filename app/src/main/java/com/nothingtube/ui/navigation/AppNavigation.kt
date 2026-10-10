package com.nothingtube.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.nothingtube.NothingTubeApp
import com.nothingtube.data.model.VideoItem
import com.nothingtube.ui.components.NothingBottomBar
import com.nothingtube.ui.favorites.FavoritesScreen
import com.nothingtube.ui.history.HistoryScreen
import com.nothingtube.ui.home.HomeScreen
import com.nothingtube.ui.music.MusicScreen
import com.nothingtube.ui.player.PlayerScreen
import com.nothingtube.ui.search.SearchScreen
import com.nothingtube.ui.settings.SettingsScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val app = context.applicationContext as NothingTubeApp
    val repository = app.repository
    val scope = rememberCoroutineScope()

    var apiKey by remember { mutableStateOf("") }
    var favorites by remember { mutableStateOf<List<com.nothingtube.data.local.VideoEntity>>(emptyList()) }
    var history by remember { mutableStateOf<List<com.nothingtube.data.local.VideoEntity>>(emptyList()) }
    var currentVideo by remember { mutableStateOf<VideoItem?>(null) }

    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("nothing_tube", 0)
        apiKey = prefs.getString("api_key", "") ?: ""
    }

    LaunchedEffect(Unit) {
        scope.launch { repository.favorites().collect { favorites = it } }
    }
    LaunchedEffect(Unit) {
        scope.launch { repository.history().collect { history = it } }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column(Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f)) {
            NavHost(navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        onSearch = { navController.navigate("search") },
                        onOpenVideo = { _, _, _, _ -> },
                        onOpenFavorites = { navController.navigate("favorites") },
                        onOpenHistory = { navController.navigate("history") }
                    )
                }
                composable("search") {
                    SearchScreen(
                        apiKey = apiKey,
                        onSearch = { q, music -> repository.search(q, apiKey, music) },
                        onOpenVideo = { item ->
                            scope.launch { repository.markOpened(item) }
                            currentVideo = item
                            navController.navigate("player")
                        }
                    )
                }
                composable("player") {
                    currentVideo?.let { item ->
                        PlayerScreen(item = item, onBack = { navController.popBackStack() })
                    }
                }
                composable("favorites") {
                    FavoritesScreen(
                        favorites = favorites,
                        onOpen = { entity ->
                            val item = VideoItem(entity.videoId, entity.title, entity.channel, entity.thumbnail, entity.duration)
                            scope.launch { repository.markOpened(item) }
                            currentVideo = item
                            navController.navigate("player")
                        },
                        onRemove = { entity ->
                            scope.launch {
                                val item = VideoItem(entity.videoId, entity.title, entity.channel, entity.thumbnail, entity.duration)
                                repository.toggleFavorite(item, false)
                            }
                        }
                    )
                }
                composable("history") {
                    HistoryScreen(
                        history = history,
                        onOpen = { entity ->
                            val item = VideoItem(entity.videoId, entity.title, entity.channel, entity.thumbnail, entity.duration)
                            scope.launch { repository.markOpened(item) }
                            currentVideo = item
                            navController.navigate("player")
                        },
                        onClear = { scope.launch { repository.clearHistory() } }
                    )
                }
                composable("music") {
                    MusicScreen(apiKey = apiKey, onOpenSearch = { navController.navigate("search") })
                }
                composable("settings") {
                    SettingsScreen(
                        apiKey = apiKey,
                        onApiKeyChange = { key ->
                            apiKey = key
                            context.getSharedPreferences("nothing_tube", 0).edit()
                                .putString("api_key", key).apply()
                        }
                    )
                }
            }
        }
        NothingBottomBar(currentRoute = currentRoute, onNavigate = { route ->
            navController.navigate(route) {
                popUpTo("home") { inclusive = route == "home" }
                launchSingleTop = true
            }
        })
    }
}

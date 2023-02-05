package com.example.bobrovsky

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.bobrovsky.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel() {

    fun bindNavController(navController: NavController) {
        navigator.bind(navController)
    }

    fun unbindNavController() {
        navigator.unbind()
    }
}
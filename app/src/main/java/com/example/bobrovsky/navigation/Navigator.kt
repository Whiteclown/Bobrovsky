package com.example.bobrovsky.navigation

import androidx.navigation.NavController
import com.example.bobrovsky.R
import com.example.details.presentation.DetailsRouter
import com.example.details.ui.DetailsFragment
import com.example.list.presentation.ListRouter

class Navigator : ListRouter, DetailsRouter {

    private var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun unbind() {
        navController = null
    }

    override fun routeToDetail(filmId: Int, isFavorite: Boolean) {
        navController?.navigate(
            R.id.action_listFragment_to_detailsFragment,
            DetailsFragment.createBundle(filmId, isFavorite)
        )
    }

    override fun routeBack() {
        navController?.popBackStack()
    }
}
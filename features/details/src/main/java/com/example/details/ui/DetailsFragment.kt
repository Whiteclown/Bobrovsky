package com.example.details.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import coil.load
import coil.request.CachePolicy
import coil.size.Scale
import com.example.details.R
import com.example.details.databinding.FragmentDetailsBinding
import com.example.details.presentation.DetailsAction
import com.example.details.presentation.DetailsState
import com.example.details.presentation.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        initObservers()
        binding.btnBack.setOnClickListener {
            viewModel.routeBack()
        }
        viewModel.load()
    }

    private fun initObservers() {
        viewModel.state.onEach(::render).launchIn(viewModel.viewModelScope)
        viewModel.actions.onEach(::handleAction).launchIn(viewModel.viewModelScope)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fullScreenOn()
    }

    private fun render(state: DetailsState) {
        when (state) {
            is DetailsState.Loading -> {
                binding.loadingView.root.visibility = View.VISIBLE
            }

            is DetailsState.Content -> {
                binding.apply {
                    ivLogo.scaleType = ImageView.ScaleType.CENTER_CROP
                    ivLogo.load(state.film.posterUrl) {
                        memoryCachePolicy(CachePolicy.ENABLED)
                        diskCachePolicy(CachePolicy.ENABLED)
                        scale(Scale.FILL)
                    }
                    tvTitle.text = state.film.nameRu
                    tvDescription.text = state.film.description
                    val listOfGenres = state.film.genres.joinToString(", ") { it.genre }
                    tvGenres.text = getString(R.string.genres, listOfGenres)
                    val listOfCountries = state.film.countries.joinToString(", ") { it.country }
                    tvCountries.text = getString(R.string.countries, listOfCountries)
                    loadingView.root.visibility = View.GONE
                }
            }
        }
    }

    private fun fullScreenOff() {
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun fullScreenOn() {
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        requireActivity().window.statusBarColor = Color.TRANSPARENT
    }

    override fun onDetach() {
        fullScreenOff()
        super.onDetach()
    }

    private fun handleAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.ShowError   -> {
                val message = SpannableString(action.message)
                message.setSpan(
                    ForegroundColorSpan(Color.BLACK),
                    0,
                    message.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                context?.let {
                    AlertDialog
                        .Builder(it)
                        .setTitle(getString(R.string.error_dialog_title))
                        .setMessage(message)
                        .setNeutralButton(getString(R.string.error_dialog_neutral_button_text)) { _, _ -> }
                        .show()
                }
            }
        }
    }

    companion object {

        private const val FILM_ID = "filmId"
        private const val IS_FAVORITE = "isFavorite"

        fun createBundle(filmId: Int, isFavorite: Boolean) =
            bundleOf(
                FILM_ID to filmId,
                IS_FAVORITE to isFavorite
            )
    }
}
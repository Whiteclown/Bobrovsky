package com.example.list.ui

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.example.list.R
import com.example.list.databinding.FragmentListBinding
import com.example.list.presentation.ListAction
import com.example.list.presentation.ListState
import com.example.list.presentation.ListViewModel
import com.example.list.ui.adapter.FilmsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ListViewModel by viewModels()

    private val filmsAdapter = FilmsAdapter(
        onItemClicked = { viewModel.routeToDetail(it) },
        onItemLongClicked = {
            notifyAdapter()
            viewModel.changeFavorite(it)
        }
    )

    private var favorite = false
    private var filtered = false
    private var template = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)
        initRV()
        initObservers()
        initSearchBar()
        viewModel.load()

        binding.btnPopular.setOnClickListener {
            binding.toolbar.title = "Популярные"
            favorite = false
            filterFavorite()
        }
        binding.btnFavorite.setOnClickListener {
            binding.toolbar.title = "Избранное"
            favorite = true
            filterFavorite()
        }
    }

    private fun initObservers() {
        viewModel.state.onEach(::render).launchIn(viewModel.viewModelScope)
        viewModel.actions.onEach(::handleAction).launchIn(viewModel.viewModelScope)
    }

    private fun notifyAdapter() {
        filmsAdapter.notifyDataSetChanged()
    }

    private fun initRV() {
        binding.rvFilms.adapter = filmsAdapter
    }

    private fun render(state: ListState) {
        when (state) {
            is ListState.Loading -> {
                binding.viewLoading.root.visibility = View.VISIBLE
            }

            is ListState.Content -> {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        val data = state.data.map {
                            if (filtered) {
                                it.visibility = it.nameRu.lowercase().contains(template.lowercase())
                            }
                            if (favorite && !it.isFavorite) {

                                it.visibility = false
                            }
                            return@map it
                        }
                        filmsAdapter.submitData(data)
                    }
                }
                binding.viewLoading.root.visibility = View.GONE
            }
        }
    }

    private fun initSearchBar() {
        binding.toolbar.title = getString(R.string.popular)
        binding.toolbar.inflateMenu(R.menu.menu_search)
        val searchItem: MenuItem = binding.toolbar.menu.findItem(R.id.actionSearch)

        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                searchFilter(msg)
                return false
            }
        })
    }

    private fun filterFavorite() {
        if (favorite) {
            filmsAdapter.snapshot().map {
                it?.visibility = it?.isFavorite == favorite
            }
        } else {
            filmsAdapter.snapshot().map { it?.visibility = true }
        }
        filmsAdapter.notifyDataSetChanged()
    }

    private fun searchFilter(temp: String) {
        if (temp != "") {
            template = temp
            filmsAdapter.snapshot().map {
                if (!favorite) {
                    it?.visibility = (it?.nameRu?.lowercase()?.contains(temp.lowercase()) ?: false)
                } else {
                    it?.visibility = (it?.nameRu?.lowercase()?.contains(temp.lowercase()) ?: false) && it?.isFavorite == true
                }
            }
            filtered = true
        } else {
            if (!favorite) {
                filmsAdapter.snapshot().map { it?.visibility = true }
            } else {
                filmsAdapter.snapshot().map { it?.visibility = it?.isFavorite == favorite }
            }

            filtered = false
        }
        filmsAdapter.notifyDataSetChanged()
    }

    private fun handleAction(action: ListAction) {
        when (action) {
            is ListAction.ShowError   -> {
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
}
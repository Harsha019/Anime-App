package com.anime.app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anime.app.R
import com.anime.app.data.api.ApiClient
import com.anime.app.data.db.AnimeDatabase
import com.anime.app.data.db.AnimeEntity
import com.anime.app.data.repository.AnimeRepositoryImpl
import com.anime.app.databinding.FragmentAnimeListBinding
import com.anime.app.ui.adapters.AnimeAdapter
import com.anime.app.ui.factory.AppViewModelFactory
import com.anime.app.ui.viewmodel.AnimeViewModel
import com.anime.app.utils.Resource
import kotlinx.coroutines.launch

class AnimeListFragment : Fragment(R.layout.fragment_anime_list) {

    private lateinit var viewModel: AnimeViewModel
    private lateinit var binding: FragmentAnimeListBinding
    private lateinit var adapter: AnimeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAnimeListBinding.bind(view)

        initViewModel()
        setupRecycler()
        observeData()

        viewModel.loadAnime()
    }

    private fun initViewModel() {
        val repository = AnimeRepositoryImpl(
            ApiClient.api,
            AnimeDatabase.getInstance(requireContext()).animeDao(),
            requireContext()
        )

        viewModel = ViewModelProvider(this,
            AppViewModelFactory(
                AnimeViewModel::class.java,
                repository
            ) )[AnimeViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        requireActivity().title = "Top Anime"
    }

    private fun setupRecycler() {
        adapter = AnimeAdapter { anime ->
            val action = AnimeListFragmentDirections.actionAnimeListFragmentToAnimeDetailFragment(anime.id)
            findNavController().navigate(action)
        }

        binding.rvAnime.adapter = adapter
        binding.rvAnime.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.anime.collect { result ->
                when (result) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> showList(result.data)
                    is Resource.Error -> showError(result.message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvAnime.visibility = View.GONE
        binding.tvError.visibility = View.GONE
    }

    private fun showList(data: List<AnimeEntity>) {
        binding.progressBar.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.rvAnime.visibility = View.VISIBLE
        adapter.submitList(data)
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.rvAnime.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = message
    }
}


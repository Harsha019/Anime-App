package com.anime.app.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.anime.app.R
import com.anime.app.data.api.ApiClient
import com.anime.app.data.db.AnimeDatabase
import com.anime.app.data.model.response.AnimeDetailResponse
import com.anime.app.data.repository.AnimeRepositoryImpl
import com.anime.app.databinding.FragmentAnimeDetailBinding
import com.anime.app.ui.factory.AppViewModelFactory
import com.anime.app.ui.viewmodel.AnimeDetailViewModel
import com.anime.app.utils.Resource
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch


class AnimeDetailFragment : Fragment(R.layout.fragment_anime_detail) {

    private lateinit var binding: FragmentAnimeDetailBinding
    private lateinit var viewModel: AnimeDetailViewModel
    private val args: AnimeDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAnimeDetailBinding.bind(view)

        initViewModel()
        observeData()

        viewModel.loadAnimeDetail(args.animeId)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().title = "Anime Details"
    }

    private fun initViewModel() {

        val repository = AnimeRepositoryImpl(
            ApiClient.api,
            AnimeDatabase.getInstance(requireContext()).animeDao(),
            requireContext()
        )

        val factory = AppViewModelFactory(
            AnimeDetailViewModel::class.java,
            repository
        )

        viewModel = ViewModelProvider(this, factory).get(AnimeDetailViewModel::class.java)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.detail.collect { result ->
                when (result) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> showData(result.data)
                    is Resource.Error -> showError(result.message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.contentLayout.visibility = View.GONE
        binding.tvError.visibility = View.GONE
    }

    private fun showData(data: AnimeDetailResponse) {
        binding.progressBar.visibility = View.GONE
        binding.contentLayout.visibility = View.VISIBLE

        val anime = data.data

        binding.tvTitle.text = anime.title
        binding.tvRating.text = "Rating: ${anime.score ?: "N/A"}"
        binding.tvEpisodes.text = "Episodes: ${anime.episodes ?: "N/A"}"
        binding.tvGenres.text = anime.genres?.joinToString { it.name } ?: "N/A"
        binding.tvSynopsis.text = anime.synopsis ?: "No description"

        val trailerUrl = anime.trailer?.embedUrl
        Log.d("TRAILER_TEST", "embedUrl = $trailerUrl")


        if (!trailerUrl.isNullOrBlank()) {

            binding.webTrailer.visibility = View.VISIBLE
            binding.imgPoster.visibility = View.GONE

            binding.webTrailer.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
            }

            binding.webTrailer.webChromeClient = WebChromeClient()
            binding.webTrailer.webViewClient = WebViewClient()

            binding.webTrailer.loadUrl(trailerUrl)

        } else {
            binding.webTrailer.visibility = View.GONE
            binding.imgPoster.visibility = View.VISIBLE

            Glide.with(this)
                .load(anime.images?.jpg?.imageUrl)
                .into(binding.imgPoster)
        }
    }


    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.contentLayout.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = message
    }
}

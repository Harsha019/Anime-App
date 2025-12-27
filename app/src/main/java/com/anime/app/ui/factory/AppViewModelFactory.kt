package com.anime.app.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anime.app.domain.repository.AnimeRepository

class AppViewModelFactory<T : ViewModel>(
    private val clazz: Class<T>,
    private val repository: AnimeRepository
) : ViewModelProvider.Factory {

    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
        if (!modelClass.isAssignableFrom(clazz)) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        return clazz.getConstructor(
            AnimeRepository::class.java
        ).newInstance(repository) as VM
    }
}




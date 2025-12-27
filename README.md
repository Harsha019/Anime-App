# Anime App (Jikan API)

An Android application that displays a list of popular anime and detailed information using the **Jikan API**.  
Built using **MVVM architecture**, **Room**, **Retrofit**, and **StateFlow**, with offline support and clean separation of concerns.

--------------------------------------------------------------------------------------------------------------------------------------

# Features

# Anime List Screen
- Fetches top anime from Jikan API
- Displays:
  - Poster image
  - Title
  - Rating
  - Number of episodes
- Smooth scrolling using RecyclerView

# Anime Detail Screen
- Shows:
  - Anime title
  - Synopsis
  - Genres
  - Episodes count
  - Rating
- Trailer support (WebView)
- Fallback to poster image if trailer not available

# Offline Support
- Anime list cached using **Room Database**
- Data loaded from local DB when offline
- Syncs automatically when network is available

# Error Handling
- Network failure handling
- Empty state UI
- Graceful fallback for missing data

---

# Architecture

This project follows **MVVM (Model–View–ViewModel)** architecture.


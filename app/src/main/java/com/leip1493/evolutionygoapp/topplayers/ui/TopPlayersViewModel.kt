package com.leip1493.evolutionygoapp.topplayers.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leip1493.evolutionygoapp.core.models.Player
import com.leip1493.evolutionygoapp.topplayers.data.TopPlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopPlayersViewModel @Inject constructor(
    private val topPlayersRepository: TopPlayersRepository,
) : ViewModel() {

    private val _players = MutableLiveData<List<Player>>()
    val players: LiveData<List<Player>> = _players

    private val _seasons = MutableLiveData<List<String>>()
    val seasons: LiveData<List<String>> = _seasons

    private val _banlist = MutableLiveData<List<String>>()
    val banlist: LiveData<List<String>> = _banlist

    init {
        getPlayers()
        getSeasons()
        getBanlist()
    }

    private fun getPlayers() {
        viewModelScope.launch {
            _players.value = topPlayersRepository.getPlayers()
        }
    }

    private fun getSeasons() {
        viewModelScope.launch {
            _seasons.value = topPlayersRepository.getSeasons()
        }
    }

    private fun getBanlist() {
        viewModelScope.launch {
            _banlist.value = topPlayersRepository.getBanlist()
        }
    }
}
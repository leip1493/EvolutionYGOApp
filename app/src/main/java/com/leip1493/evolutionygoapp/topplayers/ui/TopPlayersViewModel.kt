package com.leip1493.evolutionygoapp.topplayers.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leip1493.evolutionygoapp.core.models.Player
import com.leip1493.evolutionygoapp.core.models.Season
import com.leip1493.evolutionygoapp.topplayers.data.TopPlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopPlayersViewModel @Inject constructor(
    private val topPlayersRepository: TopPlayersRepository,
) : ViewModel() {

    private val _isLoadingPlayers = MutableLiveData<Boolean>()
    val isLoadingPlayers: LiveData<Boolean> = _isLoadingPlayers

    private val _isLoadingBanlist = MutableLiveData<Boolean>()
    val isLoadingBanlist: LiveData<Boolean> = _isLoadingBanlist

    private val _players = MutableLiveData<List<Player>>()
    val players: LiveData<List<Player>> = _players

    private val _seasons = MutableLiveData<List<Season>>()
    val seasons: LiveData<List<Season>> = _seasons

    private val _banlist = MutableLiveData<List<String>>()
    val banlist: LiveData<List<String>> = _banlist

    private val _selectedSeason = MutableLiveData(Season(4, "Season 4"))
    val selectedSeason: LiveData<Season> = _selectedSeason

    private val _selectedBanlist = MutableLiveData("Global")
    val selectedBanlist: LiveData<String> = _selectedBanlist

    init {
        getPlayers()
        getSeasons()
        getBanlist()
    }

    fun getPlayers() {
        viewModelScope.launch {
            _isLoadingPlayers.value = true
            _players.value =
                topPlayersRepository.getPlayers(
                    _selectedSeason.value!!,
                    _selectedBanlist.value!!
                )
            _isLoadingPlayers.value = false
        }
    }

    fun getSeasons() {
        viewModelScope.launch {
            _seasons.value = topPlayersRepository.getSeasons()
        }
    }

    fun getBanlist() {
        viewModelScope.launch {
            _isLoadingBanlist.value = true
            _banlist.value = topPlayersRepository.getBanlist()
            _isLoadingBanlist.value = false
        }
    }

    fun selectSeason(season: Season) {
        _selectedSeason.value = season
        getPlayers()
    }

    fun selectBanlist(banlist: String) {
        _selectedBanlist.value = banlist
        getPlayers()
    }

}
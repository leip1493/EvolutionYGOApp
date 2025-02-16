package com.leip1493.evolutionygoapp.playerdetail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leip1493.evolutionygoapp.core.models.Match
import com.leip1493.evolutionygoapp.core.models.Player
import com.leip1493.evolutionygoapp.playerdetail.data.PlayerDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    private val playerDetailRepository: PlayerDetailRepository,
) : ViewModel() {

    private var _playerStats = MutableLiveData<Player>()
    val playerStats: LiveData<Player> = _playerStats

    private var _isLoadingPlayerStats = MutableLiveData<Boolean>()
    val isLoadingPlayerStats: LiveData<Boolean> = _isLoadingPlayerStats

    private var _playerMatches = MutableLiveData<List<Match>>()
    val playerMatches: LiveData<List<Match>> = _playerMatches

    private var _isLoadingPlayerMatches = MutableLiveData<Boolean>()
    val isLoadingPlayerMatches: LiveData<Boolean> = _isLoadingPlayerMatches

    fun getPlayerStats(playerId: String, season: String, banlist: String) {
        viewModelScope.launch {
            _isLoadingPlayerStats.value = true
            _playerStats.value = playerDetailRepository.getPlayerStats(playerId, season, banlist)
            _isLoadingPlayerStats.value = false
        }
    }

    fun getPlayerMatches(playerId: String, season: String, banlist: String) {
        viewModelScope.launch {
            _isLoadingPlayerMatches.value = true
            _playerMatches.value =
                playerDetailRepository.getPlayerMatches(1, 100, playerId, season, banlist)
            _isLoadingPlayerMatches.value = false
        }
    }

}
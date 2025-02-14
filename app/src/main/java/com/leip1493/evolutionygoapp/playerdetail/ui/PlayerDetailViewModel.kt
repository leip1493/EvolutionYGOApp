package com.leip1493.evolutionygoapp.playerdetail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun getPlayerStats(playerId: String, season: String, banlist: String) {
        viewModelScope.launch {
            _playerStats.value = playerDetailRepository.getPlayerStats(playerId, season, banlist)
        }
    }

}
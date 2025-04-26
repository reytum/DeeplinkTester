package com.deeplink_tester.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deeplink_tester.data.models.Category
import com.deeplink_tester.domain.repo.DeeplinkRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import multiplatform.network.cmptoast.showToast

class DeeplinkViewModel : ViewModel() {
    private val _state = MutableStateFlow(DeeplinkState())
    val uiState: StateFlow<DeeplinkState> = _state.asStateFlow()

    fun fetchDeepLinks() {
        viewModelScope.launch {
            val data = DeeplinkRepo.fetchDeepLinks()
            if (data.isNotEmpty()) {
                showToast("Deeplinks updated from remote")
                _state.value = DeeplinkState(
                    categories = data
                )
            }
        }
    }
}

data class DeeplinkState(
    val categories: List<Category> = DeeplinkRepo.getInitialDeepLinks()
)
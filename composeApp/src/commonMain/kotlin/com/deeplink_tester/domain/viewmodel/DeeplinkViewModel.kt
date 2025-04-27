package com.deeplink_tester.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deeplink_tester.PlatformState
import com.deeplink_tester.data.models.Category
import com.deeplink_tester.domain.repo.DeeplinkRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//import multiplatform.network.cmptoast.showToast

class DeeplinkViewModel : ViewModel() {
    private val _state = MutableStateFlow(DeeplinkState())
    val uiState: StateFlow<DeeplinkState> = _state.asStateFlow()

    fun fetchDeepLinks(platformState: PlatformState) {
        val data = DeeplinkRepo.getLocalDeepLinks(platformState)
        if (data.isNotEmpty()) {
            _state.value = DeeplinkState(
                categories = data,
                isUpdateReceived = false
            )
        }

        viewModelScope.launch {
            val remoteData = DeeplinkRepo.fetchDeepLinks(platformState)
            if (remoteData.isNotEmpty()) {
                //showToast("Deeplinks updated from remote")
                _state.value = DeeplinkState(
                    categories = remoteData,
                    isUpdateReceived = true
                )
            }
        }
    }

    fun updateDeepLink(index: Int, newUrl: String, category: Category, baseUrl: String) {
        _state.value = _state.value.copy(
            categories = _state.value.categories.map { cat ->
                if (cat == category) {
                    cat.copy(
                        deepLinks = cat.deepLinks.mapIndexed { i, deeplink ->
                            if (i == index) {
                                deeplink.copy(url = newUrl)
                            } else {
                                deeplink
                            }
                        }
                    )
                } else {
                    cat
                }
            }
        )
    }
}

data class DeeplinkState(
    val categories: List<Category> = emptyList(),
    val isUpdateReceived: Boolean = false
)
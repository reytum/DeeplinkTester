package com.deeplink_tester.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deeplink_tester.data.models.Category
import com.deeplink_tester.data.models.Deeplink
import com.deeplink_tester.domain.repo.DeeplinkRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//import multiplatform.network.cmptoast.showToast

class DeeplinkViewModel : ViewModel() {
    private val _state = MutableStateFlow(DeeplinkState())
    val uiState: StateFlow<DeeplinkState> = _state.asStateFlow()

    fun fetchDeepLinks() {
        viewModelScope.launch {
            val data = DeeplinkRepo.fetchDeepLinks()
            if (data.isNotEmpty()) {
                //showToast("Deeplinks updated from remote")
                _state.value = DeeplinkState(
                    categories = data
                )
            }
        }
    }

    /*fun updateDeepLink(index: Int, deeplink: String, category: Category, selectedBaseUrl: String) {
        val currentDeepLinks = category.deepLinks.toMutableList()
        val preUpdateDeeplink = category.deepLinks[index]
        currentDeepLinks[index] = Deeplink(preUpdateDeeplink.name, deeplink)
        val updatedCategory = category.copy(deepLinks = currentDeepLinks)
        val updatedCategories = _state.value.categories.toMutableList()
        var index = -1
        _state.value.categories.forEachIndexed { index1, it ->
            if (category.name == category.name) {
                index = index1
            }
        }
        if (index != -1) {
            updatedCategories[index] = updatedCategory
            _state.value = _state.value.copy(categories = updatedCategories)
        }
    }*/

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
    val categories: List<Category> = DeeplinkRepo.getInitialDeepLinks()
)
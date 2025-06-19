package com.amsterdam.cutetudee.presentation.screens.category

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.repository.CategoryService
import com.amsterdam.cutetudee.presentation.utils.UriToBitmapString
import com.amsterdam.cutetudee.presentation.utils.ValidateImageSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(kotlin.uuid.ExperimentalUuidApi::class)
class CategoryViewModel(
    private val service: CategoryService,
    private val validateImageSize: ValidateImageSize,
    private val uriToBitmapString: UriToBitmapString
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryUiState())
    val state = _state.asStateFlow()

    // region Add Category

    fun updateCategoryName(name: String){
        _state.update {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    name = name,
                )
            )
        }
        shouldEnableLoading(name = state.value.addBottomSheet.name, uri = state.value.addBottomSheet.image)
    }

    fun updateCategoryImage(uri: Uri){
        _state.update {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    image = uri,

                )
            )
        }
        shouldEnableLoading(name = state.value.addBottomSheet.name, uri = state.value.addBottomSheet.image)
    }

    private fun shouldEnableLoading(name: String, uri: Uri){
        if (name != "" && uri != Uri.EMPTY){
            _state.update {
                it.copy(
                    addBottomSheet = it.addBottomSheet.copy(
                        isEnabled = true
                    )
                )
            }
        } else {
            _state.update {
                it.copy(
                    addBottomSheet = it.addBottomSheet.copy(
                        isEnabled = false
                    )
                )
            }
        }
    }

    fun dismissBottomSheet(){
        _state.update {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    name = "",
                    image = Uri.EMPTY
                ),
                showBottomSheet = false
            )
        }
    }

    fun addCategory(){
        if (!validateImageSize(state.value.addBottomSheet.image)){
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    addBottomSheet = it.addBottomSheet.copy(
                        isLoading = true
                    )
                )
            }
            service.addCategory(
                Category(
                    imageUrl = uriToBitmapString.uriToBase64(state.value.addBottomSheet.image),
                    name = state.value.addBottomSheet.name,
                    numberOfTasks = 0
                )
            )
            delay(5000)
            _state.update {
                it.copy(
                    addBottomSheet = it.addBottomSheet.copy(
                        isLoading = false
                    )
                )
            }
        }

    }
    // endregion
}
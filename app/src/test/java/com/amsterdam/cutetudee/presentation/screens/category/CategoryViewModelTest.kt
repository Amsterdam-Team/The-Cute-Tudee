package com.amsterdam.cutetudee.presentation.screens.category

import android.net.Uri
import com.amsterdam.cutetudee.domain.entity.Category
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.utils.TestDispatcherProvider
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


class CategoryViewModelTest {
    private lateinit var categoryService: CategoryService
    private lateinit var viewModel: CategoryViewModel
    private val testDispatcher = TestDispatcherProvider()
    private var testScope = TestScope(testDispatcher.testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        categoryService = mockk(relaxed = true)
        viewModel = CategoryViewModel(categoryService, testDispatcher)
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
    @Test()
    fun `init should update the categories state when the category service return list of category`() {
        // Arrange
        val id = Uuid.random()
        val category = Category(
            id = id,
            name = "category 1",
            image = "image",
            isUserCreated = true,
            numberOfTasks = 1
        )
        val expectedUiState = CategoryUiState(
            categoryId = id.toString(),
            categoryImage = Uri.parse("image"),
            categoryName = "category 1",
            badgeCount = "1",
            isAddedByUser = true
        )

        every { categoryService.getAllCategories() } returns flowOf(listOf(category))

        // Act
        testScope.advanceUntilIdle()
        // Assert
        assertThat(viewModel.state.value.categories)
            .isEqualTo(listOf(expectedUiState))
    }

    @Test
    fun `onFABClicked should update the hideBottomSheet state to false`() {
        // act
        viewModel.onFABClicked()
        // assert
        assertThat(
            viewModel.state.value
                .hideBottomSheet
        ).isFalse()
    }

    @Test
    fun `onAddCategoryClicked should hide the bottom sheet and reset the bottom sheet data when update success`() {
        //act
        viewModel.onAddCategoryClicked()
        //assert
        assertThat(viewModel.state.value.addBottomSheet.isLoading).isTrue()
        testScope.advanceUntilIdle()
        assertThat(viewModel.state.value.hideBottomSheet).isTrue()
        assertThat(viewModel.state.value.addBottomSheet).isEqualTo(BottomSheetState())
    }


    @Test
    fun `onUpdateCategoryImage should update the state of the image uri`() {
        //arrange
        val selectedUri = Uri.EMPTY
        //act
        viewModel.onUpdateCategoryImage(selectedUri)
        //assert
        assertThat(viewModel.state.value.addBottomSheet.image).isEqualTo(selectedUri)
    }

    @Test
    fun `onUpdateCategoryTextValue should update the state of the image uri`() {
        //arrange
        val categoryName = "category name"
        //act
        viewModel.onUpdateCategoryTextValue(categoryName)
        //assert
        assertThat(viewModel.state.value.addBottomSheet.name).isEqualTo(categoryName)
    }

    @Test
    fun `add button should be enabled when all required fields are filled`() {
        //arrange
        val categoryName = "category name"
        val selectedUri = Uri.parse("fake image uri")
        //act
        viewModel.onUpdateCategoryTextValue(categoryName)
        viewModel.onUpdateCategoryImage(selectedUri)
        //assert
        assertThat(viewModel.state.value.addBottomSheet.isEnabled).isTrue()
    }


    @Test
    fun `add button should not be enabled when required fields are missing`() {
        //arrange
        val categoryName = "category name"
        //act
        viewModel.onUpdateCategoryTextValue(categoryName)
        //assert
        assertThat(viewModel.state.value.addBottomSheet.isEnabled).isFalse()
    }

    @Test
    fun `onCancelAddCategoryClicked should hide the bottom sheet and reset the its data`() {
        // act
        viewModel.onCancelAddCategoryClicked()
        //assert
        assertThat(
            viewModel.state.value
                .hideBottomSheet
        ).isTrue()
        assertThat(viewModel.state.value.addBottomSheet).isEqualTo(BottomSheetState())
    }

    @Test
    fun `onDismissAddSheet should hide the bottom sheet`() {
        //act
        viewModel.onDismissAddSheet()
        //assert
        assertThat(viewModel.state.value.hideBottomSheet).isTrue()
    }

    // region: Effect Tests
    @Test
    fun `onAddCategoryClicked should update the effect with ShowAddSnackBar when add success`() {
        // arrange
        var effect: CategoryEffect? = null
        //act
        testScope.runTest {
            val collectJob = launch {
                viewModel.effect.collect {
                    effect = it
                }
            }
            viewModel.onAddCategoryClicked()
            testScope.advanceUntilIdle()
            collectJob.cancel()
        }
        //assert
        assertThat(effect).isEqualTo(CategoryEffect.ShowAddSnackBar)
    }

    @Test
    fun `onAddCategoryClicked should update the effect with ShowError when add failed`() {
        // arrange
        var effect: CategoryEffect? = null
        coEvery { categoryService.addCategory(any()) } throws Exception()
        //act
        testScope.runTest {
            val collectJob = launch {
                viewModel.effect.collect {
                    effect = it
                }
            }
            viewModel.onAddCategoryClicked()
            testScope.advanceUntilIdle()
            collectJob.cancel()
        }
        //assert
        assertThat(effect).isEqualTo(CategoryEffect.ShowError)
    }

}


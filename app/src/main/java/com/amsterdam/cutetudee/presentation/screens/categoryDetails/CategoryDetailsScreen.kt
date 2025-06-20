package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.TaskItemCard
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.screens.category.composables.AddEditCategoryBottomSheet
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.component.HorizontalTabs
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.component.Tab
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.component.TopAppBar
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.toBitmap
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryDetailsScreen(
    viewModel: CategoryDetailsViewModel = koinViewModel(),
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    val uiState by viewModel.state.collectAsState()
    val selectedState by viewModel.stateFilter.collectAsState()

    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage.isNotEmpty() -> Text(text = "error ${uiState.errorMessage}")
        else -> {
            CategoryDetailsContent(
                uiState = uiState,
                tasks = uiState.taskUiState,
                selectedState = selectedState,
                categoryUiState = uiState.categoryUiState,
                onStatusChange = viewModel::setStatus,
                onBack = { navController.popBackStack() },
                categoryTitle = uiState.categoryUiState.title,
                onOptionClick = viewModel::onToggleBottomSheet,
                categoryImage = uiState.categoryUiState.image,
                onEditCategory = viewModel::editCategory,
                onDeleteCategory = viewModel::deleteCategory,
                onDismissRequest = viewModel::dismissBottomSheet,
                onImageSelected = viewModel::updateCategoryImage,
                onTextValueChange = viewModel::updateCategoryName
            )
        }
    }
}

@Composable
private fun CategoryDetailsContent(
    uiState: CategoryDetailsUiState,
    tasks: List<TaskUiState>,
    categoryUiState: CategoryUiState,
    selectedState: Task.Status,
    categoryImage: String,
    modifier: Modifier = Modifier,
    onStatusChange: (Task.Status) -> Unit,
    onBack: () -> Unit,
    categoryTitle: String,
    onOptionClick: (Painter) -> Unit = {},
    onEditCategory: () -> Unit,
    onDeleteCategory: () -> Unit,
    onDismissRequest: () -> Unit,
    onImageSelected: (Uri) -> Unit,
    onTextValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppTheme.color.surfaceHigh)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        TopAppBar(
            onClickBack = onBack,
            title = categoryTitle,
            withOption = categoryUiState.isUserCreation,
            showIndicator = false,
            onclickOption = { onOptionClick(BitmapPainter(categoryUiState.image.toBitmap().asImageBitmap())) },
        )

        val inProgressCount = tasks.count { it.status == Task.Status.IN_PROGRESS.name }
        val toDoCount = tasks.count { it.status == Task.Status.TODO.name }
        val doneCount = tasks.count { it.status == Task.Status.DONE.name }

        HorizontalTabs(
            tabs = listOf(
                Tab(title = stringResource(R.string.in_progress), count = inProgressCount),
                Tab(title = stringResource(R.string.todo), count = toDoCount),
                Tab(title = stringResource(R.string.done), count = doneCount)
            ),
            selectedTabIndex = when (selectedState) {
                Task.Status.IN_PROGRESS -> 0
                Task.Status.TODO -> 1
                Task.Status.DONE -> 2
            },
            onTabSelected = {
                val selectedStatus = when (it) {
                    0 -> Task.Status.IN_PROGRESS
                    1 -> Task.Status.TODO
                    2 -> Task.Status.DONE
                    else -> Task.Status.IN_PROGRESS
                }
                onStatusChange(selectedStatus)
            }
        )
        val filteredTasks = tasks.filter { it.status == selectedState.name }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.color.surface)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredTasks) { task ->
                TaskItemCard(
                    categoryImage = BitmapPainter(categoryImage.toBitmap().asImageBitmap()),
                    priorityUi = enumValueOf<PriorityUi>(task.priority),
                    title = task.title,
                    description = task.description,
                    date = task.createdDate
                )
            }
        }

        AddEditCategoryBottomSheet(
            text = uiState.addBottomSheet.name,
            image = uiState.addBottomSheet.image,
            isLoading = uiState.addBottomSheet.isLoading,
            isEnabled = uiState.addBottomSheet.isEnabled,
            isEdit = true,
            painter = uiState.addBottomSheet.painter,
            hideBottomSheet = uiState.hideBottomSheet,
            onDeleteCategory = onDeleteCategory,
            onAddCategory = onEditCategory,
            onDismissRequest = onDismissRequest,
            onImageSelected = onImageSelected,
            onTextValueChange = onTextValueChange,
        )
    }
}

//@ThemeAndLocalePreviews
//@Composable
//fun CategoryDetailsPreview() {
//    CuteTudeeTheme {
//        val fakeTasks = listOf(
//            TaskUiState(
//                id = "kjsd",
//                title = "Study Kotlin",
//                description = "Read about coroutines and Flow",
//                createdDate = "2025-06-16",
//                status = Task.Status.IN_PROGRESS.name,
//                priority = PriorityUi.HIGH.name,
//                categoryId = "kjsd"
//            ),
//            TaskUiState(
//                id = "kj5sd",
//                title = "Write Report",
//                description = "Project update",
//                createdDate = "2025-06-14",
//                status = Task.Status.DONE.name,
//                priority = PriorityUi.MEDIUM.name,
//                categoryId = "kjsd"
//            ),
//            TaskUiState(
//                id = "k4jsd",
//                title = "Write Report",
//                description = "Project update",
//                createdDate = "2025-06-14",
//                status = Task.Status.DONE.name,
//                priority = PriorityUi.MEDIUM.name,
//                categoryId = "kjsd"
//            ),
//            TaskUiState(
//                id = "3kjsd",
//                title = "Study Kotlin",
//                description = "Read about coroutines and Flow",
//                createdDate = "2025-06-16",
//                status = Task.Status.IN_PROGRESS.name,
//                priority = PriorityUi.HIGH.name,
//                categoryId = "kjsd"
//            ),
//            TaskUiState(
//                id = "5kjsd",
//                title = "Write Report",
//                description = "Project update",
//                createdDate = "2025-06-14",
//                status = Task.Status.TODO.name,
//                priority = PriorityUi.MEDIUM.name,
//                categoryId = "kjsd"
//            )
//        )
//
//        val selectedStatus = remember { mutableStateOf(Task.Status.TODO) }
//
//        CategoryDetailsContent(
//            modifier = Modifier.statusBarsPadding(),
//            tasks = fakeTasks,
//            categoryUiState = CategoryUiState(
//                id = "ksdjk",
//                title = "category 1",
//                image = R.drawable.category_add_icon.toString(),
//                isUserCreation = true
//            ),
//            selectedState = selectedStatus.value,
//            onStatusChange = {
//                selectedStatus.value = it
//            },
//            onBack = {},
//            categoryTitle = "Coding",
//            categoryImage = "iVBORw0KGgoAAAANSUhEUgAAAFQAAABUCAYAAAAcaxDBAAAAAXNSR0IArs4c6QAAAARzQklUCAgI\n" +
//                    "CHwIZIgAAAcZSURBVHic7Zx9jBxlHce/39nrveyeUhIQG15EIEZTX9AeClKNNZoYouElhoAoQhvo\n" +
//                    "ni3GxNB7a8ya3u1eU4Mv4W72SuVCBaUJ2CDRKqW+JVqUi4iJEAWE4kvExJCanbnbu9vn6x/2TG9n\n" +
//                    "dm9v75ndvd58/tvf88zz+853n51nZnZ+A8TExMTExMTErHryw16PO+yfH2UORjl4K+AO++czoUEI\n" +
//                    "W0F0AoCg50mMpvu7D9rOd8YaOjGqs2T8PSDuqtRHwOHegdQNNvM6NgdrFfKj3qdkvD9VMxMACFzv\n" +
//                    "Zgv9NnOfcYbmc14awg9AnldLfxJftpn/jDLUzRXuAeAubyuekx+d/rAtDW3L6ezmpj9Kmc3i/7+I\n" +
//                    "EqkTMM4LCZm/3DHU/ZotYcvh4D6lvDn/YQKfrNRHwBGYtl3k/BMgNixqNOoB8MuFj/ft9S+YN7wM\n" +
//                    "ACSUEk7nH7b382QtWpZclA4MT79lzjF9oG4ieHbVzoIv6gTEV0FMOcTxOZN8aucg/12LmHo4tfg8\n" +
//                    "CaKngqiTYGJnur/rQQDIZwtPgPz44i66p5RYd8ApzW8mdDPILYFRhJ/QUV+6v/vZanqqGurmvK0E\n" +
//                    "vr3EPtXCsxAeV4KP9/Ylf2thPADAWEbdTrt/jMT7QzsIv6PjXL+9v+vVhVA+6z0Kov6Vnfp8tdOt\n" +
//                    "ioa6I/6NdHSo7sSVEF6Gw2/OznRNfjHD/9Q7zFhG3U6Hf5TAlRUSPZge6P5cedTNFg6RvLHevAAA\n" +
//                    "4rp0f+qxsKbQRWkyo/WguXdFSSuLeSukb7S3+6/ls969E6PTF9UzTKLD+2FFM6W7w8wEABKz9eRb\n" +
//                    "hIE7kVEyrCnU0JkO/y6S5644cTWIThA7JHMinyt8Zyw3c1mtm+ZzngswdGWmcW5JD3Z/rdK2Ekv1\n" +
//                    "yF2cBBvUPp0Oawo1lMCdK066LPjZBEov5HPexMSIt6Faz3y2cDuA0J2BtHX7UNd3o1AYgGZrWDhg\n" +
//                    "qJubvhjABVHrqcCdxsFLbrawK6xxPOdtAnl/WJuAr6YHuyejlXc63Oju05vKowFDSfOuxggKh0AX\n" +
//                    "yb35rPfM2N7iO05vc6RKs++x3oFUJnp1i+GcHzhVC/nJ8+LIldQCcXnCzD/njvg3AYCb9XMg31be\n" +
//                    "TcCLs8XkrY0XCAi4sDwWvFKSqh7DGg0dfc/N+u8lFTgMCJim2q5dyenXiiDOKQ+FLUpnNUDKsggz\n" +
//                    "8xQ704MdzzVUzOmIbywPhRkaen7VckjHegdSoQtUoyBNR3ksYKikRGPkrABhpmSc25stA2LAv7AZ\n" +
//                    "2vqGgkM7dif/2mwVClmDgoYSaoia+nnln7Nd32q2CAAAsa48FDRUnG+ImDqR8ItMpmU01vSTj6kR\n" +
//                    "xoZGT2yoZWJDLRMbapnYUMvEhlomNtQysaGWiQ21TGyoZWJDLRMbapnYUMvEhlomNtQysaGWiQ21\n" +
//                    "TGyoZdacoaS92iwBpjy25gwFZG2fqeCXswYNZeBpj3pRyF/ua8rQfNbbJuGDtsajgoYuq05ptTKx\n" +
//                    "Z+ZSJebvA7El6uLWM36GujlvWG2lF8Nqj6IgMEMZsnKtRsZz3iYHegDAxqhyCAg8wRI2Q1e9ofms\n" +
//                    "t8cBpgBGZiYAkAhUlARmqID51VxEn896h0Fc14hcgorlseBPnppZje8lOFWm+GMQVzcuK/3ySMgx\n" +
//                    "lK+3+vOM5dyf1bmz9I4BbGwFixR4tj9wDDXCPxqjxg7jOZ3dFDMBEMEq65AHbp2XGqLGAgf3KUX4\n" +
//                    "R5thJgAoxKvgDC12Wiu/jhpvzjtCYFOz8qfaOp8ujwUM3ZFhAcKPGiOpfvLZwtdJfqhZ+QU8euvd\n" +
//                    "9MrjoVdKIg9EL6l+8lnvGpBfaqYGGk2ExUMN7R1IHhb002gl1QeJ8wA91EwNAo6kh7qPhrVVvJZ3\n" +
//                    "mLpBQiseTz8Bcn3TsgvHTTFZ8Y0QS57Bu9nCLhK7Ab7BrrLVhaDXISfTO5isWtJT0yXRZEbri+3+\n" +
//                    "ThHvA3AVgTdbUdniSPg7geOgpmaLKbeWIt26rjH3jxQuLzmJjYC5hMAVgK4CGKjMXU0I+hfFX4uY\n" +
//                    "cozzshKlPy71SqEwrF20j+dmLqExPaDew/8Z/AGwRQtxBR/QbwQed8jfG/Dp3oGuV2wMHeldkP05\n" +
//                    "7wqJVxtoC6CPkMFy6MagkwB/DvJnmNev0rtTU1FlauhtpfG9/pVOSR8DcBuISyNNJv2Z4AMmwSdt\n" +
//                    "vnxrKZp2n25idHqzZLYBuM3y0PuNw8kv9CWfsjxuTTT9xufYsH+h45gRgLeQ9f3HJcGQOLjOwdC2\n" +
//                    "vlRT75Y13dAF9o8WNxoznwHx6WVtKDxSSrR9ZUdfx/MRSVsWLWPoAvlhrwcJ7AZwbdWOwvdhkIty\n" +
//                    "gamHljN0gYnR6Yuk0s0SPkPy3QAg4BlChxKO89Adfcm/NVvjqsUdKb5zIlt8e7N1xMTExMTExKxl\n" +
//                    "/gsXSk5gKxf5fgAAAABJRU5ErkJggg==\n",
//        )
//    }
//}
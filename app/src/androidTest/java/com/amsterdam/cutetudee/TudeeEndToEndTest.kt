package com.amsterdam.cutetudee

import android.content.Context
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.test.swipeUp
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.amsterdam.cutetudee.di.serviceModule
import com.amsterdam.cutetudee.di.testDataModule
import com.amsterdam.cutetudee.di.viewModelModule
import com.amsterdam.cutetudee.presentation.CuteTudeeApp
import com.amsterdam.cutetudee.presentation.MainActivity
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.navigation.NavigationBarItems
import com.amsterdam.cutetudee.presentation.utils.addEditBottomSheetTag
import com.amsterdam.cutetudee.presentation.utils.daysLazyRowTag
import com.amsterdam.cutetudee.presentation.utils.deleteTag
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin

@RunWith(AndroidJUnit4::class)
class TudeeEndToEndTest {
    @get:Rule()
    val composeRule = createAndroidComposeRule<MainActivity>()
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val onBoardingRobot by lazy { OnBoardingScreenRobot(composeRule) }
    val userInputTitle = "Task Title"
    val userInputDescription = "Task Description"

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
            modules(testDataModule, serviceModule, viewModelModule)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldAddTwoTasksAndDeleteOneOfTheme() {
        composeRule.activity.setContent { CuteTudeeApp() }

        composeRule.waitUntilExactlyOneExists(
            matcher = hasText(context.getString(R.string.onboarding_title_one)),
            timeoutMillis = 10_000
        )

        onBoardingRobot
            .assertFirstScreen()
            .clickNextButton()
            .assertSecondScreen()
            .clickNextButton()
            .assertThirdScreen()
            .clickNextButton()

        composeRule.waitUntilExactlyOneExists(
            matcher = hasText(context.getString(R.string.app_title)),
            timeoutMillis = 10_000
        )

        HomeRobot(composeRule, userInputTitle, userInputDescription)
            .clickAddButton()
            .fillAddForm()
            .clickSaveButton()
            .assertTaskIsExistOnTheScreen()
        composeRule.onNodeWithContentDescription(NavigationBarItems.Tasks.name).performClick()

        composeRule.waitUntilExactlyOneExists(
            matcher = hasText(context.getString(R.string.tasks)),
            timeoutMillis = 10_000
        )
        TasksRobot(composeRule, userInputTitle, userInputDescription)
            .assertTaskIsExistOnTheScreen()
            .clickOtherDay()
            .assertTaskIsNotExists()
            .clickAddButton()
            .fillAddForm()
            .clickSaveButton()
            .swipeToDeleteTaskItemCard()
            .clickOtherDay()
            .assertTaskIsNotExists()

        composeRule.onNodeWithContentDescription(NavigationBarItems.Categories.name).performClick()
        composeRule.waitUntilExactlyOneExists(
            matcher = hasText(context.getString(R.string.categories)),
            timeoutMillis = 10_000
        )
    }

    class OnBoardingScreenRobot(
        private val composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
    ) {

        private val context: Context
            get() = composeRule.activity.applicationContext

        private fun assertScreenContent(
            @StringRes titleRes: Int,
            @StringRes descriptionRes: Int,
            @StringRes imageContentDescRes: Int,
        ): OnBoardingScreenRobot {
            val title = context.getString(titleRes)
            val description = context.getString(descriptionRes)
            val imageDesc = context.getString(imageContentDescRes)

            composeRule.onNodeWithText(title)
                .assertExists("Expected title: $title")
                .assertIsDisplayed()

            composeRule.onNodeWithText(description)
                .assertExists("Expected description: $description")
                .assertIsDisplayed()

            composeRule.onAllNodesWithContentDescription(imageDesc)
                .onFirst()
                .assertIsDisplayed()

            return this
        }

        fun assertFirstScreen(): OnBoardingScreenRobot = assertScreenContent(
            R.string.onboarding_title_one,
            R.string.onboarding_description_one,
            R.string.content_description_onboarding_robot_base
        )

        fun assertSecondScreen(): OnBoardingScreenRobot = assertScreenContent(
            R.string.onboarding_title_two,
            R.string.onboarding_description_two,
            R.string.content_description_onboarding_robot_base
        )

        fun assertThirdScreen(): OnBoardingScreenRobot = assertScreenContent(
            R.string.onboarding_title_three,
            R.string.onboarding_description_three,
            R.string.content_description_onboarding_robot_base
        )

        fun clickNextButton(): OnBoardingScreenRobot {
            val nextButtonDesc = context.getString(R.string.next_button_onboarding_description)

            composeRule.onAllNodesWithContentDescription(nextButtonDesc)
                .onFirst()
                .performClick()

            return this
        }
    }

    @OptIn(ExperimentalTestApi::class)
    class HomeRobot(
        private val composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
        val userInputTitle: String,
        val userInputDescription: String
    ) {
        private val context: Context
            get() = composeRule.activity.applicationContext


        fun clickAddButton(): HomeRobot {
            val addButtonDesc = context.getString(R.string.add_task)
            composeRule.onNodeWithContentDescription(addButtonDesc)
                .performClick()
            return this
        }

        fun fillAddForm(): HomeRobot {
            composeRule.onNodeWithText(context.getString(R.string.task_title_hint))
                .performTextInput(userInputTitle)

            composeRule.onNodeWithText(context.getString(R.string.task_description_hint))
                .performTextInput(userInputDescription)

            composeRule.onNodeWithText(context.getString(PriorityUi.HIGH.labelRes)).performClick()

            composeRule.onNodeWithTag(addEditBottomSheetTag)
                .performTouchInput {
                    swipeUp()
                }

            composeRule.onNodeWithContentDescription(context.getString(R.string.cooking))
                .performClick()

            return this
        }

        fun clickSaveButton(): HomeRobot {
            composeRule.onNodeWithText(context.getString(R.string.add)).performClick()
            return this
        }

        fun assertTaskIsExistOnTheScreen(): HomeRobot {
            composeRule.waitUntilExactlyOneExists(
                matcher = hasText(userInputTitle),
                timeoutMillis = 10_000
            )
            composeRule.onNodeWithText(userInputTitle)
                .assertExists("Expected title: $userInputTitle")
                .assertIsDisplayed()
            composeRule.onNodeWithText(userInputDescription)
                .assertExists("Expected description: $userInputDescription")
                .assertIsDisplayed()
            return this
        }
    }

    @OptIn(ExperimentalTestApi::class)
    class TasksRobot(
        private val composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
        private val userInputTitle: String,
        private val userInputDescription: String
    ) {
        private val context: Context
            get() = composeRule.activity.applicationContext

        val tempTaskTitle = "temporary task title"
        val tempTaskDescription = "temporary task Description"
        fun assertTaskIsExistOnTheScreen(): TasksRobot {
            composeRule.onNodeWithText(userInputTitle)
                .assertExists("Expected title: $userInputTitle")
                .assertIsDisplayed()
            composeRule.onNodeWithText(userInputDescription)
                .assertExists("Expected description: $userInputDescription")
                .assertIsDisplayed()
            return this
        }

        fun clickAddButton(): TasksRobot {
            val addButtonDesc = context.getString(R.string.add_task)
            composeRule.onNodeWithContentDescription(addButtonDesc)
                .performClick()
            return this
        }

        fun fillAddForm(): TasksRobot {
            composeRule.onNodeWithText(context.getString(R.string.task_title_hint)).performTextClearance()
            composeRule.onNodeWithText(context.getString(R.string.task_title_hint)) .performTextInput(tempTaskTitle)

            composeRule.onNodeWithText(context.getString(R.string.task_description_hint))
                .performTextInput(tempTaskDescription)
            composeRule.onNodeWithText(context.getString(R.string.priority)).performTouchInput {
                swipeUp()
            }

            composeRule.onNodeWithText(context.getString(PriorityUi.MEDIUM.labelRes)).performClick()

            composeRule.onNodeWithTag(addEditBottomSheetTag)
                .performTouchInput {
                    swipeUp()
                }
            composeRule.onNodeWithContentDescription(context.getString(R.string.cooking))
                .performClick()
            return this
        }

        fun clickOtherDay(): TasksRobot {

            composeRule.onNodeWithContentDescription(context.getString(R.string.next_month))
                .performClick()
            composeRule.onNodeWithTag(daysLazyRowTag).performTouchInput {
                swipeRight()
                swipeRight()
                swipeRight()
            }
            val today =
                Clock.System
                    .now()
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date.dayOfMonth

            val otherDay = if (today != 5) 5 else 6
            composeRule.onNodeWithText("$otherDay")
                .performClick()
            return this
        }

        fun assertTaskIsNotExists(): TasksRobot {
            composeRule.waitUntilExactlyOneExists(
                matcher = hasContentDescription(context.getString(R.string.empty_tasks_title)),
                timeoutMillis = 60_000
            )
            composeRule.onNodeWithContentDescription(context.getString(R.string.empty_tasks_title)).assertIsDisplayed()
            return this
        }

        fun clickSaveButton(): TasksRobot {
            composeRule.onNodeWithText(context.getString(R.string.add)).performClick()
            return this
        }
        fun swipeToDeleteTaskItemCard() : TasksRobot{
            composeRule.waitUntilExactlyOneExists(
                matcher = hasText(tempTaskTitle),
                timeoutMillis = 60_000
            )
            composeRule
                .onNodeWithText(tempTaskTitle)
                .performTouchInput {
                    swipeLeft()
                    swipeLeft()
                }
            composeRule
                .onNodeWithContentDescription(context.getString(R.string.delete_icon))
                .performClick()
            composeRule.waitUntilExactlyOneExists(
                matcher = hasTestTag(deleteTag),
                timeoutMillis = 10_000
            )
            composeRule
                .onNodeWithTag(deleteTag)
                .performClick()
            return this
        }
    }
}
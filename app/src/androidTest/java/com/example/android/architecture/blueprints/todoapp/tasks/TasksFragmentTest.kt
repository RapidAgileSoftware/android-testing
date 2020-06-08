package com.example.android.architecture.blueprints.todoapp.tasks

import FakeAndroidTestRepository
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.ITasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@ExperimentalCoroutinesApi
class TasksFragmentTest(){

    private lateinit var repository: ITasksRepository

    @Before
    fun initRepository(){
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository= repository
    }

    @After
    fun clearDB()= runBlockingTest{
        ServiceLocator.resetRepository()
    }

    @Test
    fun clickTask_navigateToDetailFragmentOne() = runBlockingTest{
        // Given - I'm on the tasks screen with two tasks
        repository.saveTask(Task("Task 1", "description 1", isCompleted = false, id = "id1"))
        repository.saveTask(Task("Task 2", "description 2", isCompleted = true, id = "id2"))

        val scenario = launchFragmentInContainer<TasksFragment> (Bundle(), R.style.AppTheme)
        // mocking Nav Controller with Mockito
        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        // When -  click on the first list item
        onView(withId(R.id.tasks_list))
                .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                        hasDescendant(withText("Task 1")), click()))

        // Then - Verify that we navigate to the first detail screen
        verify(navController).navigate(
            TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment("id1")
        )
    }

    @Test
    fun clickAddTaskButton_navigateToAddEditFragment() = runBlockingTest {
        // Given - I'm on the tasks screen

        val scenario = launchFragmentInContainer<TasksFragment> (Bundle(), R.style.AppTheme)
        // mocking Nav Controller with Mockito
        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // When -  click on + button
        onView(withId(R.id.add_task_fab)).perform(click())

        // THEN - Verify that we navigate to the add screen
        verify(navController).navigate(
                TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                        null,
                    getApplicationContext<Application>().getString(R.string.add_task))
        )

    }
}
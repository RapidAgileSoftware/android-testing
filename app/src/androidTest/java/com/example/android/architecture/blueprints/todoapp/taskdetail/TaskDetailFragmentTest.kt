package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
//MediumTest needs a medium amount of time to run
@MediumTest
class TaskDetailFragmentTest(){

    @Test
    fun activeTaskDetails_DisplayedInUI(){
        // Given is an active (= not completed) task to the DB
        val activeTask = Task("Active Task", "let's do something awesome", false)

        // When Details fragment launched to display task
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)
        Thread.sleep(2000)
    }

}
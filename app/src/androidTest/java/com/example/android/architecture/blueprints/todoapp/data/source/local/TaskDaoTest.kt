package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest (){

    //execute each task synchronously using Architecture Components
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    private lateinit var database:ToDoDatabase

    @Before
    fun setupDB(){
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                ToDoDatabase::class.java).build()
    }

    @After
    fun closeDB() = database.close()

    @Test
    fun insertTaskAndGetItById()= runBlockingTest{
        // Given - insert a Task
        val task = Task("Task 1", "Description 1")
        database.taskDao().insertTask(task)

        // When - getting the task by Id from DB
        val loadedTask = database.taskDao().getTaskById(task.id)

        // Then - it has the correct values
        assertThat<Task>(loadedTask as Task, notNullValue())
        assertThat(loadedTask?.id, `is`(task.id))
        assertThat(loadedTask?.title, `is`("Task 1"))
        assertThat(loadedTask?.description, `is`("Description 1"))
        assertThat(loadedTask?.isCompleted, `is`(task.isCompleted))

    }

    @Test
    fun updateTaskAndGetItById()= runBlockingTest{
        // Given - insert task
        val task1 = Task("Task 1", "Description 1")
        database.taskDao().insertTask(task1)
        // update task by creating a new task with same id but different attributes
        val updatedTask = Task("New Title", "new description", isCompleted = true, id = task1.id)
        database.taskDao().insertTask(updatedTask)

        // When loading updated Task with initial, we get new values
        val loadedTask = database.taskDao().getTaskById(task1.id)

        assertThat<Task>(loadedTask as Task, notNullValue())
        assertThat(loadedTask?.id, `is`(task1.id))
        assertThat(loadedTask?.title, `is`("New Title"))
        assertThat(loadedTask?.description, `is`("new description"))
        assertThat(loadedTask?.isCompleted, `is`(true))

    }

}
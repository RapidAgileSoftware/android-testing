package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test

import org.junit.Assert.*

class StatisticsUtilsTest {

    // one task, none completed
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsZeroHundred() {
        // GIVEN a list of tasks with one active task
        val tasks = listOf<Task>(
                Task("Task Title 1", "task description", isCompleted = false)
        )
        // WHEN you call getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks)

        // THEN there are 0% completed tasks and 100% active tasks
        //assertEquals(0f, result.completedTasksPercent)
        assertThat(result.completedTasksPercent, `is` (0f))
        //assertEquals(100f, result.activeTasksPercent)
        assertThat(result.activeTasksPercent, `is` (100f))
    }

    // 2 completed tasks, 3 active tasks
    @Test
    fun getActiveAndCompletedStats_both_returns60_40(){
        // GIVEN a list of 2 completed and 3 active tasks
        val tasks = listOf<Task>(
                Task("Task 1", "description 1", isCompleted = true),
                Task("Task 2", "description 2", isCompleted = false),
                Task("Task 3", "description 3", isCompleted = false),
                Task("Task 4", "description 4", isCompleted = true),
                Task("Task 5", "description 5", isCompleted = false)
        )
        // WHEN you call getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks)

        // THEN there are 40% completed tasks and 60% active tasks
        assertThat(result.completedTasksPercent, `is` (40f))
        assertThat(result.activeTasksPercent, `is` (60f))

    }

    // one task, all completed
    @Test
    fun getActiveAndCompletedStats_allCompleted_returnsHundredZero() {
        // GIVEN a list of one completed task
        val tasks = listOf<Task>(
                Task("Task Title 1", "task description", isCompleted = true)
        )
        // WHEN you call getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks)
        // THEN there are 100% completed tasks and 0% active tasks
        assertThat(result.completedTasksPercent, `is` (100f))
        assertThat(result.activeTasksPercent, `is` (0f))
    }

    // 2 completed tasks, 2 active tasks
    @Test
    fun getActiveAndCompletedStats_both_returns50_50(){
        // GIVEN a list of 2 active and 2 completed tasks
        val tasks = listOf<Task>(
                Task("Task 1", "description 1", isCompleted = true),
                Task("Task 2", "description 2", isCompleted = false),
                Task("Task 3", "description 3", isCompleted = false),
                Task("Task 4", "description 4", isCompleted = true)
        )
        // WHEN you call getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks)

        // THEN there are 50% completed tasks and 50% active tasks
        assertThat(result.completedTasksPercent, `is` (50f))
        assertThat(result.activeTasksPercent, `is` (50f))
    }

    // empty list, return 0,0
    @Test
    fun getActiveAndCompletedStats_emptyList_returnsZeroZero() {
        // GIVEN a empty task list
        val tasks = emptyList<Task>()
        // WHEN you call getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks)

        // THEN there are 0% completed tasks and 0% active tasks
        assertThat(result.completedTasksPercent, `is` (0f))
        assertThat(result.activeTasksPercent, `is` (0f))
    }

    // error :null list, return 0,0
    @Test
    fun getActiveAndCompletedStats_null_returnsZeroZero() {
        // GIVEN
        val tasks = null
        // WHEN
        val result = getActiveAndCompletedStats(tasks)
        // THEN there are 0% completed tasks and 0% active tasks
        assertThat(result.completedTasksPercent, `is` (0f))
        assertThat(result.activeTasksPercent, `is` (0f))
    }
}
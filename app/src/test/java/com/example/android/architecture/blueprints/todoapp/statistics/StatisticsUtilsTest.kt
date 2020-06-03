package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Test

import org.junit.Assert.*

class StatisticsUtilsTest {

    // one task, none completed
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsZeroHundred() {
        val tasks = listOf<Task>(
                Task("Task Title 1", "task description", isCompleted = false)
        )

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(StatsResult(100f,0f),result)
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(100f, result.activeTasksPercent)
    }

    // 2 completed tasks, 3 active tasks
    @Test
    fun getActiveAndCompletedStats_both_returns60_40(){
        val tasks = listOf<Task>(
                Task("Task 1", "description 1", isCompleted = true),
                Task("Task 2", "description 2", isCompleted = false),
                Task("Task 3", "description 3", isCompleted = false),
                Task("Task 4", "description 4", isCompleted = true),
                Task("Task 5", "description 5", isCompleted = false)
        )
        val result = getActiveAndCompletedStats(tasks)
        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)
    }

    // one task, all completed
    @Test
    fun getActiveAndCompletedStats_allCompleted_returnsHundredZero() {
        val tasks = listOf<Task>(
                Task("Task Title 1", "task description", isCompleted = true)
        )

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(100f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

    // 2 completed tasks, 2 active tasks
    @Test
    fun getActiveAndCompletedStats_both_returns50_50(){
        val tasks = listOf<Task>(
                Task("Task 1", "description 1", isCompleted = true),
                Task("Task 2", "description 2", isCompleted = false),
                Task("Task 3", "description 3", isCompleted = false),
                Task("Task 4", "description 4", isCompleted = true)
        )
        val result = getActiveAndCompletedStats(tasks)
        assertEquals(50f, result.completedTasksPercent)
        assertEquals(50f, result.activeTasksPercent)
    }

    // empty list, return 0,0
    @Test
    fun getActiveAndCompletedStats_emptyList_returnsZeroZero() {
        val tasks = emptyList<Task>()

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

    // error :null list, return 0,0
    @Test
    fun getActiveAndCompletedStats_null_returnsZeroZero() {
        val tasks = null

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }
}
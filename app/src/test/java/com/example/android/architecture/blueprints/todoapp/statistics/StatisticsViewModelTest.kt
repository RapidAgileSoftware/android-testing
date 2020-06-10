package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.core.Is.`is`


import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StatisticsViewModelTest(){
    // we are testing Architecture Components
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    // we are testing Coroutines and ViewModel
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // subject under test
    private lateinit var statisticsViewModel:StatisticsViewModel

    // dependencies
    private lateinit var repository:FakeTestRepository

    // init subject under test and dependency
    @Before
    fun initViewModelAndRepo(){

        // empty taskRepository
        repository = FakeTestRepository()
        // init ViewModel with dependency injection of fake task repository
        statisticsViewModel = StatisticsViewModel(repository)

    }

    @Test
    fun loadTasks_loading(){
        // When viewModel on refresh
        // we want to pause viewModelScope.launch so we can check  _dataLoading.value == true
        mainCoroutineRule.pauseDispatcher()
        statisticsViewModel.refresh()
        // Then - dataLoading Icon is showing
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(true))
        //  now we want the rest to run imediatly, so we resume the dispatcher
        mainCoroutineRule.resumeDispatcher()
        // afterwards it is false
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(false))
    }


}
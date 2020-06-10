package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

class StatisticsViewModelTest(){
    // we are testing Architecture Components
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    // we are testing Coroutines and ViewModel
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


}
/*
 *  Copyright (c) 2022  Thiago Lopes da Silva
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.lopes.githubsearch.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lopes.githubsearch.data.database.AppDatabase
import com.lopes.githubsearch.util.rule.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchLocalRepoImplDataUnitTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testDispatcher = TestCoroutineRule()

    @RelaxedMockK
    lateinit var appDatabase: AppDatabase

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        clearMocks(appDatabase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun should_call_insert_all_method_once() = testDispatcher.runBlockingTest {
        val searchLocalRepo = SearchLocalRepoImplData(appDatabase)

        searchLocalRepo.insertAll(emptyList())

        coVerify(exactly = 1) { appDatabase.searchGithubDao().insertAll(any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun should_call_get_all_method_once() = testDispatcher.runBlockingTest {
        val searchLocalRepo = SearchLocalRepoImplData(appDatabase)

        searchLocalRepo.getAllByPagingSource()

        coVerify(exactly = 1) { appDatabase.searchGithubDao().getAllByPagingSource() }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun should_call_clean_all_method_once() = testDispatcher.runBlockingTest {
        val searchLocalRepo = SearchLocalRepoImplData(appDatabase)

        searchLocalRepo.cleanAll()

        coVerify(exactly = 1) { appDatabase.searchGithubDao().cleanAll() }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun should_call_return_valid_database_instance() = testDispatcher.runBlockingTest {
        val searchLocalRepo = SearchLocalRepoImplData(appDatabase)

        val result = searchLocalRepo.getDatabase()

        Assert.assertEquals(result, appDatabase)
    }
}

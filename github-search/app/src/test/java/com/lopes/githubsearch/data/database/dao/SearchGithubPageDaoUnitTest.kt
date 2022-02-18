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

package com.lopes.githubsearch.data.database.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.lopes.githubsearch.data.database.AppDatabase
import com.lopes.githubsearch.support.model.ObjectInject
import com.lopes.githubsearch.util.rule.TestCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class SearchGithubPageDaoUnitTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testDispatcher = TestCoroutineRule()

    private lateinit var searchGithubDao: SearchGithubPageDao
    private lateinit var inMemoryAppDb: AppDatabase
    private val searchGithubInfoPage by lazy { ObjectInject.searchGithubInfoPage }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        inMemoryAppDb = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        searchGithubDao = inMemoryAppDb.searchGithubPageDao()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun should_overwrite_search_github_page() = testDispatcher.runBlockingTest {
        testDispatcher.testCoroutineScope.launch(Dispatchers.IO) {
            searchGithubDao.insert(searchGithubInfoPage)

            val updateSearchGithubInfoPage = searchGithubInfoPage.copy(nextPage = 3)
            searchGithubDao.insert(updateSearchGithubInfoPage)

            val result = searchGithubDao.searchRemoteKeyById(searchGithubInfoPage.label)
            Assert.assertEquals(result?.nextPage, 3)

            inMemoryAppDb.close()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun should_clean_all_search_github_page() = testDispatcher.runBlockingTest {
        testDispatcher.testCoroutineScope.launch(Dispatchers.IO) {
            searchGithubDao.insert(searchGithubInfoPage)

            searchGithubDao.cleanAll()

            val result = searchGithubDao.getAll()
            Assert.assertEquals(result.size, 0)

            inMemoryAppDb.close()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun should_delete_only_specific_page_key() = testDispatcher.runBlockingTest {
        testDispatcher.testCoroutineScope.launch(Dispatchers.IO) {
            searchGithubDao.insert(searchGithubInfoPage)

            val nextLabelQuery = searchGithubInfoPage.copy(label = "newQuery")
            searchGithubDao.insert(nextLabelQuery)

            searchGithubDao.deleteByKey(searchGithubInfoPage.label)

            val result = searchGithubDao.searchRemoteKeyById(nextLabelQuery.label)
            Assert.assertEquals(result, nextLabelQuery)

            inMemoryAppDb.close()
        }
    }

}
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

package com.lopes.githubsearch.domain.interactor

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.lopes.githubsearch.data.database.entities.GithubEntity
import com.lopes.githubsearch.domain.repository.SearchGithubRepository
import com.lopes.githubsearch.model.ObjectInject
import com.lopes.githubsearch.util.paging.SearchGithubDomainDiff
import com.lopes.githubsearch.util.paging.collectData
import com.lopes.githubsearch.util.rule.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListGithubInfoUseCaseUnitTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testDispatcher = TestCoroutineRule()

    @RelaxedMockK
    lateinit var mockSearchGithubRepository: SearchGithubRepository

    private val fakeQueryLanguage = "kotlin"
    private val githubSearchItem by lazy {
        ObjectInject.githubEntity
    }

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        clearMocks(mockSearchGithubRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun should_return_true_if_repository_is_called_once() = testDispatcher.runBlockingTest {
        // given
        val listGithubUseCase = ListGithubInfoUseCase(mockSearchGithubRepository)

        // when
        listGithubUseCase.execute(fakeQueryLanguage)

        // then
        coVerify(exactly = 1) { mockSearchGithubRepository.searchRepoByLanguage(any()) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun should_return_one_valid_github_domain_item() = testDispatcher.runBlockingTest {
        // given
        coEvery {
            mockSearchGithubRepository.searchRepoByLanguage(any())
        } returns flow {
            emit(PagingData.from(listOf(githubSearchItem)))
        }

        // when
        val listGithubUseCase = ListGithubInfoUseCase(mockSearchGithubRepository)
        val resultFlow = listGithubUseCase.execute(fakeQueryLanguage)

        // then
        val flowData = resultFlow.first().collectData(SearchGithubDomainDiff())

        val expectedGithubDomain = ObjectInject.searchGithubDomain
        Assert.assertEquals(flowData.first(), expectedGithubDomain)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun should_return_empty_github_domain_item_when_no_data_exists_in_stream() =
        testDispatcher.runBlockingTest {
            // given
            coEvery {
                mockSearchGithubRepository.searchRepoByLanguage(any())
            } returns flow {
                emit(PagingData.from(emptyList<GithubEntity>()))
            }

            // when
            val listGithubUseCase = ListGithubInfoUseCase(mockSearchGithubRepository)
            val resultFlow = listGithubUseCase.execute(fakeQueryLanguage)

            // then
            val flowData = resultFlow.first().collectData(SearchGithubDomainDiff())
            Assert.assertEquals(flowData.size, 0)
        }
}

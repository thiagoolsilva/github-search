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

package com.lopes.githubsearch.data.api

import com.lopes.githubsearch.model.ObjectInject
import com.lopes.githubsearch.util.retrofit.toJson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SearchGithubApiUnitTest {

    private lateinit var server: MockWebServer
    private val searchAPI: SearchGithubAPIContract by lazy {
        Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SearchGithubAPIContract::class.java)
    }

    @Before
    fun beforeEachTest() {
        server = MockWebServer()
        server.start()
    }

    @After
    fun afterEachTest() {
        server.shutdown()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun should_return_valid_search_github_api_response() = runBlocking {
        val searchGithubApiResponse = ObjectInject.searchGithubApiResponse

        server.enqueue(MockResponse().setBody(searchGithubApiResponse.toJson()))

        val response = searchAPI.searchRepositoryByType("kotlin", 1, 1)
        Assert.assertEquals(response, searchGithubApiResponse)

        val request = server.takeRequest()
        val expectedPath = "/search/repositories?sort=stars&q=kotlin&page=1&per_page=1"
        Assert.assertEquals(request.path, expectedPath)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun should_return_next_page_after_request_one() = runBlocking {
        val searchGithubApiResponse = ObjectInject.searchGithubApiResponse

        // first enqueue request
        server.enqueue(MockResponse().setBody(searchGithubApiResponse.toJson()))

        // next page enqueue request
        val nextItem = ObjectInject.searchGithubInfoItemResponse.copy(id = 2)
        val nextEnqueueRequest = searchGithubApiResponse.copy(items = listOf(nextItem))
        server.enqueue(MockResponse().setBody(nextEnqueueRequest.toJson()))

        val response = searchAPI.searchRepositoryByType("kotlin", 1, 1)
        Assert.assertEquals(response, searchGithubApiResponse)

        val expectedFirstPathRequest = "/search/repositories?sort=stars&q=kotlin&page=1&per_page=1"
        Assert.assertEquals(server.takeRequest().path, expectedFirstPathRequest)

        val nextPageResponse = searchAPI.searchRepositoryByType("kotlin", 2, 1)
        Assert.assertEquals(nextPageResponse, nextEnqueueRequest)

        val expectedSecondPathRequest = "/search/repositories?sort=stars&q=kotlin&page=2&per_page=1"
        Assert.assertEquals(server.takeRequest().path, expectedSecondPathRequest)
    }
}

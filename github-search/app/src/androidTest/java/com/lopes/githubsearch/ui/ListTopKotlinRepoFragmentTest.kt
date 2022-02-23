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

package com.lopes.githubsearch.ui

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.lopes.githubsearch.R
import com.lopes.githubsearch.data.api.SearchGithubAPIContract
import com.lopes.githubsearch.launchFragmentInHiltContainerGoogle
import com.lopes.githubsearch.model.ObjectInject
import com.lopes.githubsearch.ui.adapter.SearchGithubPageAdapter
import com.lopes.githubsearch.ui.imageloader.ImageLoader
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.every
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ListTopKotlinRepoFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    // should return a mockk instance by Hilt
    lateinit var searchGithubAPIContract: SearchGithubAPIContract

    @Inject
    lateinit var testNavHostController: TestNavHostController

    @Inject
    // should return a mockk instance by Hilt
    lateinit var imageLoader: ImageLoader

    @Before
    fun setUp() {
        hiltRule.inject()
        clearMocks(searchGithubAPIContract, imageLoader)

        every { imageLoader.load(any(), any(), any()) } returns Unit
    }

    @Test
    fun should_recycle_view_displayed() {
        coEvery {
            searchGithubAPIContract.searchRepositoryByType(any(), any(), any())
        } returns ObjectInject.searchGithubApiResponse

        launchFragmentInHiltContainerGoogle<ListTopKotlinRepoFragment> {
            Navigation.setViewNavController(requireView(), testNavHostController)
        }

        onView(withId(R.id.recycle_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun should_expected_repository_name_be_found() {
        coEvery {
            searchGithubAPIContract.searchRepositoryByType(any(), any(), any())
        } returns ObjectInject.searchGithubApiResponse

        launchFragmentInHiltContainerGoogle<ListTopKotlinRepoFragment> {
            Navigation.setViewNavController(requireView(), testNavHostController)
        }

        onView(withId(R.id.recycle_view))
            .perform(
                RecyclerViewActions.scrollToPosition<SearchGithubPageAdapter.SearchViewHolder>(
                    1
                )
            )
            .check(matches(isDisplayed()))

        val expectedRepositoryName = ObjectInject.searchGithubApiResponse.items.first().name
        onView(withId(R.id.content_item))
            .check(matches(withText(expectedRepositoryName)))
    }

    @Test
    fun should_show_expected_stars_count() {
        coEvery {
            searchGithubAPIContract.searchRepositoryByType(any(), any(), any())
        } returns ObjectInject.searchGithubApiResponse

        launchFragmentInHiltContainerGoogle<ListTopKotlinRepoFragment> {
            Navigation.setViewNavController(requireView(), testNavHostController)
        }

        val expectedRepositoryStarCount =
            ObjectInject.searchGithubApiResponse.items.first().stars.toString()
        onView(withId(R.id.txt_star_count))
            .check(matches(withText(expectedRepositoryStarCount)))
    }

    @Test
    fun should_show_expected_forks_count() {
        coEvery {
            searchGithubAPIContract.searchRepositoryByType(any(), any(), any())
        } returns ObjectInject.searchGithubApiResponse

        launchFragmentInHiltContainerGoogle<ListTopKotlinRepoFragment> {
            Navigation.setViewNavController(requireView(), testNavHostController)
        }

        val expectedRepositoryForksCount =
            ObjectInject.searchGithubApiResponse.items.first().forks.toString()
        onView(withId(R.id.txt_star_count))
            .check(matches(withText(expectedRepositoryForksCount)))
    }
}

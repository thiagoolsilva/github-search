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

package com.lopes.githubsearch.support.model

import com.lopes.githubsearch.data.api.dto.SearchGithubApiResponse
import com.lopes.githubsearch.data.api.dto.SearchGithubInfoItemResponse
import com.lopes.githubsearch.data.api.dto.SearchGithubOwner
import com.lopes.githubsearch.data.database.entities.GithubEntity
import com.lopes.githubsearch.data.database.entities.SearchGithubInfoPage
import com.lopes.githubsearch.domain.entity.SearchGithubDomain
import com.lopes.githubsearch.ui.model.GithubInfoView

class ObjectInject {

    companion object Model {
        const val apiQueryParam = "kotlin"
        const val apiPageParam = 1
        const val apiItemsPerPageParam = 1
        val githubEntity = GithubEntity(
            1,
            "fake name",
            "fake full name",
            "fake description",
            "fake login",
            1,
            1,
            "fake language",
            "fake login",
            "avatar url"
        )
        val searchGithubInfoItemResponse = SearchGithubInfoItemResponse(
            1,
            "fake name",
            "fake full name",
            "fake description",
            "fake login",
            1,
            1,
            "fake language",
            SearchGithubOwner("fake login", "avatar url")
        )
        val searchGithubDomain = SearchGithubDomain(
            1,
            "fake name",
            1,
            1,
            "fake login",
            "avatar url"
        )
        val githubInfoView = GithubInfoView(
            id = 1,
            name = "fake name",
            stars = 1,
            forks = 1,
            developerName = "fake login",
            developerPhotoUrl = "avatar url"
        )
        val searchGithubInfoPage = SearchGithubInfoPage(
            label = "kotlin",
            nextPage = 2
        )
        val searchGithubApiResponse = SearchGithubApiResponse(
            total = 1,
            items = listOf(searchGithubInfoItemResponse)
        )
    }
}

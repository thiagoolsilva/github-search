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

import com.lopes.githubsearch.data.api.SearchGithubAPIContract
import com.lopes.githubsearch.data.api.dto.SearchGithubApiResponse
import com.lopes.githubsearch.domain.repository.SearchRemoteGithubDataSource
import javax.inject.Inject

class SearchRemoteRepoImplData @Inject constructor(
    private val retrofitSource: SearchGithubAPIContract
) : SearchRemoteGithubDataSource {
    override suspend fun searchRepositoryByType(
        query: String,
        page: Int,
        itemsPerPage: Int
    ): SearchGithubApiResponse {
        return retrofitSource.searchRepositoryByType(query, page, itemsPerPage)
    }
}

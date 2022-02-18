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

package com.lopes.githubsearch.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lopes.githubsearch.crosscutting.PagingConstants
import com.lopes.githubsearch.data.database.AppDatabase
import com.lopes.githubsearch.data.database.entities.GithubEntity
import com.lopes.githubsearch.data.mediator.SearchGithubMediator
import com.lopes.githubsearch.domain.repository.SearchGithubRepository
import com.lopes.githubsearch.domain.repository.SearchLocalGithubInfoDataSource
import com.lopes.githubsearch.domain.repository.SearchPagingLocalKeyDataSource
import com.lopes.githubsearch.domain.repository.SearchRemoteGithubDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchRepoInfoImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteGithubDataSource,
    private val localSearchGithubGithubInfoData: SearchLocalGithubInfoDataSource<AppDatabase>,
    private val localSearchLocalKeyData: SearchPagingLocalKeyDataSource<AppDatabase>
) : SearchGithubRepository {

    override fun searchRepoByLanguage(repoLanguage: String): Flow<PagingData<GithubEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = PagingConstants.Constants.PAGING_SIZE
            ),
            remoteMediator = SearchGithubMediator(
                query = repoLanguage,
                remoteDataSource = remoteDataSource,
                localSearchPagingKeyData = localSearchLocalKeyData,
                localSearchGithubGithubInfoData = localSearchGithubGithubInfoData
            ),
            pagingSourceFactory = {
                localSearchGithubGithubInfoData.getAllByPagingSource()
            }
        ).flow
    }
}

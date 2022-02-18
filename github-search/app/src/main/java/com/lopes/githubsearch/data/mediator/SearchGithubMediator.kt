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

package com.lopes.githubsearch.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lopes.githubsearch.crosscutting.NetworkConstants.Constants.FIRST_GITHUB_SEARCH_PAGE_INDEX
import com.lopes.githubsearch.data.database.AppDatabase
import com.lopes.githubsearch.data.database.entities.GithubEntity
import com.lopes.githubsearch.data.database.entities.SearchGithubInfoPage
import com.lopes.githubsearch.data.mapper.toGithubEntity
import com.lopes.githubsearch.domain.repository.SearchLocalGithubInfoDataSource
import com.lopes.githubsearch.domain.repository.SearchPagingLocalKeyDataSource
import com.lopes.githubsearch.domain.repository.SearchRemoteGithubDataSource
import java.io.IOException
import retrofit2.HttpException
import timber.log.Timber

@ExperimentalPagingApi
class SearchGithubMediator(
    private val query: String,
    private val remoteDataSource: SearchRemoteGithubDataSource,
    private val localSearchPagingKeyData: SearchPagingLocalKeyDataSource<AppDatabase>,
    private val localSearchGithubGithubInfoData: SearchLocalGithubInfoDataSource<AppDatabase>
) : RemoteMediator<Int, GithubEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GithubEntity>
    ): MediatorResult {
        val page = when (loadType) {
            // The network load method takes an optional String
            // parameter. For every page after the first, pass the String
            // token returned from the previous page to let it continue
            // from where it left off. For REFRESH, pass null to load the
            // first page.
            LoadType.REFRESH -> null
            // In this example, you never need to prepend, since REFRESH
            // will always load the first page in the list. Immediately
            // return, reporting end of pagination.
            LoadType.PREPEND -> return MediatorResult.Success(
                endOfPaginationReached = true
            )
            LoadType.APPEND -> {
                val remoteKey = localSearchPagingKeyData.searchRemoteKeyById(query)

                // You must explicitly check if the page key is null when
                // appending, since null is only valid for initial load.
                // If you receive null for APPEND, that means you have
                // reached the end of pagination and there are no more
                // items to load.
                if (remoteKey?.nextPage == null) {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
                remoteKey.nextPage
            }
        }
        return mediatorResult(page, state, loadType)
    }

    private suspend fun mediatorResult(
        pages: Int?,
        state: PagingState<Int, GithubEntity>,
        loadType: LoadType
    ): MediatorResult {
        try {
            val page = pages ?: FIRST_GITHUB_SEARCH_PAGE_INDEX

            Timber.d("current page: $page")

            val finalAPIQuery = "language:$query"
            val apiResponse = remoteDataSource.searchRepositoryByType(
                finalAPIQuery,
                page,
                state.config.pageSize
            )
            val repos = apiResponse.items
            val endOfPaginationReached = repos.isEmpty()
            if (endOfPaginationReached.not()) {
                localSearchPagingKeyData.getDatabase().withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        localSearchGithubGithubInfoData.cleanAll()
                        localSearchPagingKeyData.deleteByKey(query)
                    }

                    val nextPage = page + 1
                    localSearchPagingKeyData.insert(
                        SearchGithubInfoPage(label = query, nextPage = nextPage)
                    )
                    val githubEntity = repos.map { it.toGithubEntity() }
                    localSearchGithubGithubInfoData.insertAll(githubEntity)
                }
                MediatorResult.Success(
                    endOfPaginationReached = endOfPaginationReached
                )
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            Timber.e(exception)
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            Timber.e(exception)
            return MediatorResult.Error(exception)
        }
    }
}

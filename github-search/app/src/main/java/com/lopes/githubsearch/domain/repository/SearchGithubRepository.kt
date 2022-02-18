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

package com.lopes.githubsearch.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.lopes.githubsearch.data.api.dto.SearchGithubApiResponse
import com.lopes.githubsearch.data.database.entities.GithubEntity
import com.lopes.githubsearch.data.database.entities.SearchGithubInfoPage
import kotlinx.coroutines.flow.Flow

interface SearchGithubRepository {
    fun searchRepoByLanguage(repoLanguage: String): Flow<PagingData<GithubEntity>>
}

interface SearchLocalGithubInfoDataSource<T> {
    suspend fun insertAll(GithubEntities: List<GithubEntity>)
    fun getAllByPagingSource(): PagingSource<Int, GithubEntity>
    suspend fun cleanAll()
    fun getDatabase(): T
}

interface SearchPagingLocalKeyDataSource<T> {
    suspend fun insert(remoteKeyEntities: SearchGithubInfoPage)
    suspend fun searchRemoteKeyById(key: String): SearchGithubInfoPage?
    suspend fun deleteByKey(key: String)
    suspend fun clearAll()
    fun getDatabase(): T
}

interface SearchRemoteGithubDataSource {
    suspend fun searchRepositoryByType(
        query: String,
        page: Int,
        itemsPerPage: Int
    ): SearchGithubApiResponse
}

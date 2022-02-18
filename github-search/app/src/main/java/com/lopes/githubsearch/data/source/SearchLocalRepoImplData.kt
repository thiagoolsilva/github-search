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

import androidx.paging.PagingSource
import com.lopes.githubsearch.data.database.AppDatabase
import com.lopes.githubsearch.data.database.entities.GithubEntity
import com.lopes.githubsearch.domain.repository.SearchLocalGithubInfoDataSource
import javax.inject.Inject

class SearchLocalRepoImplData @Inject constructor(
    private val appDatabase: AppDatabase
) : SearchLocalGithubInfoDataSource<AppDatabase> {

    override suspend fun insertAll(GithubEntities: List<GithubEntity>) {
        appDatabase.searchGithubDao().insertAll(GithubEntities)
    }

    override fun getAllByPagingSource(): PagingSource<Int, GithubEntity> {
        return appDatabase.searchGithubDao().getAllByPagingSource()
    }

    override suspend fun cleanAll() {
        appDatabase.searchGithubDao().cleanAll()
    }

    override fun getDatabase(): AppDatabase {
        return appDatabase
    }


}
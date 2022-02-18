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

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lopes.githubsearch.data.database.entities.GithubEntity

@Dao
interface SearchGithubDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(GithubEntities: List<GithubEntity>)

    @Query("select * from github_repo_entity ORDER by stars DESC")
    fun getAllByPagingSource(): PagingSource<Int, GithubEntity>

    @VisibleForTesting
    @Query("select * from github_repo_entity ORDER by stars DESC")
    suspend fun getAll(): List<GithubEntity>

    @Query("DELETE FROM github_repo_entity")
    suspend fun cleanAll()
}
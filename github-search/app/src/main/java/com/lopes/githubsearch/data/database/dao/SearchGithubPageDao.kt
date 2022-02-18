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
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lopes.githubsearch.data.database.entities.SearchGithubInfoPage

@Dao
interface SearchGithubPageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(infoPageKey: SearchGithubInfoPage)

    @Query("SELECT * FROM search_github_page_index WHERE label = :query")
    suspend fun searchRemoteKeyById(query: String): SearchGithubInfoPage?

    @Query("DELETE FROM search_github_page_index where :key")
    suspend fun deleteByKey(key: String)

    @Query("DELETE FROM search_github_page_index")
    suspend fun cleanAll()

    @VisibleForTesting
    @Query("SELECT * FROM search_github_page_index")
    suspend fun getAll(): List<SearchGithubInfoPage>
}

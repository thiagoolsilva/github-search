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

package com.lopes.githubsearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lopes.githubsearch.data.database.converter.DateConverter
import com.lopes.githubsearch.data.database.dao.SearchGithubDao
import com.lopes.githubsearch.data.database.dao.SearchGithubPageDao
import com.lopes.githubsearch.data.database.entities.GithubEntity
import com.lopes.githubsearch.data.database.entities.SearchGithubInfoPage

@Database(
    entities = [GithubEntity::class, SearchGithubInfoPage::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    // Database DAOs
    abstract fun searchGithubDao(): SearchGithubDao
    abstract fun searchGithubPageDao(): SearchGithubPageDao
}

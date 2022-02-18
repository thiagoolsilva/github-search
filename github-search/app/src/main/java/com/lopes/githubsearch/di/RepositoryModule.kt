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

package com.lopes.githubsearch.di

import com.lopes.githubsearch.data.database.AppDatabase
import com.lopes.githubsearch.data.repository.SearchRepoInfoImpl
import com.lopes.githubsearch.data.source.SearchLocalRepoImplData
import com.lopes.githubsearch.data.source.SearchLocalRepoPagingImpData
import com.lopes.githubsearch.data.source.SearchRemoteRepoImplData
import com.lopes.githubsearch.domain.repository.SearchGithubRepository
import com.lopes.githubsearch.domain.repository.SearchLocalGithubInfoDataSource
import com.lopes.githubsearch.domain.repository.SearchPagingLocalKeyDataSource
import com.lopes.githubsearch.domain.repository.SearchRemoteGithubDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesPagingLocalKeySource(impl: SearchLocalRepoPagingImpData): SearchPagingLocalKeyDataSource<AppDatabase>

    @Binds
    @Singleton
    abstract fun providesSearchInfoLocalGithubInfo(impl: SearchLocalRepoImplData): SearchLocalGithubInfoDataSource<AppDatabase>

    @Binds
    @Singleton
    abstract fun providesListGithubInfoRepository(impl: SearchRepoInfoImpl): SearchGithubRepository

    @Binds
    @Singleton
    abstract fun providesSearchRemoteGithubSource(impl: SearchRemoteRepoImplData): SearchRemoteGithubDataSource
}
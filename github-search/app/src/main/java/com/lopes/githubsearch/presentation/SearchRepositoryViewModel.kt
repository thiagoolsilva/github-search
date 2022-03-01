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

package com.lopes.githubsearch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.lopes.githubsearch.domain.interactor.ListGithubInfoUseCase
import com.lopes.githubsearch.presentation.mapper.toGithubInfoView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SearchRepositoryViewModel @Inject constructor(
    private val listGithubInfoUseCase: ListGithubInfoUseCase
) : ViewModel() {

    /**
     * Get a flow of github results from repository
     * @return StateFlow of Paging Data that lives for 5 seconds.
     */
    fun searchRepo(query: String) = listGithubInfoUseCase.execute(query).map { pagingData ->
        pagingData.map {
            it.toGithubInfoView()
        }
    }
        // cache pageData transformation
        .cachedIn(viewModelScope)
        .stateIn(
            initialValue = PagingData.empty(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )
}

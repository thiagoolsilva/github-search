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

package com.lopes.githubsearch.util.paging

import androidx.recyclerview.widget.DiffUtil
import com.lopes.githubsearch.domain.entity.SearchGithubDomain
import com.lopes.githubsearch.ui.model.GithubInfoView

/**
 * Search Github Domain Diff
 */
class SearchGithubDomainDiff : DiffUtil.ItemCallback<SearchGithubDomain>() {
    override fun areItemsTheSame(
        oldItem: SearchGithubDomain,
        newItem: SearchGithubDomain
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: SearchGithubDomain,
        newItem: SearchGithubDomain
    ): Boolean {
        return oldItem.id == newItem.id
    }
}

/**
 * Search Github Info View Diff
 */
class searchGithubInfoViewDiff : DiffUtil.ItemCallback<GithubInfoView>() {
    override fun areItemsTheSame(oldItem: GithubInfoView, newItem: GithubInfoView): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: GithubInfoView, newItem: GithubInfoView): Boolean {
        return oldItem.id == newItem.id
    }
}

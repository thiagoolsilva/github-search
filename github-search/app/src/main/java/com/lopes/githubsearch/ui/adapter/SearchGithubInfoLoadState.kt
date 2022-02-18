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

package com.lopes.githubsearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lopes.githubsearch.R
import com.lopes.githubsearch.databinding.LoadGithubFooterStateBinding

class SearchGithubInfoLoadState(
    private val retryRequest: () -> Unit
) : LoadStateAdapter<SearchGithubInfoLoadState.SearchViewHolder>() {

    override fun onBindViewHolder(holder: SearchViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.load_github_footer_state, parent, false)
        return SearchViewHolder(view, retryRequest)
    }

    inner class SearchViewHolder(view: View, retryRequest: () -> Unit) :
        RecyclerView.ViewHolder(view.rootView) {
        private val binding = LoadGithubFooterStateBinding.bind(view)

        init {
            binding.retryRequest.setOnClickListener {
                retryRequest.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.loadErrorMessage.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryRequest.isVisible = loadState is LoadState.Error
            binding.loadErrorMessage.isVisible = loadState is LoadState.Error
        }
    }
}
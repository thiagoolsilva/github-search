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
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lopes.githubsearch.R
import com.lopes.githubsearch.databinding.ContentItemBinding
import com.lopes.githubsearch.ui.model.GithubInfoView
import javax.inject.Inject

class SearchGithubPageAdapter @Inject constructor() :
    PagingDataAdapter<GithubInfoView, SearchGithubPageAdapter.SearchViewHolder>(
        diffSearchViewItems
    ) {

    override fun onBindViewHolder(holder: SearchGithubPageAdapter.SearchViewHolder, position: Int) {
        val selectedGithubItem = getItem(position)
        selectedGithubItem?.let {
            holder.bind(selectedGithubItem)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchGithubPageAdapter.SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_item, parent, false)
        return SearchViewHolder(view)
    }

    inner class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ContentItemBinding.bind(view)

        fun bind(item: GithubInfoView) {
            binding.contentItem.text = item.name
            binding.txtAuthorName.text = item.developerName
            binding.txtForkCount.text = item.forks.toString()
            binding.txtStarCount.text = item.stars.toString()

            val developerUrl = item.developerPhotoUrl

            //download and cache image into ImageView
            Glide.with(binding.imgDeveloperUrl.context)
                .load(developerUrl)
                .into(binding.imgDeveloperUrl)
        }
    }

    companion object {
        private val diffSearchViewItems = object : DiffUtil.ItemCallback<GithubInfoView>() {
            override fun areItemsTheSame(
                oldItem: GithubInfoView,
                newItem: GithubInfoView
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GithubInfoView,
                newItem: GithubInfoView
            ): Boolean {
                return newItem == oldItem
            }
        }
    }

}
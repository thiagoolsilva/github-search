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

package com.lopes.githubsearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lopes.githubsearch.databinding.ListTopKotlinRepoFragmentBinding
import com.lopes.githubsearch.presentation.SearchRepositoryViewModel
import com.lopes.githubsearch.ui.adapter.SearchGithubInfoLoadState
import com.lopes.githubsearch.ui.adapter.SearchGithubPageAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val DEFAULT_SEARCH_QUERY_REPOSITORY = "kotlin"

@AndroidEntryPoint
class ListTopKotlinRepoFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: ListTopKotlinRepoFragmentBinding? = null

    @Inject
    lateinit var searchRepositoryAdapter: SearchGithubPageAdapter
    private val viewModel: SearchRepositoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListTopKotlinRepoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            initAdapter()
            configSwipeLayout()
            fetchKotlinRepositories()
        }
    }

    /**
     * Config the Swipe Layout
     */
    private fun configSwipeLayout() {
        binding.swipeContainer.setOnRefreshListener {
            fetchKotlinRepositories()
        }
    }

    /**
     * Config the Page Adapter
     */
    private fun initAdapter() {
        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchRepositoryAdapter.withLoadStateFooter(
                footer = SearchGithubInfoLoadState { searchRepositoryAdapter.retry() }
            )
            // prevent lost recycle view state after rotation
            searchRepositoryAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        searchRepositoryAdapter.addLoadStateListener {
            // close swipe layout after event ended
            binding.swipeContainer.isRefreshing = false
        }
    }

    /**
     * Fetch kotlin repositories in Github using cache strategy
     */
    private fun fetchKotlinRepositories() {
        lifecycleScope.launch {
            viewModel.searchRepo(DEFAULT_SEARCH_QUERY_REPOSITORY)
                // only receive data when UI is being showing to the user.
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    searchRepositoryAdapter.submitData(it)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

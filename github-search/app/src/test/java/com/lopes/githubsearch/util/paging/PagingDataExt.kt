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

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Extension function to get data from PagingData source
 */
@ExperimentalCoroutinesApi
suspend fun <Input : Any> PagingData<Input>.collectData(differ: DiffUtil.ItemCallback<Input>): List<Input> {
    val listUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
    val asyncPagingDiffer = AsyncPagingDataDiffer(
        diffCallback = differ,
        updateCallback = listUpdateCallback,
        workerDispatcher = Dispatchers.Main
    )

    // execute flow data into AsyncPagingDataDiffer
    asyncPagingDiffer.submitData(this)

    return asyncPagingDiffer.snapshot().items
}

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

package com.lopes.githubsearch.data.mediator.cache

import com.lopes.githubsearch.crosscutting.addHour
import com.lopes.githubsearch.crosscutting.diffInHour
import java.util.Date
import javax.inject.Inject
import timber.log.Timber

class CacheStrategyImpl @Inject constructor() : CacheStrategy {

    override fun isCacheUpToDate(expectedCacheWindowInHour: Int, cacheReference: Date, lastSavedCache: Date?): Boolean {
        val isCacheUpToDate = when (lastSavedCache) {
            null -> false
            else -> {
                val diffInHour = cacheReference.diffInHour(lastSavedCache)
                diffInHour in 0..expectedCacheWindowInHour
            }
        }

        Timber.d("lastDbUpdate: $lastSavedCache, nextDbUpdate: ${lastSavedCache?.addHour(expectedCacheWindowInHour)}, " +
                "is cache up to date: $isCacheUpToDate, cache time in hour: $expectedCacheWindowInHour")

        return isCacheUpToDate
    }
}

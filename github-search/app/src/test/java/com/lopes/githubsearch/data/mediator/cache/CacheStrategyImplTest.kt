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
import com.lopes.githubsearch.crosscutting.addMinute
import com.lopes.githubsearch.model.ObjectInject
import java.util.Date
import org.junit.Assert
import org.junit.Test

class CacheStrategyImplTest {

    private val expectedCacheTimeWindowsInHour = 1
    private val currentDateTime = Date(System.currentTimeMillis())

    @Test
    fun should_return_false_when_no_cache_exists_in_database() {
        val cacheStrategy = CacheStrategyImpl()
        val result = cacheStrategy.isCacheUpToDate(expectedCacheTimeWindowsInHour, currentDateTime, null)

        Assert.assertFalse(result)
    }

    @Test
    fun should_return_false_when_cache_is_not_up_to_date() {
        val cacheStrategy = CacheStrategyImpl()
        val currentDate = ObjectInject.Model.Cache().copy()

        val result = cacheStrategy.isCacheUpToDate(expectedCacheTimeWindowsInHour, currentDateTime, currentDate.currentCacheHour.addHour(2))
        Assert.assertFalse(result)
    }

    @Test
    fun should_return_true_when_cache_is_smaller_than_last_cache_date() {
        val cacheStrategy = CacheStrategyImpl()
        val currentDate = ObjectInject.Model.Cache(currentDateTime).copy()
        val changedCurrentDate = currentDate.currentCacheHour.addMinute(20)

        val result = cacheStrategy.isCacheUpToDate(expectedCacheTimeWindowsInHour, currentDateTime, changedCurrentDate)
        Assert.assertTrue(result)
    }

    @Test
    fun should_return_false_when_cache_is_equals_to_last_cache_date() {
        val cacheStrategy = CacheStrategyImpl()
        val currentDate = ObjectInject.Model.Cache(currentDateTime).copy()
        val changedCurrentDate = currentDate.currentCacheHour.addHour(1)

        val result = cacheStrategy.isCacheUpToDate(expectedCacheTimeWindowsInHour, currentDateTime, changedCurrentDate)
        Assert.assertFalse(result)
    }

    @Test
    fun should_return_false_when_cache_is_greater_than_last_cache_date_in_one_minute() {
        val cacheStrategy = CacheStrategyImpl()
        val currentDate = ObjectInject.Model.Cache(currentDateTime).copy()
        val changedCurrentDate = currentDate.currentCacheHour.addMinute(61)

        val result = cacheStrategy.isCacheUpToDate(expectedCacheTimeWindowsInHour, currentDateTime, changedCurrentDate)
        Assert.assertFalse(result)
    }
}

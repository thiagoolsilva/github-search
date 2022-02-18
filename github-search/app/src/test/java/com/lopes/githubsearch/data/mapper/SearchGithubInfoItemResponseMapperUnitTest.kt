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

package com.lopes.githubsearch.data.mapper

import com.lopes.githubsearch.support.model.ObjectInject
import org.junit.Assert
import org.junit.Test

class SearchGithubInfoItemResponseMapperUnitTest {
    private val searchGithubInfoItemResponse by lazy {
        ObjectInject.searchGithubInfoItemResponse
    }

    @Test
    fun should_return_valid_search_github_domain() {
        val expectedGithubEntity = ObjectInject.githubEntity
        val result = searchGithubInfoItemResponse.toGithubEntity()
        Assert.assertEquals(result, expectedGithubEntity)
    }
}

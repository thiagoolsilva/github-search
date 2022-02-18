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

package com.lopes.githubsearch.data.api.dto

import com.squareup.moshi.Json

data class SearchGithubInfoItemResponse(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "full_name") val fullName: String,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "html_url") val url: String,
    @field:Json(name = "stargazers_count") val stars: Int,
    @field:Json(name = "forks_count") val forks: Int,
    @field:Json(name = "language") val language: String?,
    @field:Json(name = "owner") val owner: SearchGithubOwner
)

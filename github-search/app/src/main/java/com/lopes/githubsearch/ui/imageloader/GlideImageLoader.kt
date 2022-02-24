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

package com.lopes.githubsearch.ui.imageloader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class GlideImageLoader @Inject constructor() : ImageLoader {
    override fun load(context: Context, url: String, view: ImageView) {
        Glide.with(context)
            .load(url)
            .into(view)
    }
}
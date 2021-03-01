/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Loading image through Glide
 * @BindingAdapter() it will tell "data binding" that we want this "binding adapter" bindImage()
 * EXECUTED, when our chosen XML item will have the imageUrl attribute.
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {

    // It is a conditional statement
    imgUrl?.let {

        // This code will convert "a string" to a "Uri Object"
        // 1. toUri() is a Kotlin extension function provided by Android KTX core library.
        // 2. Since our server wants a secure connection, that's why we will use HTTPS Scheme to
        // append https using buildUpon().scheme("https") method.
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        // Now, by using our Uri Object (which has our URL Link) we can easily load our picture
        // by using following code:
        Glide.with(imgView.context)

                // We are providing URL through "Uri Object"
                .load(imgUri)

                // Optional: Placeholder & No Connection icons
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))

                // Targeted <ImageView> component in which the image will be loaded.
                .into(imgView)
    }

}

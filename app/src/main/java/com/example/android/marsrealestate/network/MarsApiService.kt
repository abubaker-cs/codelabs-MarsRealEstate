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

package com.example.android.marsrealestate.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

// We will create Moshi Builder to parse JSON data
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

// Retrofit's Builder needs two things to build a "Web Service API":
// 1. A BASE_URL to work with
// 2. A Converter Factory: It tells Retrofit what to do with the data it gets back from the web service.
// Instead of ScalarConverterFactory, we are now using MoshiConverterFactory
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

// It will define "HOW" Retrofit will talk to the web server using HTTP Requests
interface MarsApiService {

    // Based on our provided ENDPOINT a CALL object will be used to start the web request
    // Format: BASE_URL/Endpoint
    // Example: https://android-kotlin-fun-mars-server.appspot.com/realestate
    // Note: the structure for the MarsProperty is defined in our data class in MarsProperty.kt file
    @GET("realestate")
    suspend fun getProperties(): List<MarsProperty>

}

// Public Object: It will be used to initialize the Retrofit service from internal files,
// i.e. we will use it to make calls from OverviewViewModel.kt file
object MarsApi {

    // Create() method will create teh Retrofit service itself with our MarsApiService interface
    // defined above, due to its nature we will prefer to initialize it using "lazily".

    // Remember "lazy instantiation" is when object creation is purposely delayed until you actually
    // need that object to avoid unnecessary computation or use of other computing resources.
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }

}
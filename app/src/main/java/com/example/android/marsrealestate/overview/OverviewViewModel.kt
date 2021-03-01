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

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.Callback
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    // LiveData for a Single Mars Property
    private val _properties = MutableLiveData<List<MarsProperty>>()

    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    // private val _property = MutableLiveData<MarsProperty>()

    // val property: LiveData<MarsProperty>
    // get() = _property

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties() {

        _response.value = "Connecting to the server..."

        viewModelScope.launch {

            try {
                val listResult = MarsApi.retrofitService.getProperties()
                _response.value = "Success: ${listResult.size} Mars properties retrieved"

                // It sets the value of the _property to the 0-index inside the ListResult
                if (listResult.size > 0) {
                    // _property.value = listResult[0]
                    _properties.value = listResult
                }

            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }

        }


        // _response.value = "Set the Mars API Response here!"

        // We are using enqueue to start the network request on a background thread
        //MarsApi.retrofitService.getProperties().enqueue(

        // This object will contain the type of the response received from the web server
        // object : Callback<List<MarsProperty>> {

        // It is called when the web service response is successful
        // override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
        //    _response.value = "Success: ${response.body()?.size} Mars properties retrieved"
        //}

        // It is called when the web service response is failed
        //override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
        // _response.value = "Failure: " + t.message
        //}

        //})
    }
}

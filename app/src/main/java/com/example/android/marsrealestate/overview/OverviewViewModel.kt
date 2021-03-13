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
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch

// Our enum will represent all the available statuses
enum class MarsApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the response String
    val status: LiveData<MarsApiStatus>
        get() = _status

    // LiveData: It will hold the "entire list" of MarsProperty Objects
    private val _properties = MutableLiveData<List<MarsProperty>>()

    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    /**
     * Once the user will click on an item inside the RecyclerView's GRID then following code
     * will be helpful for navigating to the Detailed View
     */
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty


    // Assign to the selected Mars property
    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    // Marks the navigation state to complete
    // It will also help in avoid navigation being triggered again when the user will return from the detail view.
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }


    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {

        viewModelScope.launch {

            // 1. State: Loading
            _status.value = MarsApiStatus.LOADING

            try {

                // This code will be used to initialize the web request (ref: MarsApiServices.kt file)
                val listResult = MarsApi.retrofitService.getProperties(filter.value)

                // 2. State: Success
                _status.value = MarsApiStatus.DONE

                // It sets the value of the _property to the 0-index inside the ListResult
                if (listResult.size > 0) {
                    _properties.value = listResult
                }

            } catch (e: Exception) {

                // 3. State: Error
                _status.value = MarsApiStatus.ERROR

                // It will clear the RecyclerView
                _properties.value = ArrayList()
            }

        }

    }

    // It will initialize the filter query
    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }
}

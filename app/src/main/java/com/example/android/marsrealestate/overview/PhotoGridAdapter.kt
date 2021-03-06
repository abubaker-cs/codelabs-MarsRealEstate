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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

// It is important to import these classes
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty

/**
 * Since now:
 * = fragment_overview.xml (has a) RecyclerView
 * = grid_view_item.xml (has a) Single ImageView
 *
 * So we can bind the data to the RecyclerView through this RecyclerView Adapter
 * Our Adapter will extend ListAdapter<> with following parameters:
 * 1. List item type
 * 2. ViewHolder
 * 3. DiffUtil.ItemCallback
 */
class PhotoGridAdapter : ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {

    // It will extend DiffUtil.ItemCallback<> with the type of object we want to compare,
    // i.e MarsProperty that is defined in the MarsProperty.kt file with:
    // id, imgSrcUrl, type and price
    companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {

        // It will return true if the "object references" are same
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem === newItem
        }

        // Returns true if the content is same
        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }

    // We are defining an inner class definition for MarsPropertyViewHolder,
    // that will extend RecyclerView.ViewHolder
    // Note: We need GridViewItemBinding for binding the MarsProperty to the layout (XML File)
    class MarsPropertyViewHolder(private var binding: GridViewItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        // It will take MarsProperty as an argument,
        fun bind(marsProperty: MarsProperty) {

            // set binding.property to that object
            binding.property = marsProperty

            // Then execute changes immediately
            binding.executePendingBindings()
        }

        /**
         * Important: This change may cause data-binding errors in Android Studio,
         * to resolve those errors, we may need to clan and rebuild the app.
         */
    }

    /**
     * We implemented these ListAdapter methods by pressing Ctrl + i
     */

    // onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.MarsPropertyViewHolder {

        //
        return MarsPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))

    }

    // onBindViewHolder
    override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPropertyViewHolder, position: Int) {

        //
        val marsProperty = getItem(position)
        holder.bind(marsProperty)

    }

}




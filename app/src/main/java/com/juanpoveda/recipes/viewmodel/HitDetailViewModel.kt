package com.juanpoveda.recipes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juanpoveda.recipes.model.Hit

class HitDetailViewModel : ViewModel() {
    private val _selectedHit = MutableLiveData<Hit>()
    val selectedHit: LiveData<Hit>
        get() = _selectedHit

    init {
    }

    fun setNewHit(hit: Hit) {
        _selectedHit.value = hit
    }
}
package com.juanpoveda.recipes.lifecycleobservers

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

// ****LifeCycle s1: Select a class to observe for the lifecycle of an Activity or Fragment. Receive a Lifecycle param in the class
// and extend of LifecycleObserver. There are 3 parts in the Jetpack's Lifecycle library:
//  -Lifecycle owners, which are the components that have (and "own") a lifecycle. Activity and Fragment are lifecycle owners. Lifecycle owners implement the LifecycleOwner interface.
//  -The Lifecycle class, which holds the actual state of a lifecycle owner and triggers events when lifecycle changes happen.
//  -Lifecycle observers, which observe the lifecycle state and perform tasks when the lifecycle changes. Lifecycle observers implement the LifecycleObserver interface.
class LifeCycleLogging (lifecycle: Lifecycle, tag: String): LifecycleObserver {
    private var tag: String
    init {
        // ****LifeCycle s2: In the init block add the observer to the lifecycle object
        lifecycle.addObserver(this)
        this.tag = tag
    }

    // ****LifeCycle s3: Annotate the methods that you want to execute according to the lifecycle event.
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun logOnCreateCall() {
        Log.i(tag, "OnCreate called with LifecycleObserver")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun logOnStopCall() {
        Log.i(tag, "OnStop called with LifecycleObserver")
    }
}
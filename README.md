# Sandbox-android-app
Sandbox Android app with step by step comments to implement the most common and new libs/components/features etc.

Search for these comments in the app to check the steps needed to implement that feature:

// ****ViewBindingFragment - Use view binding to replace findViewById for Fragments (Jetpack ViewBinding)

// ****ViewBindingActivity - Use view binding to replace findViewById for Activities (Jetpack ViewBinding)

// ****ViewModel - Create ViewModel and connect with View and repository. (Jetpack LiveData, ViewModel)

// ****Transformations - Use Transformations to apply changes to existing LiveData variables in one single line.

// ****Room - Create a Room database. (Jetpack Room, Coroutines)

// ****RoomTest - Create an instrumented test to test Room database. (Testing)

// ****Repository - Create a Repository to manage all the data source in 1 place. Requires: ****Room, ****Retrofit

// ****WorkManager - WorkManager to schedule a background task in an optimized and efficient way. WorkManager is for background work that's deferrable and requires guaranteed execution:
  - Deferrable means that the work is not required to run immediately. For example, sending analytical data to the server or syncing the database in the background is work that can be deferred.
  - Guaranteed execution means that the task will run even if the app exits or the device restarts.(Jetpack WorkManager)
  While WorkManager runs background work, it takes care of compatibility issues and best practices for battery and system health. WorkManager offers compatibility back to API level 14. WorkManager chooses an appropriate way to schedule a background task, depending on the device API level. It might use JobScheduler (on API 23 and higher) or a combination of AlarmManager and BroadcastReceiver.

// ****Navigation - Implement Navigation component (Jetpack Navigation)

// ****NavigationWithLiveData - Implement Navigation between Fragments using LiveData. This is helpful when you want to launch fragments after performing some tasks like network calls or database operations (Jetpack Navigation, LiveData)

// ****SafeArgs - Pass data between Fragments (part of Jetpack Navigation)

// ****LifeCycle - Use the Jetpack's LifeCycle lib to observe changes in the lifecycle of an Activity/Fragment  (Jetpack LifeCycles)

// ****NavigationDrawer - Add a Navigation Drawer to the app using Jetpack's navigation features

// ****RecyclerView - Implement a RecyclerView using androidx, itemClickListener and ViewBinding.

// ****RecyclerViewDiffUtil - Add DiffUtil for a RecyclerView. This helps to achieve a better performance avoiding the call of notifyDataSetChanged()

// ****RecyclerViewGridLayout - RecyclerView with a GridLayout to display a grid of items

// ****RecyclerViewHeader - Add a header (or multiple view types) to an existing RecyclerView

// ****RecyclerViewErrorHandling - Add status to an existing RecyclerView, that way the app can show loading and network error images.

// ****ViewBindingRecyclerView - Use view binding to replace findViewById for RecyclerView (Jetpack ViewBinding)

// ****DownloadableFontsAndStyles - Add downloadable fonts and some styles to the app.

// ****Notifications - Configure a notification channel, show, dismiss and customize some notifications in the app.

// ****CustomView - Create a custom view from scratch (extending View).

// ****Retrofit - Implement retrofit to call endpoints and get the data

// ****Glide - Implement and use Glide to load images

// ****ShareWithOtherApps - Add toolbar share button and send an implicit intent to share content with other apps

// ****Timber - Log events with the Timber lib.

// ****InstrumentedTest - Setup an instrumented test (requires Android device). (Testing)



----- Pending ------

- JetPack's Architecture components:

  * Paging

- JetPack's UI:

  * Animations & transitions
  * Auto, TV & Wear
  * Emoji
  * Layout
  * Palette

- JetPack's Behavior:

  * DownloadManager
  * Media & Playback
  * Permissions
  * Sharing
  * Slices

- Foundation:

  * Test (instrumented done)



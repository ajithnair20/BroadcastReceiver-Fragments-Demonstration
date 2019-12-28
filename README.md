# BroadcastReceiver-Fragments-Demonstration 

The project aims at demonstarting the usage of the concepts of Broadcast Receiver and Fragments in Android. The project compries of three Applications A1, A2 and A3 which are to be installed on the same device which communicate with each other using broadcast receivers.

## Broadcast Receivers in Android
A broadcast receiver (receiver) is an Android component which allows you to register for system or application events. All registered receivers for an event are notified by the Android runtime once this event happens. To know more about broadcast receivers in Android please follow the [link.](https://developer.android.com/reference/android/content/BroadcastReceiver)

## Fragments in Android
A Fragment represents a behavior or a portion of user interface in a FragmentActivity. You can combine multiple fragments in a single activity to build a multi-pane UI and reuse a fragment in multiple activities. You can think of a fragment as a modular section of an activity, which has its own lifecycle, receives its own input events, and which you can add or remove while the activity is running (sort of like a "sub activity" that you can reuse in different activities). To know more about broadcast receivers in Android please follow the [link.](https://developer.android.com/guide/components/fragments)

## Implementation
The project comprises of three applications - A1, A2 and A3. 
#### A1
A1's UI has a welcome message and a button on pressing which it asks for a permission defined in A3. On granting the permission it launches A2.If the permission is denied, the application dislpays a toast message mentioning that the permission was denied and the app cannot proceed.
#### A2
A2's UI again comprises of a welcome message and a button. On pressing the button, the application prompts the user to grant message defined in A3. After granting the permission, it launches app A3. If the permission is denied, the app displays a toast message and close the application by itself.
#### A3
A3 demonsrates the usage of Dynamic Fragments and Option Menus. It has a fragment displaying the list of cell phones and a second fragment which displays the image of the cell phone selected. The fragments layout changes when the device's orientation is changed meanwhile retaining the user's selection. The applicaiton has an option menu with option to exit the application and another option to broadcast a message to other two apps.

The permission ***edu.uic.cs478.s19.kaboom*** is defined in application A3. Apps A2 and A1 registers themselves to listen to the intent of broadcast message from A3. On selecting the Launch Apps option in A3, it broadcasts the URL of the website of the cell phone selected. In case no cell phone is selected then the applicaiton displays a toast message. An ordered braodcast is transmitted with App A2 having higher priority first receives the message and displays a toast message. After that, A1 receives the message and on receiving the message opens a new web activity and displays the website of the cell phone selected in A3.
## Setting up the environment
After cloning the repository, import the project into Android Studio. Setup an emulator using AVD manager. The application supports API level 28 (Pie) and above.

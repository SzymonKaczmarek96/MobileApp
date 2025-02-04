#MobileApp
A Mobile Application built with Jetpack Compose and MVVM architecture for managing tasks (Todo items). 
This app allows users to add, delete, and sort tasks based on different criteria, such as creation date and priority. 
It uses Room Database for local data persistence and follows modern Android development practices.

##Features

Add Tasks: Add new tasks with a title and automatic timestamp.

Delete Tasks: Remove tasks you no longer need.

Update Task Priority: Change the priority of tasks (Low, Medium, High).

Sort Tasks: Sort tasks by:

Young: Newest tasks first.

Old: Oldest tasks first.

Priority: Highest priority tasks first.

Responsive UI: Built with Jetpack Compose for a modern and dynamic user interface.

Data Persistence: Tasks are stored in a local database using Room.

###Technologies Used

Jetpack Compose: Modern UI toolkit for building native Android apps.

ViewModel: Manages UI-related data and survives configuration changes.

LiveData: Observable data holder for UI updates.

Room Database: Local database for storing tasks.

Coroutines: For asynchronous programming and background tasks.

####Setup Instructions

Prerequisites
Android Studio (latest version recommended).

Android device or emulator with API level 21 or higher.

Steps
Clone the Repository:
git clone https://github.com/SzymonKaczmarek96/MobileApp.git
cd MobileApp

Open the Project in Android Studio:
Launch Android Studio.

Select Open an Existing Project and navigate to the cloned repository.
Build and Run:
Connect an Android device or start an emulator.
Click Run in Android Studio to build and launch the app.


package com.example.baseapp3

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//for dependency injection
//has to be added to the manifest?
@HiltAndroidApp
class NoteApp : Application ()
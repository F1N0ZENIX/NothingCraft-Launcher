package com.nothingtube

import android.app.Application
import com.nothingtube.data.local.AppDatabase
import com.nothingtube.data.repository.VideoRepository

class NothingTubeApp : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { VideoRepository(database.videoDao()) }
}

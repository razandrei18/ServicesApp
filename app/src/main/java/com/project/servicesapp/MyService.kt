package com.project.servicesapp

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.view.ContentInfoCompat
import com.project.servicesapp.Constants.CHANNEL_ID
import com.project.servicesapp.Constants.MUSIC_NOTIFICATION_ID

class MyService: Service() {

    private lateinit var musicPlayer:MediaPlayer

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initMusic()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        musicPlayer.start()

        return START_STICKY
    }

    private fun showNotification(){
        val notificationIntent = Intent(this, MainActivity::class.java)


        val pendingIntent = PendingIntent.getActivities(
            this, 0, arrayOf(notificationIntent), PendingIntent.FLAG_IMMUTABLE
        )

        val notification = Notification
            .Builder(this, CHANNEL_ID)
            .setContentText("Music Player")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(MUSIC_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(){
        val servicesChannel = NotificationChannel(
            CHANNEL_ID, "Music Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val manager = getSystemService(
            NotificationManager::class.java
        )

        manager.createNotificationChannel(servicesChannel)
    }

    private fun initMusic(){
        musicPlayer = MediaPlayer.create(this, R.raw.mymusic)
        musicPlayer.isLooping = true
        musicPlayer.setVolume(100F, 100F)
    }
}
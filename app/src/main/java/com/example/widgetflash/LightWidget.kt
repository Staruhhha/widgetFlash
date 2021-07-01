package com.example.widgetflash

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Camera
import android.hardware.camera2.CameraManager
import android.os.Build
import android.widget.RemoteViews
import androidx.core.content.ContextCompat.getSystemService

/**
 * Implementation of App Widget functionality.
 */
class LightWidget : AppWidgetProvider() {



    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val action : String = intent!!.getAction().toString()

        if (action == "falshOn"){

            val cameraM : CameraManager? = context!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager

            val cameraList = cameraM!!.cameraIdList[0]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraM!!.setTorchMode(cameraList,true)
            }

        }

    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.light_widget)


    val intent : Intent = Intent(context, LightWidget::class.java)
    intent.setAction("falshOn")
    val pendingIntent : PendingIntent = PendingIntent.getBroadcast(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)

        views.setOnClickPendingIntent(
            R.id.flashOnOff,
            pendingIntent
        )

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}





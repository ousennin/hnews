package cobyx.gnezdo.hnews.ui

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import cobyx.gnezdo.hnews.network.NewsService


class NewsWidget : AppWidgetProvider() {
    companion object {
        private const val SERVICE_REQUEST_CODE = 0
    }

    private var pendingIntent: PendingIntent? = null

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val intent = Intent(context, NewsService::class.java)
        if (pendingIntent == null)
            pendingIntent = PendingIntent.getService(
                context,
                SERVICE_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

        context?.let { NewsService.enqueueWork(it, intent) }
    }
}
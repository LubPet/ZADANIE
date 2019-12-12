package com.example.mobv.model.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import com.example.mobv.model.Wifi


class WifiRepository(private val ctx: Context) {

    fun performScan(onSuccess: (List<Wifi>) -> Unit, onFailure: () -> Unit) {

        val wifiManager: WifiManager = ctx.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val wifiScanReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {

                if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION == intent.action) {
                    val wifis = ArrayList<Wifi>()
                    wifiManager.scanResults.forEach {
                        wifis.add(Wifi(it.SSID, it.BSSID))
                    }
                    context.unregisterReceiver(this)
                    onSuccess(wifis)

                } else {
                    context.unregisterReceiver(this)
                    onFailure()
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
         ctx.registerReceiver(wifiScanReceiver, intentFilter)

        val success = wifiManager.startScan()
        if (!success)
            onFailure()
    }

    fun getConnectionInfo(): List<Wifi> {
        val wifiManager: WifiManager = ctx.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifi = wifiManager.connectionInfo

        val wifis = ArrayList<Wifi>()

        if (wifi == null) {
            return ArrayList()
        }

        var bssid = ""
        if (wifi.bssid != null) {
            bssid = wifi.bssid.toString()
        }

        var ssid = ""
        if (wifi.ssid != null) {
            ssid = wifi.ssid.toString()
        }

        if (bssid.trim() == "" && ssid.trim() == "") {
            return ArrayList()
        }

        wifis.add(
            Wifi(
                ssid,
                bssid
            )
        )

        return wifis
    }

    companion object {

        fun create(ctx: Context): WifiRepository {
            return WifiRepository(ctx)
        }
    }


}
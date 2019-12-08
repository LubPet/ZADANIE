package com.example.mobv.wifi.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import com.example.mobv.wifi.repository.domain.Wifi


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
//                    context.unregisterReceiver(this)
                    onSuccess(wifis)

                } else {
//                    context.unregisterReceiver(this)
                    onFailure()
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
         ctx.registerReceiver(wifiScanReceiver, intentFilter)

        val success = wifiManager.startScan()
        println("KOKOT")
    }


    companion object {

        fun create(ctx: Context): WifiRepository {
            return WifiRepository(ctx)
        }
    }


}
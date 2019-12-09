package com.example.mobv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobv.Adapter.RoomAdapter

class RoomsActivity : AppCompatActivity() {

    private val roomsAdapter: RoomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)
    }
}

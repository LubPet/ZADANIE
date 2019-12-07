package com.example.mobv.api.responses

import com.example.mobv.utils.DateUtils
import java.util.*

class ContactMessage(val uid: String, val message: String, val time: String, val uid_name: String, val contact_name: String, val uid_fid: String, val contact_fid: String) {

    fun getTime(): Date {
        return DateUtils.parseTime(time)!!
    }
}
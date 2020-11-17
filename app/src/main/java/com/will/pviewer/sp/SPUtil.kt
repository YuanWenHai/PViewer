package com.will.pviewer.sp

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.will.pviewer.setting.SERVER_HOME_NET
import com.will.pviewer.setting.SERVER_LIB_NET
import org.json.JSONArray

/**
 * created  by will on 2020/11/17 11:55
 */
const val APP_SP_NAME = "app_shared_preference"
class SPUtil {

    companion object{
        private const val serverKey = "key_server"
        private const val KEY_CURRENT_SERVER = "key_current_server"
        private const val KEY_OTHER_SERVER = "key_other_server"

        fun getCurrentServer(context: Context): String{
            return context.getSharedPreferences(APP_SP_NAME,Context.MODE_PRIVATE).getString(
                KEY_CURRENT_SERVER,null) ?: SERVER_HOME_NET
        }
        fun setCurrentServer(context: Context,server: String){
            context.getSharedPreferences(APP_SP_NAME,Context.MODE_PRIVATE).edit().putString(
                KEY_CURRENT_SERVER,server).apply()
        }
        fun getOtherServer(context: Context): String{
            return context.getSharedPreferences(APP_SP_NAME,Context.MODE_PRIVATE).getString(
                KEY_OTHER_SERVER,null) ?: SERVER_LIB_NET
        }
        fun setOtherServer(context: Context,server: String){
            context.getSharedPreferences(APP_SP_NAME,Context.MODE_PRIVATE).edit().putString(
                KEY_OTHER_SERVER,server).apply()
        }
        fun switchServer(context: Context){
            val current = getCurrentServer(context)
            val other = getOtherServer(context)
            setCurrentServer(context,other)
            setOtherServer(context,current)
        }
    }




}
package uz.gita.flagquizapp.threadimpl

import android.content.Context
import android.content.SharedPreferences
import uz.gita.flagquizapp.contract.ContractThread

class ModelThread(context: Context):ContractThread.Model {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val FILE_NAME = "GITA1"

    init {
        loadShared(context)
    }

    private fun loadShared(context: Context) {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    override fun readShared(): IntArray {
        val arr = IntArray(2)
        val str = sharedPreferences!!.getString("s", "")
        if (str != "") {
            arr[0] = str!!.length
        }
        val countQuestion = sharedPreferences!!.getInt("countQuestion", -1)
        if (countQuestion != -1) {
            arr[1] = countQuestion
        }
        return arr
    }
}
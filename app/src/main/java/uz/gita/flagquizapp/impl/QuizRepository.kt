package uz.gita.flagquizapp.impl

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import uz.gita.flagquizapp.R
import uz.gita.flagquizapp.contract.QuizContract
import uz.gita.flagquizapp.models.QuizData

class QuizRepository (context: Context): QuizContract.Model {

    private val dataAsia: ArrayList<QuizData> = ArrayList<QuizData>()

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val FILE_NAME = "GITA1"

    init {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        loadDate()
    }

    private fun loadDate() {
        dataAsia.add(QuizData(R.drawable.uzbekistan, "узбекистан", "зутукенгтслдазби"))
        dataAsia.add(QuizData(R.drawable.qozogiston, "казахстан", "лаыжткьачсшнзэах"))
        dataAsia.add(QuizData(R.drawable.tojikistion, "таджикистан", "засджаотшйинтхки"))
        dataAsia.add(QuizData(R.drawable.india_flag, "индия", "дижуумнуяэииввлщ"))
        dataAsia.add(QuizData(R.drawable.korea, "южнаякорея", "рнцжоажеузякюияе"))
        dataAsia.add(QuizData(R.drawable.germaniya, "германия", "яршбнёщтимеащнпг"))
        dataAsia.add(QuizData(R.drawable.spain, "испания", "аяажисийннахыпащ"))
        dataAsia.add(QuizData(R.drawable.fransiya, "франция", "шмриэжыеёафснцая"))
        dataAsia.add(QuizData(R.drawable.italiya, "италия", "плмиклюмяътшцапи"))
        dataAsia.add(QuizData(R.drawable.italiya, "португалия", "яптлтйочтгритуам"))
        dataAsia.add(QuizData(R.drawable.argentina, "аргентина", "нжиётунлнгдрааюе"))
        dataAsia.add(QuizData(R.drawable.brazilya, "бразилия", "бзщряилижлыаиеуж"))
        dataAsia.add(QuizData(R.drawable.urugvay, "уругвай", "йзвряилужлгаиеуж"))
        dataAsia.add(QuizData(R.drawable.kolimbiya, "колумбия", "иоцуплдчобиякмоу"))
        dataAsia.add(QuizData(R.drawable.peru, "перу", "роцуплдчебиякмоу"))
    }

    override fun get(level: Int): QuizData? {
        return dataAsia[level]
//            return dataEvropa.get(level);
//            return dataAmerica.get(level);
    }

    override fun countQuestion(): Int {
        return dataAsia.size
    }

    override fun writeShare(list: List<Int?>?) {
        val stringBuilder = StringBuilder()
        for (i in list!!.indices) {
            stringBuilder.append( "${list[i]}"+"#")
//            list!![i].toString()
        }
        val s = stringBuilder.toString()
        editor!!.putString("s", s)
        editor!!.apply()
        write()
    }


    override fun readShare(): List<Int>? {
        val list: MutableList<Int> = ArrayList()
        val str = sharedPreferences!!.getString("s", "")
        if (str != "") {
            val split = str!!.split("#").toTypedArray()
            for (i in split.indices) {
                Log.d("TTT","${split[i]}")
                if (split[i]!="") {
                    val parseInt = split[i].toInt()
                    list.add(parseInt)
                }
            }
        }
        return list
    }


    override fun writeBall(ball: Int) {
        editor!!.putInt("ball", ball)
        editor!!.apply()
    }

    override fun readBall(): Int {
        val ball = sharedPreferences!!.getInt("ball", -1)
        return if (ball != -1) {
            ball
        } else -1
    }

    private fun write() {
        editor!!.putInt("countQuestion", dataAsia.size)
        editor!!.apply()
    }
}
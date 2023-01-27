package uz.gita.flagquizapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class DialogInfo(context: Context, private var topilganlarSoni:Double, private  var savollarniUmumiySoni:Double) : AlertDialog(context) {

    lateinit var listener:(() ->Unit)




    private lateinit var asia:TextView
    private lateinit var yevropa: TextView
    private lateinit var amerika: TextView
    private lateinit var btn_close: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_info)
        loadView()
        setCancelable(false)
        btn_close!!.setOnClickListener { view: View? ->
            listener.invoke()
        }
        val n = topilganlarSoni * 100 / savollarniUmumiySoni
        val k = n.toInt()
        asia!!.text = "$k%"
    }

//    protected fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.dialog_info)
//        loadView()
//        setCancelable(false)
//        btn_close!!.setOnClickListener { view: View? ->
//            if (hasListener()) {
//                onButtonClickListener!!.onClick()
//            }
//        }
//        val n = topilganlarSoni * 100 / savollarniUmumiySoni
//        val k = n.toInt()
//        asia!!.text = "$k%"
//    }

    private fun loadView() {

        btn_close = findViewById<ImageView>(R.id.close_dialog)!!
        asia = findViewById<TextView>(R.id.aziya_tv)!!
        yevropa = findViewById<TextView>(R.id.evropa_tv)!!
        amerika = findViewById<TextView>(R.id.amerika_tv)!!
    }

    fun setOnClickListener(l:() -> Unit){
        listener = l
    }

}
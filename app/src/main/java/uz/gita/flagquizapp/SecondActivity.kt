package uz.gita.flagquizapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import uz.gita.flagquizapp.contract.SecondContract
import uz.gita.flagquizapp.secondimpl.SecondMenager
import uz.gita.flagquizapp.secondimpl.SecondModel

class SecondActivity : AppCompatActivity(),SecondContract.ViewSecond {

    private lateinit var precenter: SecondContract.PrecenterSecond

    private lateinit var back_btn_menu: ImageView
    private lateinit var asia_btn: CardView
    private lateinit var evropa_btn: CardView
    private lateinit var amerika_btn: CardView
    private lateinit var image_group1: ViewGroup
    private lateinit var image_group2: ViewGroup
    private lateinit var image_group3: ViewGroup
    private lateinit var image_group4: ViewGroup
    private lateinit var image_group5: ViewGroup
    private lateinit var mediaPlayer: MediaPlayer

    private val imageViewList: ArrayList<ImageView> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        precenter = SecondMenager(SecondModel(), this)

        mediaPlayer = MediaPlayer.create(this, R.raw.music1)
        loadViews()
        precenter.init()
        back_btn_menu.setOnClickListener { v: View? ->
            mediaPlayer.start()
            finish()
        }

    }


    override fun loadViews() {
        back_btn_menu = findViewById(R.id.back_menu)
        loadImageButtons(imageViewList, R.id.image_group, object :OnClickListener{
            override fun onClick(index: Int) {
                precenter.onClickImage(index)
            }

        })
        loadImageButtons(imageViewList, R.id.image_group1, object :OnClickListener{
            override fun onClick(index: Int) {
                precenter.onClickImage(index)
            }

        })
        loadImageButtons(imageViewList, R.id.image_group2, object :OnClickListener{
            override fun onClick(index: Int) {
                precenter.onClickImage(index)
            }

        })
        loadImageButtons(imageViewList, R.id.image_group3, object :OnClickListener{
            override fun onClick(index: Int) {
                precenter.onClickImage(index)
            }

        })
        loadImageButtons(imageViewList, R.id.image_group4, object :OnClickListener{
            override fun onClick(index: Int) {
                precenter.onClickImage(index)
            }

        })
    }

    private fun loadImageButtons(
        imageViewList: MutableList<ImageView>,
        groupId: Int,
        listener: OnClickListener
    ) {
        val group = findViewById<ViewGroup>(groupId)
        val oldSize = imageViewList.size
        for (i in 0 until group.childCount) {
            val childAt = group.getChildAt(i)
            if (childAt is ImageView) {
                val imageView = childAt
                val index = oldSize + i
                imageView.setOnClickListener { v: View? ->
                    listener.onClick(
                        index
                    )
                }
                imageViewList.add(imageView)
            }
        }
    }


    override fun showImage(index: Int, image: Int) {
        imageViewList[index].setImageResource(image)
    }

    override fun intent(index: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id", index)
        startActivity(intent)
    }

    override fun startMusic() {
        mediaPlayer.start()
    }


    internal interface OnClickListener {
        fun onClick(index: Int)
    }
}
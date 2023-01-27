package uz.gita.flagquizapp

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import uz.gita.flagquizapp.contract.QuizContract
import uz.gita.flagquizapp.impl.QuizManager
import uz.gita.flagquizapp.impl.QuizRepository
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), QuizContract.View {

    private val answerButtons: ArrayList<Button> = ArrayList()
    private val variantButtons: ArrayList<Button> = ArrayList()
    private lateinit var imageQuestion: ImageView
    private lateinit var presenter: QuizContract.Presenter
    private lateinit var  btn_yordam: CardView
    private lateinit var btn_clear: CardView
    private lateinit var back_btn_bar: ImageView
    private lateinit var home_btn_bar: ImageView
    private lateinit var counter: TextView
    private lateinit var ball: TextView
    private lateinit var backQueston_btn: ImageButton
    private lateinit var nextQueston_btn: ImageButton

    private lateinit var liner_clear: View
    private lateinit var win_img: ImageView
    private lateinit var win_tv: TextView
    private lateinit var tekst_win: TextView
    private lateinit var win_img_achko: ImageView

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayer1: MediaPlayer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val extras = intent.extras
        val id = extras!!.getInt("id")
        presenter = QuizManager(QuizRepository(this), this, id)

        mediaPlayer = MediaPlayer.create(this, R.raw.music1)
        mediaPlayer1 = MediaPlayer.create(this, R.raw.musicwin)
        loadViews()

        presenter.init()
    }


    override fun onPause() {
        super.onPause()
        presenter.writeAll()
    }


    override fun loadViews() {
        imageQuestion = findViewById(R.id.image_question)
        btn_yordam = findViewById(R.id.btn_yordam)
        btn_clear = findViewById(R.id.btn_clear)
        back_btn_bar = findViewById(R.id.back_btn_bar)
        home_btn_bar = findViewById(R.id.home_btn_bar)
        counter = findViewById(R.id.tv_bar)
        ball = findViewById(R.id.ball)
        backQueston_btn = findViewById(R.id.image_btn_back)
        nextQueston_btn = findViewById(R.id.image_btn_next)
        liner_clear = findViewById(R.id.clear_liner)
        win_img = findViewById(R.id.win_img)
        win_tv = findViewById(R.id.win_tv)
        win_img_achko = findViewById(R.id.win_img_achko)
        tekst_win = findViewById(R.id.tekst_win)
        loadButtons(answerButtons, R.id.layout_btn_ansver, object :OnClickListener{
            override fun onClick(index: Int) {
                presenter.onClickAnswer(index)
            }

        })
        loadButtons(answerButtons, R.id.layout_btn_ansver1, object :OnClickListener{
            override fun onClick(index: Int) {
                presenter.onClickAnswer(index)
            }

        })
        loadButtons(variantButtons, R.id.layout_variaant, object :OnClickListener{
            override fun onClick(index: Int) {
                presenter.onClickVariant(index)
            }

        })
        loadButtons(variantButtons, R.id.layout_variaant1,object:OnClickListener{
            override fun onClick(index: Int) {
                presenter.onClickVariant(index)
            }

        })
        back_btn_bar.setOnClickListener { v: View? -> presenter.finishActiv() }
        btn_yordam.setOnClickListener { v: View? -> presenter.yordam() }
        btn_clear.setOnClickListener { v: View? -> presenter.clear() }
        backQueston_btn.setOnClickListener { v: View? -> presenter.btnBackQuestion() }
        nextQueston_btn.setOnClickListener { v: View? -> presenter.btnNextQuestion() }
        home_btn_bar.setOnClickListener { b: View? -> presenter.intentHome() }
    }


    private fun loadButtons(buttons: MutableList<Button>, groupId: Int, listener: OnClickListener) {
        val group = findViewById<ViewGroup>(groupId)
        val oldSize = buttons.size
        for (i in 0 until group.childCount) {
            val view = group.getChildAt(i)
            if (view is Button) {
                val button = view
                val index = oldSize + i
                button.setOnClickListener { v: View? ->
                    listener.onClick(
                        index
                    )
                }
                buttons.add(button)
            }
        }
    }

    override fun showCount(count: String) {
        counter.text = count
    }

    override fun showBall(ball: Int) {
        this.ball.text = ball.toString()
    }


    override fun loadImage(resId: Int) {
        imageQuestion.setImageResource(resId)
    }

    override fun hideAnswer(index: Int) {
        answerButtons[index].visibility = View.GONE
    }

    override fun hideAnswerGone(index: Int) {
        answerButtons[index].visibility = View.GONE
    }

    override fun showAnswer(index: Int) {
        answerButtons[index].visibility = View.VISIBLE
    }

    override fun clearAnswer(index: Int) {
        answerButtons[index].text = ""
    }


    override fun writeAnswer(index: Int, text: String) {
        answerButtons[index].text = text
        answerButtons[index].setTextColor(Color.WHITE)
    }

    override fun writeAnswerYordam(index: Int, text: String) {
        answerButtons[index].text = text
        answerButtons[index].setTextColor(Color.RED)
    }

    override fun writeVariant(index: Int, text: String) {
        variantButtons[index].text = text
    }

    override fun showVariant(index: Int) {
        variantButtons[index].visibility = View.VISIBLE
    }

    override fun hideVariant(index: Int) {
        variantButtons[index].visibility = View.INVISIBLE
    }

    override fun hideVariantGone(index: Int) {
        variantButtons[index].visibility = View.GONE
    }

    override fun hideLiner(name: String) {
        liner_clear.visibility = View.GONE
        win_tv.visibility = View.VISIBLE
        win_img.visibility = View.VISIBLE
        win_tv.text = name.uppercase(Locale.getDefault())
        win_img_achko.visibility = View.VISIBLE
        tekst_win.visibility = View.VISIBLE
    }

    override fun showLiner() {
        liner_clear.visibility = View.VISIBLE
        win_tv.visibility = View.GONE
        win_img.visibility = View.GONE
        win_img_achko.visibility = View.GONE
        tekst_win.visibility = View.GONE
    }

    override fun showToast() {
        Toast.makeText(this, "You don't have enough points", Toast.LENGTH_LONG).show()
    }

    override fun startAnim() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.scale)
        win_img.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                val animation1 = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fromscale)
                win_img.startAnimation(animation1)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    override fun startOnClickMusic() {
        mediaPlayer.start()
    }

    override fun startWinMusic() {
        mediaPlayer1.start()
    }

    override fun intentHome() {
        val intent = Intent(this, ThreadActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun finishActiv() {
        finish()
    }

    override fun showToastClicVariant() {
        Toast.makeText(this, "No space left for letters", Toast.LENGTH_SHORT).show()
    }

    override fun isShownVariant(index: Int): Boolean {
        return variantButtons[index].visibility == View.VISIBLE
    }

    internal interface OnClickListener {
        fun onClick(index: Int)
    }

}
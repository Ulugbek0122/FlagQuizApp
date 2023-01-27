package uz.gita.flagquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import uz.gita.flagquizapp.contract.ContractThread
import uz.gita.flagquizapp.threadimpl.ModelThread
import uz.gita.flagquizapp.threadimpl.PresenterThread

class ThreadActivity : AppCompatActivity(),ContractThread.View {

    private lateinit var play_btn: ImageView
    private lateinit var about_btn: TextView
    private lateinit var quit_btn: TextView
    private lateinit var info: ImageView
    private lateinit var presenter: ContractThread.Presenter
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)


        presenter = PresenterThread(this, ModelThread(this))

        loadViews()

        mediaPlayer = MediaPlayer.create(this, R.raw.music1)
        loadOnClick()

    }

    private fun loadOnClick() {
        play_btn.setOnClickListener { view: View? -> presenter.intentSecond() }
        about_btn.setOnClickListener { v: View? -> presenter.intentAbout() }
        quit_btn.setOnClickListener { view: View? -> presenter.finish() }
        info.setOnClickListener { v: View? -> presenter.info() }
    }

    private fun loadViews() {
        play_btn = findViewById(R.id.play)
        about_btn = findViewById(R.id.about)
        quit_btn = findViewById(R.id.quit)
        info = findViewById(R.id.statistic)
    }

    override fun intentAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    override fun intentSecond() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    override fun showCustomDialog(n: Int, k: Int) {
        val dialogInfo = DialogInfo(this, n.toDouble(), k.toDouble())
        dialogInfo.setOnClickListener {
            mediaPlayer.start()
            dialogInfo.dismiss()
        }
        dialogInfo.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogInfo.show()
    }


    override fun quit() {
        finish()
    }

    override fun startMusic() {
        mediaPlayer.start()
    }

}
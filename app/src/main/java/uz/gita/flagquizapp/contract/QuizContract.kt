package uz.gita.flagquizapp.contract

import uz.gita.flagquizapp.models.QuizData

interface QuizContract {

    companion object{
        val MAX_ANSWER = 18
        val VARIANT_COUNT = 16
    }


    interface Model {
        operator fun get(level: Int): QuizData?
        fun countQuestion(): Int

        fun writeShare(list: List<Int?>?)
        fun readShare(): List<Int?>?
        fun writeBall(ball: Int)
        fun readBall(): Int
    }

    interface View {
        fun showCount(count: String)
        fun showBall(ball: Int)
        fun loadViews()
        fun loadImage(resId: Int)
        fun hideAnswer(index: Int)
        fun hideAnswerGone(index: Int)
        fun showAnswer(index: Int)
        fun clearAnswer(index: Int)
        fun writeAnswer(index: Int, text: String)
        fun writeAnswerYordam(index: Int, text: String)
        fun writeVariant(index: Int, text: String)
        fun showVariant(index: Int)
        fun hideVariant(index: Int)
        fun hideVariantGone(index: Int)
        fun isShownVariant(index: Int): Boolean
        fun hideLiner(name: String)
        fun showLiner()
        fun showToast()
        fun startAnim()
        fun startOnClickMusic()
        fun startWinMusic()
        fun intentHome()
        fun finishActiv()
        fun showToastClicVariant()
    }


    interface Presenter {
        fun init()
        fun onClickAnswer(index: Int)
        fun onClickVariant(inde: Int)
        fun yordam()
        fun clear()
        fun btnNextQuestion()
        fun btnBackQuestion()
        fun initShare()
        fun writeAll()
        fun intentHome()
        fun finishActiv()
    }

}
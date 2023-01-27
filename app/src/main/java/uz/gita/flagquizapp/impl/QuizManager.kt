package uz.gita.flagquizapp.impl

import android.util.Log
import uz.gita.flagquizapp.contract.QuizContract
import uz.gita.flagquizapp.models.QuizData

class QuizManager(private  var model: QuizContract.Model,private var view: QuizContract.View?, private var id: Int) :QuizContract.Presenter {
    private var level = 0
    private var quizData: QuizData? = null
    private lateinit var userAnswer: Array<String?>
    private var ball = 10
    private var sizeVariant = 0
    private var helpAnswer: MutableList<Int>? = null

    private var winList: ArrayList<Int?>? = null

    init {
        level = id
        initShare()
    }



    private fun isOffMusic() {
        view!!.startOnClickMusic()
    }

    private fun isOffMusicWin() {
        view!!.startWinMusic()
    }

    override fun init() {
        var isWin = false
        view!!.showBall(ball)
        if (level < model.countQuestion() && level >= 0) {
            helpAnswer = ArrayList()
            quizData = model!![level]
            view!!.showCount((level + 1).toString() + "/" + model.countQuestion())
            view!!.loadImage(quizData!!.question)
            for (i in winList!!.indices) {
                Log.d("TTT", winList!![i].toString())
            }
            for (i in winList!!.indices) {
                if (level == winList!![i]) {
                    isWin = true
                }
            }
            if (!isWin) {
                val size: Int = quizData!!.answer.length
                for (i in 0 until QuizContract.MAX_ANSWER) {
                    if (i < size) {
                        view!!.showAnswer(i)
                        view!!.clearAnswer(i)
                    } else {
                        view!!.hideAnswer(i)
                    }
                }
                view!!.showLiner()
                sizeVariant = quizData!!.variant.length
                for (i in 0 until QuizContract.VARIANT_COUNT) {
                    view!!.showVariant(i)
                }
                for (i in 0 until sizeVariant) {
                    val text = getVariant(i)
                    view!!.writeVariant(i, text)
                    view!!.showVariant(i)
                }
                userAnswer = arrayOfNulls(quizData!!.answer.length)
            } else {
                for (i in 0 until QuizContract.VARIANT_COUNT) {
                    view!!.hideVariantGone(i)
                }
                for (i in 0 until QuizContract.MAX_ANSWER) {
                    view!!.hideAnswerGone(i)
                }
                view!!.hideLiner(quizData!!.answer)
            }
        }
    }

    private fun sowWinDialog() {
        for (i in 0 until QuizContract.VARIANT_COUNT) {
            view!!.hideVariantGone(i)
        }
        for (i in 0 until QuizContract.MAX_ANSWER) {
            view!!.hideAnswerGone(i)
        }
        view!!.hideLiner(quizData!!.answer)
        view!!.startAnim()
    }


    private fun getVariant(index: Int): String {
        val variant: String = quizData!!.variant
        return variant[index].toString()
    }

    private fun getAnswer(index: Int): String {
        val answer: String = quizData!!.answer
        return answer[index].toString()
    }

    override fun onClickAnswer(index: Int) {
        if (helpAnswer(index) && userAnswer[index] != null) {
            val text = userAnswer[index]
            for (i in 0 until sizeVariant) {
                val textVariant = getVariant(i)
                if (text == textVariant && !view!!.isShownVariant(i)) {
                    view!!.showVariant(i)
                    view!!.clearAnswer(index)
                    userAnswer[index] = null
                    return
                }
            }
        }
    }

    override fun onClickVariant(index: Int) {
        val answerIndex = findFirstEmptyIndex()
        if (answerIndex != -1) {
            view!!.hideVariant(index)
            val text = getVariant(index)
            view!!.writeAnswer(answerIndex, text)
            userAnswer[answerIndex] = text
            if (findFirstEmptyIndex() == -1 && getWin()) {
                next()
            }
        } else {
            view!!.showToastClicVariant()
        }
    }


    override fun clear() {
        val clearIndex = findFirstIsFullIndex()
        if (clearIndex != -1) {
            val text = userAnswer[clearIndex]
            for (i in 0 until sizeVariant) {
                val textVariant = getVariant(i)
                if (text == textVariant && !view!!.isShownVariant(i)) {
                    view!!.showVariant(i)
                    view!!.clearAnswer(clearIndex)
                    userAnswer[clearIndex] = null
                    return
                }
            }
        }
    }

    override fun btnNextQuestion() {
        isOffMusic()
        if (level < model.countQuestion() - 1) {
            level++
            init()
        }
    }

    override fun btnBackQuestion() {
        isOffMusic()
        if (level > 0) {
            level--
            init()
        }
    }

    override fun initShare() {
        winList = model!!.readShare() as ArrayList<Int?>?
        val i = model!!.readBall()
        if (i != -1) {
            ball = i
        }
    }

    override fun writeAll() {
        model!!.writeShare(winList)
        model!!.writeBall(ball)
    }

    override fun intentHome() {
        isOffMusic()
        view!!.intentHome()
    }

    override fun finishActiv() {
        isOffMusic()
        view!!.finishActiv()
    }

    override fun yordam() {
        if (ball > 0) {
            var wrongAnswer = -1
            for (i in userAnswer.indices) {
                if (userAnswer[i] == null) {
                    wrongAnswer = i
                    break
                } else {
                    if (userAnswer[i] != getAnswer(i)) {
                        wrongAnswer = i
                        break
                    }
                }
            }
            if (wrongAnswer != -1) {
                if (userAnswer[wrongAnswer] != null) {
                    putIn(wrongAnswer)
                }
                val n = thereIs(wrongAnswer)
                if (n != -1) {
                    view!!.hideVariant(n)
                    val variant = getVariant(n)
                    view!!.writeAnswerYordam(wrongAnswer, variant)
                    userAnswer[wrongAnswer] = variant
                    helpAnswer!!.add(wrongAnswer)
                    ball -= 1
                    view!!.showBall(ball)
                    if (findFirstEmptyIndex() == -1 && getWin()) {
                        next()
                    }
                } else {
                    val i = indexWrongAnswer(wrongAnswer)
                    if (i != -1) {
                        val s = userAnswer[i]
                        view!!.clearAnswer(i)
                        userAnswer[i] = null
                        view!!.writeAnswerYordam(wrongAnswer, s!!)
                        userAnswer[wrongAnswer] = s
                        helpAnswer!!.add(wrongAnswer)
                        ball -= 1
                        view!!.showBall(ball)
                        if (findFirstEmptyIndex() == -1 && getWin()) {
                            next()
                        }
                    }
                }
            }
        } else {
            view!!.showToast()
        }
    }


    private fun putIn(index: Int) {
        val s = userAnswer[index]
        for (i in 0 until sizeVariant) {
            val variant = getVariant(i)
            if (s == variant && !view!!.isShownVariant(i)) {
                view!!.showVariant(i)
                view!!.clearAnswer(index)
                userAnswer[index] = null
                return
            }
        }
    }

    //    private boolean isVisible(String str){
    //        for (int i = 0; i < sizeVariant; i++) {
    //            if (view.isShownVariant(i)) return true;
    //        }
    //        return false;
    //    }
    private fun indexWrongAnswer(index: Int): Int {
        val answer = getAnswer(index)
        for (i in userAnswer.indices) {
            if (userAnswer[i] == null) {
                continue
            }
            if (userAnswer[i] == answer && userAnswer[i] != getAnswer(i)) {
                return i
            }
        }
        return -1
    }

    private fun thereIs(index: Int): Int {
        val answer = getAnswer(index)
        for (i in 0 until sizeVariant) {
            if (answer == getVariant(i) && view!!.isShownVariant(i)) {
                return i
            }
        }
        return -1
    }


    private fun getWin(): Boolean {
        for (i in userAnswer.indices) {
            if (getAnswer(i) != userAnswer[i]) return false
        }
        return true
    }

    private operator fun next() {
        sowWinDialog()
        isOffMusicWin()
        winList!!.add(level)
        ball += 1
        view!!.showBall(ball)
    }


    private fun findFirstEmptyIndex(): Int {
        for (i in userAnswer.indices) {
            if (userAnswer[i] == null) {
                return i
            }
        }
        return -1
    }

    private fun findFirstIsFullIndex(): Int {
        for (i in userAnswer.indices.reversed()) {
            if (userAnswer[i] != null) {
                if (helpAnswer(i)) {
                    return i
                }
            }
        }
        return -1
    }

    private fun helpAnswer(index: Int): Boolean {
        for (j in helpAnswer!!.indices) {
            if (index == helpAnswer!![j]) return false
        }
        return true
    }
}
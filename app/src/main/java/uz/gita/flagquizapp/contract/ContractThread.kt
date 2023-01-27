package uz.gita.flagquizapp.contract

interface ContractThread {

    interface View {
        fun intentAbout()
        fun intentSecond()
        fun showCustomDialog(n: Int, k: Int)
        fun quit()
        fun startMusic()
    }

    interface Presenter {
        fun intentAbout()
        fun intentSecond()
        fun info()
        fun finish()
    }

    interface Model {
        fun readShared(): IntArray?
    }
}
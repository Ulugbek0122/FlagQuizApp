package uz.gita.flagquizapp.contract

interface SecondContract {
    interface ViewSecond {
        fun loadViews()
        fun showImage(index: Int, image: Int)
        fun intent(index: Int)
        fun startMusic()
    }

    interface PrecenterSecond {
        fun init()
        fun onClickImage(index: Int)
    }

    interface ModelSecond {


        fun getImage(): List<Int>
    }
}
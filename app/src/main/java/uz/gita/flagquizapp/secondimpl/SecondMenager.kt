package uz.gita.flagquizapp.secondimpl

import uz.gita.flagquizapp.contract.SecondContract

class SecondMenager(private var model:SecondContract.ModelSecond, private var view:SecondContract.ViewSecond):SecondContract.PrecenterSecond {

    private lateinit var images: List<Int>



    override fun init() {
        images = model.getImage()
        for (i in images!!.indices) {
            view.showImage(i, images!![i])
        }
    }

    override fun onClickImage(index: Int) {
        view.startMusic()
        view.intent(index)
    }
}
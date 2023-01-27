package uz.gita.flagquizapp.threadimpl

import uz.gita.flagquizapp.contract.ContractThread

class PresenterThread(private var view: ContractThread.View,private var model: ContractThread.Model):ContractThread.Presenter {



    override fun intentAbout() {
        playMusic()
        view!!.intentAbout()
    }

    override fun intentSecond() {
        playMusic()
        view!!.intentSecond()
    }

    override fun info() {
        playMusic()
        val i = model!!.readShared()
        view!!.showCustomDialog(i!![0], i[1])
    }

    override fun finish() {
        playMusic()
        view!!.quit()
    }


    private fun playMusic() {
        view!!.startMusic()
    }
}
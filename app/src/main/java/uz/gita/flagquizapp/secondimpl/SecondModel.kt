package uz.gita.flagquizapp.secondimpl

import uz.gita.flagquizapp.R
import uz.gita.flagquizapp.contract.SecondContract

class SecondModel:SecondContract.ModelSecond {
    private val list: MutableList<Int> = ArrayList()

    init {
        loadData()
    }

    private fun loadData() {
        list.add(R.drawable.uzbekistan)
        list.add(R.drawable.qozogiston)
        list.add(R.drawable.tojikistion)
        list.add(R.drawable.india_flag)
        list.add(R.drawable.korea)
        list.add(R.drawable.germaniya)
        list.add(R.drawable.spain)
        list.add(R.drawable.fransiya)
        list.add(R.drawable.italiya)
        list.add(R.drawable.italiya)
        list.add(R.drawable.argentina)
        list.add(R.drawable.brazilya)
        list.add(R.drawable.urugvay)
        list.add(R.drawable.kolimbiya)
        list.add(R.drawable.peru)
    }

    override fun getImage(): List<Int> {
        return list
    }


}
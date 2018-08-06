package expertosbd.tpinter.tpintermodalbodega.ui.entrada

import expertosbd.tpinter.tpintermodalbodega.model.Evento
import expertosbd.tpinter.tpintermodalbodega.ui.base.BaseContract

class EntradaContract {

    interface View : BaseContract.View {
        fun showProgress(show: Boolean)
        fun onFetchDataSuccess(items: MutableList<Evento>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadData()
    }
}
package expertosbd.tpinter.tpintermodalbodega.ui.salida

import expertosbd.tpinter.tpintermodalbodega.model.Evento
import expertosbd.tpinter.tpintermodalbodega.ui.base.BaseContract

class SalidaContract {

    interface View : BaseContract.View {
        fun showProgress(show: Boolean)
        fun setSalidaItems(items: MutableList<Evento>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getSalidaItems()
    }
}
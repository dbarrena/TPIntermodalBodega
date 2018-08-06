package expertosbd.tpinter.tpintermodalbodega.ui.scanner

import expertosbd.tpinter.tpintermodalbodega.model.EstatusDetalleEvento
import expertosbd.tpinter.tpintermodalbodega.model.Evento
import expertosbd.tpinter.tpintermodalbodega.model.Paquete
import expertosbd.tpinter.tpintermodalbodega.ui.base.BaseContract

class ScannerContract {

    interface View : BaseContract.View {
        fun showProgress(show: Boolean)
        fun setPaquetesPendienteItems(items: MutableList<Paquete>)
        fun setEstatusDetalleEvento(items: MutableList<EstatusDetalleEvento>)
        fun onPostSuccessful(message: String, paqueteID: Int)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getPaquetesPendienteItems(entradaID: Int)
        fun getEstatusDetalleEvento()
        fun postScannerResult(result: Paquete)
    }

}
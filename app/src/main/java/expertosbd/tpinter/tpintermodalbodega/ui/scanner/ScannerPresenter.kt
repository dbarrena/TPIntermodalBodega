package expertosbd.tpinter.tpintermodalbodega.ui.scanner

import android.util.Log
import expertosbd.tpinter.tpintermodalbodega.api.ApiServiceInterface
import expertosbd.tpinter.tpintermodalbodega.model.*
import expertosbd.tpinter.tpintermodalbodega.prefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

class ScannerPresenter : ScannerContract.Presenter {

    private val api: ApiServiceInterface = ApiServiceInterface.create()
    private val subscriptions = CompositeDisposable()
    private lateinit var view: ScannerContract.View

    override fun getPaquetesPendienteItems(entradaID: Int) {
        val items = mutableListOf<Paquete>()

        val observable = Observable
                .merge(api.paquetesCompletado(entradaID), api.paquetesPendiente(entradaID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            it.items.forEach {
                                items.add(it)
                            }
                        },
                        {
                            view.showErrorMessage(it.localizedMessage)
                        },
                        {
                            view.setPaquetesPendienteItems(items)
                        }
                )

        /*val subscribe = api
                .paquetesPendiente(entradaID)
                .mergeWith(api.paquetesCompletado(entradaID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { item: ApexApiResponse<Paquete> ->
                            view.setPaquetesPendienteItems(item.items as MutableList<Paquete>)
                        },
                        { error ->
                            view.showErrorMessage(error.localizedMessage)
                        }
                )*/

        subscriptions.add(observable)
    }

    override fun getEstatusDetalleEvento() {
        val subscribe = api
                .estatusDetalleEvento()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { item: ApexApiResponse<EstatusDetalleEvento> ->
                            if (item.items.isEmpty()) {
                                view.showErrorMessage("Error al cargar LOV estatus detalle evento")
                            } else {
                                view.setEstatusDetalleEvento(
                                        item.items as MutableList<EstatusDetalleEvento>)
                            }
                        },
                        { error ->
                            view.showErrorMessage(error.localizedMessage)
                        }
                )

        subscriptions.add(subscribe)
    }

    override fun postScannerResult(result: Paquete) {
        view.showProgress(true)
        val format = SimpleDateFormat("dd/MM/yyyy,HH:mm")
        val date = format.format(Calendar.getInstance().time)

        Log.d("ultima", result.toString())
        val subscribe = api
                .updatePaquete(
                        UpdatePaquete(date, result.detalleEventoID, result.estatusDetalleEventoID,
                                prefs.user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { item: UpdateResponse ->
                            view.onPostSuccessful(item.message, result.detalleEventoID)
                        },
                        { error ->
                            view.showErrorMessage(error.localizedMessage)
                        }
                )

        subscriptions.add(subscribe)
    }


    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: ScannerContract.View) {
        this.view = view
    }

    override fun subscribe() {
    }
}
package expertosbd.tpinter.tpintermodalbodega.ui.salida

import android.util.Log
import expertosbd.tpinter.tpintermodalbodega.api.ApiServiceInterface
import expertosbd.tpinter.tpintermodalbodega.model.ApexApiResponse
import expertosbd.tpinter.tpintermodalbodega.model.Evento
import expertosbd.tpinter.tpintermodalbodega.ui.salida.SalidaContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SalidaPresenter : SalidaContract.Presenter {
    private val api: ApiServiceInterface = ApiServiceInterface.create()
    private val subscriptions = CompositeDisposable()
    private lateinit var view: SalidaContract.View

    override fun getSalidaItems() {
        val subscribe = api
                .salida()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { item: ApexApiResponse<Evento> ->
                            if (item.items.isEmpty()) {
                                view.showErrorMessage("No hay registros abiertos")
                            } else {
                                view.setSalidaItems(item.items as MutableList<Evento>)
                            }
                        },
                        { error ->
                            view.showErrorMessage(error.localizedMessage)
                        }
                )

        subscriptions.add(subscribe)
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: SalidaContract.View) {
        this.view = view
    }
}
package expertosbd.tpinter.tpintermodalbodega.ui.entrada

import expertosbd.tpinter.tpintermodalbodega.api.ApiServiceInterface
import expertosbd.tpinter.tpintermodalbodega.model.ApexApiResponse
import expertosbd.tpinter.tpintermodalbodega.model.Evento
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EntradaPresenter : EntradaContract.Presenter {
    private val api: ApiServiceInterface = ApiServiceInterface.create()
    private val subscriptions = CompositeDisposable()
    private lateinit var view: EntradaContract.View

    override fun loadData() {
        val subscribe = api
                .entrada()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { item: ApexApiResponse<Evento> ->
                            if (item.items.isEmpty()) {
                                view.showErrorMessage("No hay registros abiertos")
                            } else {
                                view.onFetchDataSuccess(item.items as MutableList<Evento>)
                            }
                        },
                        { error ->
                            view.showErrorMessage(error.localizedMessage)
                        }
                )

        subscriptions.add(subscribe)
    }

    override fun subscribe() {
        loadData()
    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: EntradaContract.View) {
        this.view = view
    }

}
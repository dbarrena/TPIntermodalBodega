package expertosbd.tpinter.tpintermodalbodega.ui.main

class MainPresenter: MainContract.Presenter{

    private lateinit var view: MainContract.View

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun attach(view: MainContract.View) {
        this.view = view
    }

}
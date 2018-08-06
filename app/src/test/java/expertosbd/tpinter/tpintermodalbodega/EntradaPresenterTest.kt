package expertosbd.tpinter.tpintermodalbodega

import expertosbd.tpinter.tpintermodalbodega.model.Evento
import expertosbd.tpinter.tpintermodalbodega.ui.entrada.EntradaContract
import expertosbd.tpinter.tpintermodalbodega.ui.entrada.EntradaFragment
import expertosbd.tpinter.tpintermodalbodega.ui.entrada.EntradaPresenter
import org.junit.Before

class EntradaPresenterTest {

    private val view = TestEntradaView()
    private val presenter: EntradaPresenter = EntradaPresenter()

    @Before
    fun setUp() {
        view.reset()
        presenter.attach(view)
    }

    class TestEntradaView : EntradaContract.View {

        var fetchDataCounter = 0
        var fetchDataArgs: MutableList<MutableList<Evento>> = mutableListOf()
        var showErrorCounter = 0
        var showErrorArgs: MutableList<String> = mutableListOf()
        var showProgressCounter = 0

        fun reset() {
            fetchDataCounter = 0
            fetchDataArgs = mutableListOf()
            showErrorCounter = 0
            showErrorArgs = mutableListOf()
            showProgressCounter = 0
        }


        override fun onFetchDataSuccess(items: MutableList<Evento>) {
            fetchDataArgs.add(items)
            fetchDataCounter++
        }

        override fun showErrorMessage(error: String) {
            showErrorArgs.add(error)
            showErrorCounter++
        }

        override fun showProgress(show: Boolean) {
            showProgressCounter++
        }

    }

}
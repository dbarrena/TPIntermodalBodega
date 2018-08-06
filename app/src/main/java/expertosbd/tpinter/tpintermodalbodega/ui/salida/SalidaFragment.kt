package expertosbd.tpinter.tpintermodalbodega.ui.salida


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import expertosbd.tpinter.tpintermodalbodega.R
import expertosbd.tpinter.tpintermodalbodega.di.component.DaggerFragmentComponent
import expertosbd.tpinter.tpintermodalbodega.di.module.FragmentModule
import expertosbd.tpinter.tpintermodalbodega.model.Evento
import expertosbd.tpinter.tpintermodalbodega.ui.main.MainActivity
import expertosbd.tpinter.tpintermodalbodega.ui.scanner.ScannerActivity
import expertosbd.tpinter.tpintermodalbodega.utils.withColor
import kotlinx.android.synthetic.main.fragment_salida.*
import javax.inject.Inject

class SalidaFragment : Fragment(), SalidaContract.View, SearchView.OnQueryTextListener,
        SalidaAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Inject
    lateinit var presenter: SalidaContract.Presenter
    private lateinit var activity: MainActivity
    lateinit var adapter: SalidaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        activity = getActivity() as MainActivity
        activity.setActionBarTitle("Salidas")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_salida, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showProgress(true)
        presenter.attach(this)
        presenter.subscribe()
        presenter.getSalidaItems()
        swipe_container.setOnRefreshListener(this)
        setSearchView(search_view_salida)
    }

    override fun setSalidaItems(items: MutableList<Evento>) {
        adapter = SalidaAdapter(activity, items, this)
        recycler_view_salida.adapter = adapter
        recycler_view_salida.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        showProgress(false)
    }

    override fun showErrorMessage(error: String) {
        showProgress(false)
        activity.showMessage(error)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onItemClicked(item: Evento) {
        val intent = Intent(activity, ScannerActivity::class.java)
        intent.putExtra("evento", item)
        startActivity(intent)
    }

    override fun onRefresh() {
        presenter.getSalidaItems()
    }

    override fun showProgress(show: Boolean) {
        val progressBar = activity.findViewById<FrameLayout>(R.id.progress_bar_main)
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE

            if (swipe_container.isRefreshing) {
                swipe_container.isRefreshing = false
            }
        }
    }

    private fun setSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(this)
        searchView.isSubmitButtonEnabled = true
        searchView.queryHint = "Busqueda"
        searchView.isFocusable = false
        searchView.isIconified = false
        searchView.clearFocus()
    }

    private fun injectDependency() {
        val fragmentComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule(this))
                .build()

        fragmentComponent.inject(this)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SalidaFragment()
    }
}

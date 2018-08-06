package expertosbd.tpinter.tpintermodalbodega.ui.entrada


import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_entrada.*
import javax.inject.Inject

class EntradaFragment : Fragment(), EntradaContract.View, SearchView.OnQueryTextListener,
        EntradaAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Inject
    lateinit var presenter: EntradaContract.Presenter
    private lateinit var activity: MainActivity
    private lateinit var adapter: EntradaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        activity = getActivity() as MainActivity
        activity.setActionBarTitle("Entradas")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_entrada, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showProgress(true)
        presenter.attach(this)
        presenter.subscribe()
        swipe_container.setOnRefreshListener(this)
        setSearchView(search_view_entrada)
    }

    override fun onFetchDataSuccess(items: MutableList<Evento>) {
        adapter = EntradaAdapter(activity, items, this)
        recycler_view_entrada.adapter = adapter
        recycler_view_entrada.layoutManager =
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
        adapter?.let { it.filter.filter(query) }
        return false
    }

    override fun onItemClicked(item: Evento) {
        val intent = Intent(activity, ScannerActivity::class.java)
        intent.putExtra("evento", item)
        startActivity(intent)
    }

    override fun onRefresh() {
        presenter.loadData()
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
        fun newInstance() = EntradaFragment()
    }
}

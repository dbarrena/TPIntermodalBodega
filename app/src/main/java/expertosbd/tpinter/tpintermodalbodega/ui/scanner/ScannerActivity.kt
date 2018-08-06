package expertosbd.tpinter.tpintermodalbodega.ui.scanner

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import expertosbd.tpinter.tpintermodalbodega.R
import expertosbd.tpinter.tpintermodalbodega.di.component.DaggerActivityComponent
import expertosbd.tpinter.tpintermodalbodega.di.module.ActivityModule
import expertosbd.tpinter.tpintermodalbodega.model.Evento
import expertosbd.tpinter.tpintermodalbodega.model.Paquete
import expertosbd.tpinter.tpintermodalbodega.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_scanner.*
import kotlinx.android.synthetic.main.dialog_scanner.*
import java.util.*
import javax.inject.Inject
import android.widget.Spinner
import android.widget.TextView
import expertosbd.tpinter.tpintermodalbodega.model.EstatusDetalleEvento


class ScannerActivity : BaseActivity(), ScannerCameraFragment.OnScannedListener,
        ScannerContract.View, ScannerPendingAdapter.OnItemClickListener {
    @Inject
    lateinit var presenter: ScannerContract.Presenter

    lateinit var pendingAdapter: ScannerPendingAdapter
    lateinit var completedAdapter: ScannerCompletedAdapter
    private val paquetesMap: HashMap<Int, Paquete> = HashMap()
    lateinit var dialogSpinnerItems: MutableList<EstatusDetalleEvento>
    //private lateinit var scannerCameraFragment: ScannerCameraFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        injectDependency()
        presenter.attach(this)
        rootLayoutID = R.id.scanner_root
        setSupportActionBar(toolbar_scanner)
        supportActionBar?.title = "Detalle Evento"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val evento = intent.getSerializableExtra("evento") as Evento
        presenter.getPaquetesPendienteItems(evento.eventoID)
        presenter.getEstatusDetalleEvento()

        pending_items_button.setOnClickListener { expandable_layout_pending.toggle() }
        completed_items_button.setOnClickListener { expandable_layout_completed.toggle() }

        //report.isNestedScrollingEnabled = false
    }

    override fun onResume() {
        super.onResume()
        /*scannerCameraFragment = supportFragmentManager.findFragmentById(
                R.id.scanner_fragment) as ScannerCameraFragment*/
    }

    override fun onScanned(barcode: Int) {
        val paquete = paquetesMap[barcode]
        val paqueteCompleted: Paquete? = completedAdapter.findPaqueteByID(barcode)

        try {
            val tg = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
            tg.startTone(ToneGenerator.TONE_PROP_BEEP)

            paqueteCompleted?.let { showErrorMessage("Este paquete ya esta registrado") } ?: run {

                paquete?.let {
                    showScannerDialog(it)
                } ?: showErrorMessage("No se encuentra el codigo de barras: $barcode")
            }

        } catch (e: Exception) {
            showErrorMessage(e.localizedMessage)
        }

    }

    override fun showProgress(show: Boolean) {
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar_scanner)
        if (show) {
            progressBar.visibility = View.VISIBLE
            report.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            report.visibility = View.VISIBLE
            pending_items_text.text = "Pallets pendientes: ${pendingAdapter.itemCount}"
            completed_items_text.text = "Pallets registrados: ${completedAdapter.itemCount}"
        }
    }

    override fun setPaquetesPendienteItems(items: MutableList<Paquete>) {
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)

        val layoutManagerPending = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val pendingItems = mutableListOf<Paquete>()

        val layoutManagerCompleted = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val completedItems = mutableListOf<Paquete>()

        items.forEach {
            paquetesMap[it.detalleEventoID] = it
            if(it.estatusDetalleEventoID == 0){
                pendingItems.add(it)
            } else {
                completedItems.add(it)
            }
        }

        pendingAdapter = ScannerPendingAdapter(this, pendingItems, this)
        scanner_items_pending.apply {
            adapter = pendingAdapter
            layoutManager = layoutManagerPending
            addItemDecoration(dividerItemDecoration)
            isNestedScrollingEnabled = false
        }

        completedAdapter = ScannerCompletedAdapter(this, completedItems, this)
        scanner_items_completed.apply {
            adapter = completedAdapter
            layoutManager = layoutManagerCompleted
            addItemDecoration(dividerItemDecoration)
            isNestedScrollingEnabled = false
        }

        showProgress(false)
    }

    override fun setEstatusDetalleEvento(items: MutableList<EstatusDetalleEvento>) {
        dialogSpinnerItems = items
    }

    override fun showErrorMessage(error: String) {
        showMessage(error)
        showProgress(false)
        Log.e("Error", error)
        //scannerCameraFragment.resumeCamera()
    }

    private fun showScannerDialog(result: Paquete) {
        val dialog = MaterialDialog.Builder(this)
                .title(result.producto)
                .customView(R.layout.dialog_scanner, false)
                .positiveText(R.string.positive)
                .negativeText(R.string.negative)
                .onAny { dialog, which ->
                    when (which) {
                        DialogAction.POSITIVE -> {
                            val spinner = dialog.findViewById(R.id.estatus_spinner) as Spinner
                            val spinnerSelectedItem =
                                    spinner.selectedItem as EstatusDetalleEvento
                            result.estatusDetalleEventoID =
                                    spinnerSelectedItem.estatusDetalleEventoID
                            result.estatus = spinnerSelectedItem.estatusDetalleEvento
                            presenter.postScannerResult(result)
                        }
                        DialogAction.NEGATIVE -> {
                            dialog.dismiss()
                            //scannerCameraFragment.resumeCamera()
                        }
                    }
                }
                .show()

        configureDialog(dialog, result)
    }

    private fun configureDialog(dialog: MaterialDialog, result: Paquete) {
        val spinner = dialog.findViewById(R.id.estatus_spinner) as Spinner
        val lote = dialog.findViewById(R.id.lote) as TextView

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dialogSpinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        lote.text = result.lote
    }

    override fun onPostSuccessful(message: String, paqueteID: Int) {
        showMessage(message)
        //scannerCameraFragment.resumeCamera()
        updateAdapters(paqueteID)
        showProgress(false)
    }

    private fun updateAdapters(paqueteID: Int) {
        pendingAdapter.findPaqueteByID(paqueteID)?.let {
            completedAdapter.addPaquete(it)
            pendingAdapter.removePaqueteByID(paqueteID)

            completedAdapter.notifyDataSetChanged()
            pendingAdapter.notifyDataSetChanged()

            pending_items_text.text = "Paquetes pendientes: ${pendingAdapter.itemCount}"
            completed_items_text.text = "Paquetes registrados: ${completedAdapter.itemCount}"
        }
    }

    override fun onItemClicked(paquete: Paquete) {
        showScannerDialog(paquete)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }
}

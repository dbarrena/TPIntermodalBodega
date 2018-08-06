package expertosbd.tpinter.tpintermodalbodega.ui.scanner

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerCameraFragment : Fragment(), ZXingScannerView.ResultHandler {

    private var listener: OnScannedListener? = null
    lateinit var scannerView: ZXingScannerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        scannerView = ZXingScannerView(activity)
        return scannerView
    }

    override fun handleResult(result: Result?) {
        listener?.onScanned(result?.text as Int)
    }

    fun resumeCamera(){
        scannerView.resumeCameraPreview(this)
    }

    interface OnScannedListener {
        fun onScanned(barcode: Int)
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnScannedListener) {
            listener = context
        } else {
            throw RuntimeException(
                    context.toString() + " must implement OnScannedListener")
        }
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ScannerCameraFragment()
    }
}

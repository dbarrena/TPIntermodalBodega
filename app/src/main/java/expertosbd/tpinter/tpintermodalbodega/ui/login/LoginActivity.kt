package expertosbd.tpinter.tpintermodalbodega.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.github.javiersantos.materialstyleddialogs.enums.Style
import expertosbd.tpinter.tpintermodalbodega.R
import expertosbd.tpinter.tpintermodalbodega.di.component.DaggerActivityComponent
import expertosbd.tpinter.tpintermodalbodega.di.module.ActivityModule
import expertosbd.tpinter.tpintermodalbodega.model.Login
import expertosbd.tpinter.tpintermodalbodega.prefs
import expertosbd.tpinter.tpintermodalbodega.ui.base.BaseActivity
import expertosbd.tpinter.tpintermodalbodega.ui.main.MainActivity
import expertosbd.tpinter.tpintermodalbodega.utils.withColor
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginContract.View,
        BaseActivity.OnConnectionAvailableListener {

    @Inject
    lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        rootLayoutID = R.id.main_root_login
        injectDependency()
        onConnectedListener = this

        login_button.setOnClickListener {
            if (isConnected) {
                showProgress(true)
                presenter.checkLogin(user_field.text.toString().toUpperCase(),
                        password_field.text.toString())
            }
        }

        presenter.attach(this)
    }

    override fun loginResult(result: Boolean, login: Login?) {
        showProgress(false)
        if (result) {
            prefs.user = user_field.text.toString()
            prefs.firstName = login!!.nombre
            prefs.lastName = login.apellidoPaterno
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Snackbar.make(
                    main_root_login,
                    "Contraseña no válida",
                    Snackbar.LENGTH_SHORT
            ).withColor(resources.getColor(R.color.colorAccent)).show()
        }
    }


    override fun showErrorMessage(error: String) {
        showProgress(false)
        Snackbar.make(
                main_root_login,
                error,
                Snackbar.LENGTH_LONG
        ).withColor(resources.getColor(R.color.colorAccent)).show()
    }

    override fun showVersion(localVersion: String, currentVersion: String) {
        if (localVersion == currentVersion) {
            version_number.text = "Versión: $localVersion"
        } else {
            MaterialStyledDialog.Builder(this)
                    .setTitle("Versión Incompatible")
                    .setDescription(
                            "Versión local: $localVersion \nVersión actual: $currentVersion " +
                                    "\n\nPor favor actualiza la aplicacion.\n\n")
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setHeaderColor(R.color.colorAccent)
                    .setIcon(ContextCompat.getDrawable(this, R.drawable.alert_icon)!!)
                    .setCancelable(false)
                    .withDialogAnimation(false) //maybe
                    .show()
        }
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progress_bar_login.visibility = View.VISIBLE
        } else {
            progress_bar_login.visibility = View.GONE
        }
    }

    override fun onConnectionAvailable() {
        if (version_number.text.isEmpty()) {
            presenter.checkCurrentVersion()
        }
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }
}

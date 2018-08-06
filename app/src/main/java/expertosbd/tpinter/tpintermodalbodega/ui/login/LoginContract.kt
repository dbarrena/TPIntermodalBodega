package expertosbd.tpinter.tpintermodalbodega.ui.login

import expertosbd.tpinter.tpintermodalbodega.model.Login
import expertosbd.tpinter.tpintermodalbodega.ui.base.BaseContract

class LoginContract {

    interface View : BaseContract.View {
        fun showProgress(show: Boolean)
        fun loginResult(result: Boolean, login: Login?)
        fun showVersion(localVersion: String, currentVersion: String)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun checkLogin(user: String, password: String)
        fun checkCurrentVersion()
    }
}
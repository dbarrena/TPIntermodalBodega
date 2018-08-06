package expertosbd.tpinter.tpintermodalbodega.di.component

import dagger.Component
import expertosbd.tpinter.tpintermodalbodega.di.module.ActivityModule
import expertosbd.tpinter.tpintermodalbodega.ui.login.LoginActivity
import expertosbd.tpinter.tpintermodalbodega.ui.main.MainActivity
import expertosbd.tpinter.tpintermodalbodega.ui.scanner.ScannerActivity

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(loginActivity: LoginActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(scannerActivity: ScannerActivity)
}
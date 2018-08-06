package expertosbd.tpinter.tpintermodalbodega.di.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import expertosbd.tpinter.tpintermodalbodega.api.ApiServiceInterface
import expertosbd.tpinter.tpintermodalbodega.ui.login.LoginContract
import expertosbd.tpinter.tpintermodalbodega.ui.login.LoginPresenter
import expertosbd.tpinter.tpintermodalbodega.ui.main.MainContract
import expertosbd.tpinter.tpintermodalbodega.ui.main.MainPresenter
import expertosbd.tpinter.tpintermodalbodega.ui.scanner.ScannerContract
import expertosbd.tpinter.tpintermodalbodega.ui.scanner.ScannerPresenter

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun provideLoginPresenter(): LoginContract.Presenter {
        return LoginPresenter()
    }

    @Provides
    fun provideMainPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    fun provideScannerPresenter(): ScannerContract.Presenter {
    return ScannerPresenter()
    }

    @Provides
    fun provideApiService(): ApiServiceInterface {
        return ApiServiceInterface.create()
    }
}
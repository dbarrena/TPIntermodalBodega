package expertosbd.tpinter.tpintermodalbodega.di.module

import android.support.v4.app.Fragment
import dagger.Module
import dagger.Provides
import expertosbd.tpinter.tpintermodalbodega.api.ApiServiceInterface
import expertosbd.tpinter.tpintermodalbodega.ui.entrada.EntradaContract
import expertosbd.tpinter.tpintermodalbodega.ui.entrada.EntradaPresenter
import expertosbd.tpinter.tpintermodalbodega.ui.salida.SalidaContract
import expertosbd.tpinter.tpintermodalbodega.ui.salida.SalidaPresenter

@Module
class FragmentModule(private var fragment: Fragment) {

    @Provides
    fun provideFragment(): Fragment {
        return fragment
    }

    @Provides
    fun provideEntradaPresenter(): EntradaContract.Presenter {
        return EntradaPresenter()
    }

    @Provides
    fun provideSalidaPresenter(): SalidaContract.Presenter {
        return SalidaPresenter()
    }

    @Provides
    fun provideApiService(): ApiServiceInterface {
        return ApiServiceInterface.create()
    }
}
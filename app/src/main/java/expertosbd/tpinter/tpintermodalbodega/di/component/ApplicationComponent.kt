package expertosbd.tpinter.tpintermodalbodega.di.component

import dagger.Component
import expertosbd.tpinter.tpintermodalbodega.BaseApp
import expertosbd.tpinter.tpintermodalbodega.di.module.ApplicationModule

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: BaseApp)
}
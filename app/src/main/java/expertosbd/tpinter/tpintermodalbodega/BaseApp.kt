package expertosbd.tpinter.tpintermodalbodega

import android.app.Application
import expertosbd.tpinter.tpintermodalbodega.di.component.ApplicationComponent
import expertosbd.tpinter.tpintermodalbodega.di.component.DaggerApplicationComponent
import expertosbd.tpinter.tpintermodalbodega.di.module.ApplicationModule
import expertosbd.tpinter.tpintermodalbodega.utils.Prefs
import expertosbd.tpinter.tpintermodalbodega.utils.Utils

val prefs: Prefs by lazy {
    BaseApp.prefs!!
}

val utils: Utils by lazy {
    BaseApp.utils!!
}

class BaseApp: Application() {

    lateinit var component: ApplicationComponent

    companion object {
        lateinit var instance: BaseApp private set
        var prefs: Prefs? = null
        var utils: Utils? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = Prefs(applicationContext)
        utils = Utils()
        setup()
        if(BuildConfig.DEBUG) {

        }
    }

    fun setup() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }
}
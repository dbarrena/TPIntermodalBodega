package expertosbd.tpinter.tpintermodalbodega.di.component

import dagger.Component
import expertosbd.tpinter.tpintermodalbodega.di.module.FragmentModule
import expertosbd.tpinter.tpintermodalbodega.ui.entrada.EntradaFragment
import expertosbd.tpinter.tpintermodalbodega.ui.salida.SalidaFragment
import expertosbd.tpinter.tpintermodalbodega.ui.scanner.ScannerCameraFragment

@Component(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(fragment: EntradaFragment)

    fun inject(fragment: SalidaFragment)

    fun inject(fragment: ScannerCameraFragment)

}
package expertosbd.tpinter.tpintermodalbodega.utils

import android.app.Application
import android.content.Context
import expertosbd.tpinter.tpintermodalbodega.BaseApp
import expertosbd.tpinter.tpintermodalbodega.R

const val STRING_UNKNOWN_ERROR = "STRING_UNKNOWN_ERROR"

/**
 * Returns a mocked Context for the unit tests.
 * @return a mocked Context for the unit tests
 */
/*fun getTestContext(): Context {
    val application: Application = Mockito.mock(BaseApp::class.java)
    Mockito.`when`(application.getString(R.string.unknown_error)).thenReturn(STRING_UNKNOWN_ERROR)
    Mockito.`when`(application.applicationContext).thenReturn(application)
    return application
}*/
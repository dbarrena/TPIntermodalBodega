package expertosbd.tpinter.tpintermodalbodega.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import expertosbd.tpinter.tpintermodalbodega.R
import expertosbd.tpinter.tpintermodalbodega.di.component.DaggerActivityComponent
import expertosbd.tpinter.tpintermodalbodega.di.module.ActivityModule
import expertosbd.tpinter.tpintermodalbodega.prefs
import expertosbd.tpinter.tpintermodalbodega.ui.base.BaseActivity
import expertosbd.tpinter.tpintermodalbodega.ui.entrada.EntradaFragment
import expertosbd.tpinter.tpintermodalbodega.ui.salida.SalidaFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootLayoutID = R.id.main_root
        setSupportActionBar(toolbar_main)
        injectDependency()
        setDrawer()
        welcome_text.text = "Bienvenido ${prefs.firstName.toLowerCase().capitalize()}"
        presenter.attach(this)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun setDrawer() {
        val toggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar_main,
                R.string.open_drawer, R.string.close_drawer) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.isDrawerIndicatorEnabled = true

        nav_view.setNavigationItemSelectedListener {
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.entrada -> fragment = EntradaFragment.newInstance()
                R.id.salida -> fragment = SalidaFragment.newInstance()
            }
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_content, fragment, fragment?.tag)
                    .commit()
            drawer_layout.closeDrawer(GravityCompat.START)
            welcome.visibility = View.GONE
            true
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun showErrorMessage(error: String) {
        showMessage(error)
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }
}

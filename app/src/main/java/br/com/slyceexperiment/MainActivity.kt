package br.com.slyceexperiment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import it.slyce.sdk.*
import it.slyce.sdk.SlyceUI.ActivityLauncher
import it.slyce.sdk.exception.initialization.SlyceMissingGDPRComplianceException
import it.slyce.sdk.exception.initialization.SlyceNotOpenedException

class MainActivity : AppCompatActivity() {
    private val SLYCE_ACCOUNT_ID = ""
    private val SLYCE_API_KEY = ""
    private val SLYCE_SPACE_ID = ""

    private var slyce: Slyce? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            launchWithCustomSearchDetail()
        }

        slyce = Slyce.getInstance(this)

        slyce?.open(SLYCE_ACCOUNT_ID, SLYCE_API_KEY, SLYCE_SPACE_ID,
            SlyceCompletionHandler { slyceError ->
                if (slyceError != null) {
                    Toast.makeText(
                        this@MainActivity,
                        "Error opening Slyce: " + slyceError.details,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@SlyceCompletionHandler
                }
                var session: SlyceSession? = null
                try {
                    session = Slyce.getInstance(baseContext).createSession()
                } catch (e: SlyceMissingGDPRComplianceException) {
                    e.printStackTrace()
                } catch (e: SlyceNotOpenedException) {
                    e.printStackTrace()
                }
                if (session != null) {
                    val searchParams = SlyceSearchParameters()

                    session.defaultSearchParameters = searchParams

                    launchWithCustomSearchDetail()
                }
            })
    }

    private fun launchWithCustomSearchDetail() {
        try {
            slyce?.let {
                ActivityLauncher(it, SlyceActivityMode.PICKER)
                    .customClassName(CustomSearchDetailActivity::class.java.name)
                    .launch(this)
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: SlyceNotOpenedException) {
            e.printStackTrace()
        }
    }
}
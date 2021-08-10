package br.com.slyceexperiment

import it.slyce.sdk.SlyceCustomActivity
import it.slyce.sdk.SlyceItemDescriptor
import it.slyce.sdk.SlyceSession

class CustomSearchDetailActivity : SlyceCustomActivity() {

    override fun onStop() {
        super.onStop()

        // Uncomment these lines to stop the leak
        //if (isFinishing) {
        //    session?.invalidate()
        //}
    }

}
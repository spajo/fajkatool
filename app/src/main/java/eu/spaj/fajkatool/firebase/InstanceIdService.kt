package eu.spaj.fajkatool.firebase

import android.util.Log
import eu.spaj.fajkatool.R
import eu.spaj.fajkatool.helpers.SharedPreferencesHelper

/**
 * elo Rafał on 30/07/2017.
 */
class InstanceIdService : FirebaseInstanceIdService(), AnkoLogger {
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d("beniz", refreshedToken)

        SharedPreferencesHelper.saveProp(this, getString(R.string.fcm_token), refreshedToken)
        sendRegistrationToServer(refreshedToken)
    }

    private fun sendRegistrationToServer(refreshedToken: String?) {
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("users")
                    .child(FirebaseAuth.getInstance().currentUser?.uid)
                    .child("firebaseToken")
                    .setValue(refreshedToken)
        }
    }
}

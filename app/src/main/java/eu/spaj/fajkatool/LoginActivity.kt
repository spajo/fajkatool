package eu.spaj.fajkatool

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import eu.spaj.fajkatool.helpers.SharedPreferencesHelper

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    fun getSignum(): String = SharedPreferencesHelper.getProp(this, getString(R.string.signum), "")
    fun saveSignum(value: String) = SharedPreferencesHelper.saveProp(this, getString(R.string.signum), value)
    fun saveGroup(value: String) = SharedPreferencesHelper.saveProp(this, getString(R.string.group), value)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getSignum().isNotEmpty()) {
            startActivity(intentFor<MainActivity>())
        }
        setContentView(R.layout.activity_login)

        email_sign_in_button.setOnClickListener { attemptLogin() }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        FirebaseAuth.getInstance().signInAnonymously()
        // Reset errors.
        name_view.error = null
        main_group_view.error = null

        // Store values at the time of the login attempt.
        val nameStr = name_view.text.toString()
        val groupStr = main_group_view.text.toString()

        var cancel = false
        var focusView: View? = null

        if (TextUtils.isEmpty(groupStr)) {
            name_view.error = getString(R.string.error_field_required)
            focusView = main_group_view
            cancel = true
        }

        if (TextUtils.isEmpty(nameStr)) {
            name_view.error = getString(R.string.error_field_required)
            focusView = name_view
            cancel = true
        }



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            saveSignum(nameStr)
            saveGroup(groupStr)
            startActivity(intentFor<MainActivity>())
        }
    }
}

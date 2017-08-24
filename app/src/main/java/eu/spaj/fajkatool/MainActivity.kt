package eu.spaj.fajkatool

import android.os.Bundle
import eu.spaj.fajkatool.firebase.FirebaseControl
import eu.spaj.fajkatool.helpers.SharedPreferencesHelper
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private fun SIGNUM(): String = getString(R.string.signum)
    private fun GROUP(): String = getString(R.string.group)
    private val questions: Array<String> = arrayOf(
            getString(R.string.cig_msg_1), getString(R.string.cig_msg_2), getString(R.string.cig_msg_3)
    )

    fun <T> getProp(prop: String, default: T): T = SharedPreferencesHelper.getProp(this, prop, default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cig_button.setOnClickListener { sendMessage() }

        val stringBuilder = StringBuilder()
        stringBuilder.append(getString(R.string.hello_string)).append(" ").append(getProp(SIGNUM(), ""))
        name_view.text = stringBuilder.toString()
        val group = GROUP() + ": " + getProp(GROUP(), "")
        main_group_view.text = group
        FirebaseMessaging.getInstance().subscribeToTopic(getProp(GROUP(), "null"))
    }

    private fun sendMessage() {
        async(UI) {
            val data: Deferred<String?> = bg { FirebaseControl.post(getJsonBody()) }
            val response: String? = data.await()
        }
    }

    private fun getJsonBody(): String {
        val json: JSONObject = JSONObject()
        val data: JSONObject = JSONObject()

        val randomInt = (Math.random() * questions.size).toInt()

        var question = questions[randomInt]

        val username = getProp(SIGNUM(), "null")

        if (username == "erafaja") {
            question = "erafaja zaprasza na faja!"
        }

        json.put("to", "/topics/" + getProp(GROUP(), "null"))
        data.put("title", question)
        data.put("body", "Sent by " + getProp(SIGNUM(), "null"))
        json.put("notification", data)
        return json.toString()
    }
}

package eu.spaj.fajkatool.firebase

/**
 * elo Rafał on 30/07/2017.
 */
object FirebaseControl {
    private val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")
    const private val FCM_URL = "URL_TO_FIREBASE_APP"
    const private val CONTENT_TYPE = "Content-Type"
    const private val APPLICATION_JSON = "application/json"
    const private val AUTHORIZATION = "Authorization"
    const private val SERVER_API_KEY = "FIREBASE_APP_API_KEY"
    const private val AUTH_KEY = "key=" + SERVER_API_KEY
    val client = OkHttpClient()

    public fun post(json: String): String? {
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .addHeader(AUTHORIZATION, AUTH_KEY)
                .url(FCM_URL)
                .post(body)
                .build()

        val response = client.newCall(request).execute()

        return response.body()?.string()
    }

}

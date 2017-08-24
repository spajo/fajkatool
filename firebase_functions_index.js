const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//

var notificationReq;

var firebase = require('firebase-admin');
var request = require('request');

var API_KEY = "API_key"; // Your Firebase Cloud Messaging Server API key

// Fetch the service account key JSON file contents
var serviceAccount = require("JSON_file");

// Initialize the app with a service account, granting admin privileges
firebase.initializeApp({
    credential: firebase.credential.cert(serviceAccount),
    databaseURL: "firebase_DB_URL"
});
ref = firebase.database().ref();

exports.helloWorld = functions.https.onRequest((request, response) => {
    console.log(request.body);
    notificationReq = request.body;
    listenForNotificationRequests(notificationReq);
    response.send(notificationReq.notification.title + "  " + notificationReq.notification.body + "  " + notificationReq.to);
});

function listenForNotificationRequests(notifRequest) {
    sendNotificationToUser(
        notifRequest,
        function () {
            console.log("on SUCC");
            requestSnapshot.ref.remove();
        }
    );
};



function sendNotificationToUser(notifRequest, onSuccess) {
    request({
        url: 'https://fcm.googleapis.com/fcm/send',
        method: 'POST',
        headers: {
            'Content-Type': ' application/json',
            'Authorization': 'key=' + API_KEY
        },
        body: JSON.stringify({
            notification: {
                title: notifRequest.notification.title,
                body: notifRequest.notification.body,
                sound: "default"
            },
            to: notifRequest.to
        })
    }, function (error, response, body) {
        if (error) { console.error(error); }
        else if (response.statusCode >= 400) {
            console.error('HTTP Error: ' + response.statusCode + ' - ' + response.statusMessage);
        }
        else {
            onSuccess();
        }
    });
}

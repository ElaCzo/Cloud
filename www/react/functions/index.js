const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

let db = admin.firestore();

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.helloWorld = functions.https.onRequest((request, response) => {
  response.send("Hello from Firebase!");
});

exports.firestoreUpdate = functions.storage.object().onFinalize(async (object) => {
  console.log(object);
});

/*exports.onFileChange = functions.storage.object().onFinalize(event => {
  console.log(event);
  return;
});*/

exports.addAllFilesInFireStore = functions.https.onRequest((request, response) => {
  var storageRef = admin.storage();

  storageRef.listAll().then(function (result) {
    result.items.forEach(function (docRef) {
 
        let data = {
          title: docRef.name,
          path: docRef.fullPath 
        };

        let setDoc = db.collection('livres').doc().set(data);

    });
  }).catch(function (error) {
    console.log(error)
  })

  response.send('Done.')
});
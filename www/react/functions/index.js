const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.helloWorld = functions.https.onRequest((request, response) => {
  response.send("Hello from Firebase!");
});

exports.firestoreUpdate = functions.storage.object().onFinalize(async (object) => {
  console.log(object);
});

exports.onFileChange = functions.storage.object().onFinalize(event => {
  console.log(event);
  return;
});

exports.addAllFilesInFireStore = functions.https.onRequest((request, response) => {
  // Since you mentioned your images are in a folder,
  // we'll create a Reference to that folder:
  var storageRef = firebase.storage();


  // Now we get the references of these images
  storageRef.listAll().then(function (result) {
    result.items.forEach(function (docRef) {
      // And finally display them

      /*docRef.getDownloadURL().then(function (url) {*/
        
        let data = {
          title: docRef.name,
          url: docRef.fullPath 
        };
        
        // Add a new document in collection "cities"
        let setDoc = db.collection('livres').doc().set(data);

      /*}).catch(function (error) {
        // Handle any errors
      });*/

    });
  }).catch(function (error) {
    // Handle any errors
  })
});
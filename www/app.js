(function() {
    // Your web app's Firebase configuration
    var firebaseConfig = {
        apiKey: "AIzaSyB-OG_3lnqcdChpwD42uKvYWMHGZWlc4Ow",
        authDomain: "daar-a1ea7.firebaseapp.com",
        databaseURL: "https://daar-a1ea7.firebaseio.com",
        projectId: "daar-a1ea7",
        storageBucket: "daar-a1ea7.appspot.com",
        messagingSenderId: "653755445186",
        appId: "1:653755445186:web:be24541935e8161d02b05b",
        measurementId: "G-BGZWWNYW0E"
    };
    // Initialize Firebase
    firebase.initializeApp(firebaseConfig);
    firebase.analytics();

    const txtRech = document.getElementById('rechercher');

    const db = firebase.firestore();

    const livresListe = document.querySelector('#livre-list');

    function renderLivre(doc) {
        let li = document.createElement('li');
        let titre = document.createElement('span');

        li.setAttribute('data-id', doc.id)
        titre.textContent = doc.data().titre;

        li.appendChild(titre);
        livresListe.appendChild(li)
    }

    db.collection('livres').get().then((snapshot) =>
        snapshot.docs.forEach(doc => {
            renderLivre(doc);
            //console.log(doc.data())
        })
    )
}());
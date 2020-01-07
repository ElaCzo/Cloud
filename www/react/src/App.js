import React from 'react';
import './App.css';
import Livres from './Livres.js';
import * as firebase from "firebase/app";

// Add the Firebase services that you want to use
import "firebase/firestore";

class App extends React.Component {
  constructor() {
    super();

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
    try {
      firebase.initializeApp(firebaseConfig);
      firebase.analytics();
    } catch (e) { 
      console.log(e);
    }

    this.state = {
      firebaseConfig: firebaseConfig,
      db: firebase.firestore(),
      livresRes: [],
      value: "",
      isSubmitted: false,
    };
  }

  handleChange(event) {
    this.setState({value: event.target.value})
  }

  submitQuery = (event) => {
    event.preventDefault();
    let livresRes = [];
    this.state.db.collection('livres').where("title", "==", this.state.value)
    .get()
    .then(function(querySnapshot) {
      querySnapshot.forEach(function(doc) {
          livresRes.push(JSON.stringify(doc.data()));
      });
    })

    this.setState({
      livresRes: livresRes,
      isSubmitted: true,
    }, function () {
      console.log(this.state.livresRes, " App");
    })
  }


  render() {
    return (
      <div className="App">
        <h1>Bibliothèque en ligne</h1>
        <form role="search" onSubmit={this.submitQuery.bind(this)}>
          <input type="text" title="Recherche par mots-clés" id="rechercher" onChange={this.handleChange.bind(this)}/>
        </form>
        { this.state.isSubmitted && <Livres livresRes={this.state.livresRes} /> }
      </div>
    );
  }
}

export default App;

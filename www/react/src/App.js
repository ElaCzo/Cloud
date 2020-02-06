import React from 'react';
import './App.css';
import Livres from './Livres.js';
import * as firebase from "firebase/app";

import handleQuery from './handleQuery.js';
//import fetchResults from './handleQuery.js';

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
    };
  }

  handleChange(event) {
    console.log(this.state.value)
    this.setState({ value: event.target.value })
  }

  /*submitQuery = (event) => {
    event.preventDefault();
    let livresResult = [];
    let resultFromHandleQuery = handleQuery(this.state.value);
    this.state.db.collection('livres').where("title", "==", resultFromHandleQuery).get()
    .then((querySnapshot)  => {
      querySnapshot.forEach( (doc)  => {
          livresResult.push(doc.data())
      });
      this.setState({
        livresRes: livresResult
      }, function () {
        console.log(this.state.livresRes, " App");
      })
    }) 
  }*/

  submitQuery = (event) => {
    event.preventDefault();

    let books = []
    const proxyurl = "https://cors-anywhere.herokuapp.com/";
    fetch(proxyurl + "https://mysterious-oasis-90910.herokuapp.com/searchbooks?search=" + this.state.value)
      .then(res => res.json())
      .then(
        (result) => {
          books = result.books; // à vérifier selon le json reçu
          console.log(books);

          this.setState({
            livresRes: books
          }, function () {
            console.log(this.state.livresRes, " App");
          })
        },
        // Remarque : il est important de traiter les erreurs ici
        // au lieu d'utiliser un bloc catch(), pour ne pas passer à la trappe
        // des exceptions provenant de réels bugs du composant.
        (error) => { }
      )

  }


  render() {
    return (
      <div className="App">
        <h1>Bibliothèque en ligne</h1>
        <form role="search" onSubmit={(e) => this.submitQuery(e)}>
          <input
            type="text"
            title="Recherche par mots-clés"
            id="rechercher" value={this.state.value}
            onChange={(e) => this.handleChange(e)}
            autoComplete="off"
          />
        </form>
        {<Livres livresRes={this.state.livresRes} />}
      </div>
    );
  }
}

export default App;

import React from 'react';
import logo from './logo.svg';
import './App.css';
import * as firebase from "firebase/app";

// Add the Firebase services that you want to use
import "firebase/firestore";

const Livres = (props) => {
  if (props.value === "")
    return <div></div>;

  else {
    /*return <div>
    <ul>
    {props.livres.get().then(
      (querySnapshot) => {
        querySnapshot.map(
          (doc) => { return <li>blu</li> }
        );
      }
    )
    }
    </ul>
    </div>*/

    /* snapshot.docs.forEach(doc => {
            return <li>doc.data().title</li>;
            //console.log(doc.data())
        }) */
    /*props.livres.get().then((snapshot) => {
      console.log(snapshot.size);
      if (snapshot.size > 0) {
        const listItems = snapshot.docs.map(doc => {
          <div>{doc.data().title}</div>
          //console.log(doc.data())
        })

        return <ul>{listItems}</ul>;
      }
      else
        return <div></div>;
    }
    )
  }*/
    const numbers = [1, 2, 3, 4, 5];
    const liste = [];
    const res = props.livres.get().then((snap) => {
      snap.forEach((doc) => liste.push(<li>doc.data().title</li>))
    });

    console.log(liste);
    return <ul>{liste}</ul>;
  }
}

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
    } catch (e) { }

    this.state = {
      firebaseConfig: firebaseConfig,
      db: firebase.firestore(),
      livresRef: firebase.firestore().collection('livres'),
      livresRes: firebase.firestore().collection('livres'),
      value: ""
    };
  }

  queryCustom(event) {
    this.setState({ value: event.target.value });
    var livrestmp = this.state.livresRef.where("title", "==", event.target.value);
    this.setState({ livresRes: livrestmp });
  }

  handleSubmit(event) {
    event.preventDefault();
  }

  render() {
    return (
      <div className="App">
        <h1>Bibliothèque en ligne</h1>
        <form role="search" onSubmit={this.handleSubmit.bind(this)}>
          <input type="text" title="Recherche par mots-clés" value={this.state.value} id="rechercher" onChange={this.queryCustom.bind(this)} />
        </form>
        <Livres value={this.state.value} livres={this.state.livresRes} />
      </div>
    );
  }
}

export default App;

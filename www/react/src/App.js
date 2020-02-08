import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import InputGroup from 'react-bootstrap/InputGroup'
import Form from 'react-bootstrap/Form'
import FormControl from 'react-bootstrap/FormControl'
import Button from 'react-bootstrap/Button'
import Tabs from 'react-bootstrap/Tabs'
import Nav from 'react-bootstrap/Nav'
import Tab from 'react-bootstrap/Tab'
import Card from 'react-bootstrap/Card'
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Container from 'react-bootstrap/Container'
import './App.css';
import Livres from './Livres.js';
import * as firebase from "firebase/app";

// import handleQuery from './handleQuery.js';
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
      //firebase.analytics();
    } catch (e) {
      console.log(e);
    }

    this.state = {
      firebaseConfig: firebaseConfig,
      db: firebase.firestore(),
      livresRes: [],
      suggRes: [],
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
    let sugg = []
    const proxyurl = "https://cors-anywhere.herokuapp.com/";
    fetch(proxyurl + "https://mysterious-oasis-90910.herokuapp.com/searchbooks?search=" + this.state.value)
      .then(res => res.json())
      .then(
        (result) => {
          books = result.books;
          sugg = result.sugg;

          this.setState({
            livresRes: books,
            suggRes: sugg
          }, function () {
            console.log(this.state.livresRes, " App");
            console.log(this.state.suggRes, " App");
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
        <Container>
          <Row>
            <Col>
              <Card>
                <Card.Body>
                  <Card.Title>Bibliothèque en ligne</Card.Title>
                </Card.Body>
              </Card>
            </Col>
          </Row>
          <Row>
            <Col>
              <Tabs defaultActiveKey="recherche" id="uncontrolled-tab-example">
                <Tab eventKey="recherche" title="Recherche par mots-clés">
                  <Form onSubmit={(e) => this.submitQuery(e)}>
                    <InputGroup className="mb-3">
                      <FormControl
                        placeholder="Rechercher"
                        aria-label="Rechercher"
                        aria-describedby="basic-addon2"
                        onChange={(e) => this.handleChange(e)}
                        value={this.state.value}
                        id="rechercher"
                        autoComplete="off"
                      />
                      <InputGroup.Append>
                        <Button variant="outline-secondary" type="submit">OK</Button>
                      </InputGroup.Append>
                    </InputGroup>
                  </Form>
                  <Container>
                    <Row>
                      <Col>
                        {<Livres livresRes={this.state.livresRes} num='1' />}
                      </Col>
                      <Col>
                        {<Livres livresRes={this.state.suggRes} num="2" />}
                      </Col>
                    </Row>
                  </Container>
                </Tab>
                <Tab eventKey="regex" title="Recherche par regex">
                  <Form onSubmit={(e) => this.submitQuery(e)}>
                    <InputGroup className="mb-3">
                      <FormControl
                        placeholder="Rechercher"
                        aria-label="Rechercher"
                        aria-describedby="basic-addon2"
                        onChange={(e) => this.handleChange(e)}
                        value={this.state.value}
                        id="rechercher2"
                        autoComplete="off"
                      />
                      <InputGroup.Append>
                        <Button variant="outline-secondary" type="submit">OK</Button>
                      </InputGroup.Append>
                    </InputGroup>
                  </Form>
                  <Container>
                    <Row>
                      <Col>
                        {<Livres livresRes={this.state.livresRes} num='3' />}
                      </Col>
                      <Col>
                        {<Livres livresRes={this.state.suggRes} num="4" />}
                      </Col>
                    </Row>
                  </Container>
                </Tab>
              </Tabs>
            </Col>
          </Row>
        </Container>
      </div>
    );
  }
}

export default App;

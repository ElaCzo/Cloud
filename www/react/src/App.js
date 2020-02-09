import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Container from 'react-bootstrap/Container'
import './App.css';
import Titre from './Titre.js'
import Navigation from './Navigation.js'

const App = (props) => {

    return (
      <div className="App">
        <Container>
          <Row>
            <Col>
              <Titre/>
            </Col>
          </Row>
          <Row>
            <Col>
              <Navigation/>
            </Col>
          </Row>
        </Container>
      </div>
    );
}

export default App;

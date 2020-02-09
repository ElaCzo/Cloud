import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import InputGroup from 'react-bootstrap/InputGroup'
import Form from 'react-bootstrap/Form'
import FormControl from 'react-bootstrap/FormControl'
import Button from 'react-bootstrap/Button'
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Container from 'react-bootstrap/Container'
import './App.css';
import Livres from './Livres.js';

class Formulaire extends React.Component {
    constructor() {
        super();

        this.state = {
            livresRes: [],
            suggRes: [],
            value: "",
        };
    }

    handleChange(event) {
        console.log(this.state.value);
        this.setState({ value: event.target.value });
    }

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

                (error) => { }
            )
    }

    render() {
        return (
            <div>
                <Form onSubmit={(e) => this.submitQuery(e)}>
                    <InputGroup className="mb-3">
                        <FormControl
                            placeholder="Rechercher"
                            aria-label="Rechercher"
                            aria-describedby="basic-addon2"
                            onChange={(e) => this.handleChange(e)}
                            value={this.state.value}
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
            </div>
        );
    }
}

export default Formulaire;
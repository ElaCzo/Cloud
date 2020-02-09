import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import Card from 'react-bootstrap/Card'

const Titre = (props) => {

    return (
        <Card>
            <Card.Body>
                <Card.Title>Bibliothèque en ligne</Card.Title>
            </Card.Body>
        </Card>
    );
}

export default Titre;
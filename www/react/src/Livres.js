import ListGroup from 'react-bootstrap/ListGroup'
import Card from 'react-bootstrap/Card'
import React from 'react';

const Livres = (props) => {
    let tabTitres = [];

    tabTitres = props.livresRes.map(livre => {
        let cle = livre.title + "-" + props.num;
        console.log(cle);

        if (livre.title.includes("/") || livre.title.includes(".txt")) {
            return [cle, "Titre indisponible"];
        }
        else {
            return [cle, livre.title];
        }
    });

    let titre;
    if ((parseInt(props.num) % 2) === 1) {
        titre = "Résultats de la recherche";
    }
    else {
        titre = "Suggestions";
    }

    return (
        <Card>
            <Card.Header> {titre} </Card.Header>
            <ListGroup variant="flush">
                {tabTitres.map(livre => {
                    return (
                        <ListGroup.Item disabled eventKey={livre[0]}>{livre[1]}</ListGroup.Item>
                    )
                })}
            </ListGroup>
        </Card>
    );
}
export default Livres;
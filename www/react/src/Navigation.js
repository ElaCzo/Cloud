import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import Tabs from 'react-bootstrap/Tabs'
import Tab from 'react-bootstrap/Tab'
import './App.css';
import Formulaire from './Formulaire.js'
import FormulaireRegex from './FormulaireRegex.js'
  
const Navigation = (props) => {
    
    return (
        <Tabs defaultActiveKey="recherche" id="uncontrolled-tab-example">
            <Tab eventKey="recherche" title="Recherche par mots-clés">
                <Formulaire />
            </Tab>
            <Tab eventKey="regex" title="Recherche par regex">
                <FormulaireRegex />
            </Tab>
        </Tabs>
    );
}

export default Navigation;
import React from 'react';

const Livres = (props) => {
    const { livresRes } = props;
    console.log(livresRes, " livresRes -> Livres")

    const livresList = livresRes.map((elem) => {
        let livre = JSON.parse(elem)
        console.log(livre, " inside")
        return(
            <div key={livre.chemin}>{livre.title}</div>
        )
    })
    console.log(livresList, " livresList -> Livres")
            
    return(
        <div className="liste-livres">
            { livresList }
        </div>
    );
    
}
export default Livres;
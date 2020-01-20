import React from 'react';

const Livres = ({livresRes}) => {
    return(
        <div className="liste-livres">
            {livresRes.map(livre => {
                return (
                <div key={livre.path}>{livre.title}</div>
                )
            })}
        </div>
    );
}
export default Livres;
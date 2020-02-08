import React from 'react';

const Livres = (props) => {
    return(
        <div className={ "liste-livres-"+props.num }>
            {props.livresRes.map(livre => {
                return (
                <div key={livre.path}>{livre.title}</div>
                )
            })}
        </div>
    );
}
export default Livres;
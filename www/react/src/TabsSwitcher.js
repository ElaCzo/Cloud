import React from 'react';

const TabsSwitcher = ({tabs}) => {
    return(
        <div className="liste-tabs">
            {tabs.map(tab => {
                return (
                <div key={tab.num}>{tab.title}</div>
                )
            })}
        </div>
    );
}
export default TabsSwitcher;
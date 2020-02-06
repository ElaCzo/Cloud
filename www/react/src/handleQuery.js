const handleQuery = (query) => {
    let books = []
    const proxyurl = "https://cors-anywhere.herokuapp.com/";
    fetch(proxyurl+"https://mysterious-oasis-90910.herokuapp.com/searchbooks?search=" + query)
        .then(res => res.json())
        .then(
            (result) => {
                books = result.books; // à vérifier selon le json reçu
                console.log(books);
            },
            // Remarque : il est important de traiter les erreurs ici
            // au lieu d'utiliser un bloc catch(), pour ne pas passer à la trappe
            // des exceptions provenant de réels bugs du composant.
            (error) => {}
        )

    return books
}

/*render() {
    const { error, isLoaded, items } = this.state;
    if (error) {
        return <div>Erreur : {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Chargement…</div>;
    } else {
        return (
            <ul>
                {items.map(item => (
                    <li key={item.name}>
                        {item.name} {item.price}
                    </li>
                ))}
            </ul>
        );
    }
}*/



/*const handleQuery = (value) => {
    //    var javaClass = java.type("HandlingOfRequest")
    //    return javaClass.handlingOfRequest(value)
    //Packages.HandlingOfRequest.handlingOfRequest(value)
    return value
}*/

export default handleQuery;
//export default fetchResults;
const url = "https://www.nicklasnielsen.dk/rest-endpoint/api/person/";

function getAllPersons() {
    return fetch(url + "all")
        .then(handleHttpErrors)
}

function getPersonById(id) {
    return fetch(url + "id/" + id)
        .then(handleHttpErrors)
}

function addPerson(person) {
    const options = makeOptions("POST", person);

    return fetch(url, options)
        .then(handleHttpErrors)
}

function editPerson(person) {
    const options = makeOptions("PUT", person);
    let id = person.id;

    return fetch(url + "id/" + id, options)
        .then(handleHttpErrors)
}

function deletePerson(id) {
    const options = makeOptions("DELETE");

    return fetch(url + "id/" + id, options)
        .then(handleHttpErrors)
}

const personFacade = {
    getAllPersons,
    getPersonById,
    addPerson,
    editPerson,
    deletePerson
}

function makeOptions(method, body) {
    var opts = {
        method: method,
        headers: {
            "Content-type": "application/json",
            "Accept": "application/json"
        }
    }

    if (body) {
        opts.body = JSON.stringify(body);
    }

    return opts;
}

function handleHttpErrors(res) {
    if (!res.ok) {
        return Promise.reject({ status: res.status, fullError: res.json() })
    }

    return res.json();
}

export default personFacade;
const url = "http://localhost:3333/api/users/";

function getUsers() {
    return fetch(url)
        .then(res => res.json())
        .catch(err => {
            if (err.status) {
                err.fullError.then(e => console.log(e.detail))
            }
            else { console.log("Network error"); }
        });

}

function getUser(id) {
    return fetch(url + id)
        .then(res => handleHttpErrors(res))

}

function addUser(user) {
    const options = makeOptions("POST", user);

    return fetch(url, options)
    .then(res => handleHttpErrors(res))
}

function editUser(user) {
    const options = makeOptions("PUT", user);
    let id = user.id;

    return fetch(url + id, options)
    .then(res => handleHttpErrors(res))
}

function deleteUser(id) {
    const options = makeOptions("DELETE");

    return fetch(url + id, options)
    .then(res => handleHttpErrors(res))
}

const userFacade = {
    getUsers,
    getUser,
    addUser,
    editUser,
    deleteUser
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

export default userFacade;
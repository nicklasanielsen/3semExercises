import "./style.css"
import "bootstrap/dist/css/bootstrap.css"
import "./jokeFacade"
import userFacade from "./userFacade"
import jokeFacade from "./jokeFacade"

/* 
  Add your JavaScript for all exercises Below or in separate js-files, which you must the import above
*/

/* JS For Exercise-1 below */
function getAllJokes() {
  const jokes = jokeFacade.getJokes();
  const jokesList = jokes.map(joke => "<li>" + joke + "</li>");
  const jokesAsListItems = jokesList.join("");
  document.getElementById("jokes").innerHTML = jokesAsListItems;
}

getAllJokes();

document.getElementById("getJokeByIdForm").addEventListener("submit", function (e) {
  e.preventDefault();

  let requestedId = document.getElementById("requestedJokeId").value;
  let joke = jokeFacade.getJokeById(requestedId);

  document.getElementById("jokeSelectedById").innerHTML = joke;
});

document.getElementById("addJokeForm").addEventListener("submit", function (e) {
  e.preventDefault();

  let newJoke = document.getElementById("newJoke").value;

  jokeFacade.addJoke(newJoke);

  getAllJokes();
});

/* JS For Exercise-2 below */
function getRemoteJoke() {
  const url = "https://studypoints.info/jokes/api/jokes/period/hour";

  fetch(url).then(res => res.json()).then(data => {
    let importJoke = data.joke;

    document.getElementById("ex2p").innerHTML = importJoke;
  });
}

document.getElementById("ex2btn").addEventListener("click", function (e) {
  e.preventDefault();

  getRemoteJoke();
  setInterval(getRemoteJoke, 3600000); // Updates every hour
});


/* JS For Exercise-3 below */
function getAllUsers() {
  userFacade.getUsers()
    .then(users => {
      const userRows = users.map(user => `
    <tr>
      <td>${user.id}</td>
      <td>${user.age}</td>
      <td>${user.name}</td>
      <td>${user.gender}</td>
      <td>${user.email}</td>
    </tr>
  `);

      const userRowsAsString = userRows.join("");
      document.getElementById("allUserRows").innerHTML = userRowsAsString;
    });
}

getAllUsers();

document.getElementById("getUserForm").addEventListener("submit", function (e) {
  e.preventDefault();

  let id = document.getElementById("userId").value;

  userFacade.getUser(id)
    .then(user => {
      const userRow = `
    <tr>
      <td>${user.id}</td>
      <td>${user.age}</td>
      <td>${user.name}</td>
      <td>${user.gender}</td>
      <td>${user.email}</td>
    </tr>
    `;

      document.getElementById("importedUserRow").innerHTML = userRow;
    })
    .catch(err => {
      if (err.status) {
        err.fullError.then(e =>
          document.getElementById("importedUserRow").innerHTML = e.msg
        );
      } else {
        document.getElementById("importedUserRow").innerHTML = "Network error"
      }
    });

});

document.getElementById("addUserForm").addEventListener("submit", function (e) {
  e.preventDefault();

  let user = {
    "age": document.getElementById("addUserAge").value,
    "name": document.getElementById("addUserName").value,
    "gender": document.getElementById("addUserGender").value,
    "email": document.getElementById("addUserEmail").value
  }

  userFacade.addUser(user)
  .catch(err => {
    if (err.status) {
      err.fullError.then(e =>
        document.getElementById("addUserError").innerHTML = e.msg
      );
    } else {
      document.getElementById("addUserError").innerHTML = "Network error"
    }
  });
  setTimeout(getAllUsers, 500);
});

document.getElementById("editUserForm").addEventListener("submit", function (e) {
  e.preventDefault();

  let user = {
    "id": document.getElementById("editUserId").value,
    "age": document.getElementById("editUserAge").value,
    "name": document.getElementById("editUserName").value,
    "gender": document.getElementById("editUserGender").value,
    "email": document.getElementById("editUserEmail").value
  }

  userFacade.editUser(user)
  .catch(err => {
    if (err.status) {
      err.fullError.then(e =>
        document.getElementById("editUserError").innerHTML = e.msg
      );
    } else {
      document.getElementById("editUserError").innerHTML = "Network error"
    }
  });
  setTimeout(getAllUsers, 500);
});

document.getElementById("deleteUserForm").addEventListener("submit", function (e) {
  e.preventDefault();

  let id = document.getElementById("deleteUserId").value;

  userFacade.deleteUser(id)
    .catch(err => {
      if (err.status) {
        err.fullError.then(e =>
          document.getElementById("deleteUserError").innerHTML = e.msg
        );
      } else {
        document.getElementById("deleteUserError").innerHTML = "Network error"
      }
    });

setTimeout(getAllUsers, 500);
});

/* 
Do NOT focus on the code below, UNLESS you want to use this code for something different than
the Period2-week2-day3 Exercises
*/

function hideAllShowOne(idToShow) {
  document.getElementById("about_html").style = "display:none"
  document.getElementById("ex1_html").style = "display:none"
  document.getElementById("ex2_html").style = "display:none"
  document.getElementById("ex3_html").style = "display:none"
  document.getElementById(idToShow).style = "display:block"
}

function menuItemClicked(evt) {
  const id = evt.target.id;
  switch (id) {
    case "ex1": hideAllShowOne("ex1_html"); break
    case "ex2": hideAllShowOne("ex2_html"); break
    case "ex3": hideAllShowOne("ex3_html"); break
    default: hideAllShowOne("about_html"); break
  }
  evt.preventDefault();
}
document.getElementById("menu").onclick = menuItemClicked;
hideAllShowOne("about_html");




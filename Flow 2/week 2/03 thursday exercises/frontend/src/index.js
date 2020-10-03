import "./style.css"
import "bootstrap/dist/css/bootstrap.css"
import "bootstrap"
import "./personFacade.js";
import personFacade from "./personFacade";

const errorTag = document.getElementById("error");

/*
    Functions
*/

// Used to get all persons
function getAllPersons() {
  const persons = personFacade.getAllPersons()
    .then(data => {
      const persons = data.all;
      const personRows = persons.map(person => `
      <tr>
        <td>${person.id}</td>
        <td>${person.fName}</td>
        <td>${person.lName}</td>
        <td>${person.phone}</td>
        <td>${person.street}</td>
        <td>${person.zip}</td>
        <td>${person.city}</td>
        <td><a href="javascript:void(0);" name="deleteRequest" id="${person.id}">delete</a> / <a href="javascript:void(0);" name="editRequest" id="${person.id}" data-toggle="modal" data-target="#myModal">edit</a></td>
      </tr>
    `);

      const personRowsAsString = personRows.join("");
      document.getElementById("tbody").innerHTML = personRowsAsString;
    })
    .catch(err => {
      if (err.status) {
        err.fullError.then(e => errorTag.innerHTML = e.message);
      } else {
        errorTag.innerHTML = "Network error";
      }
    });

}

// Used to add a new person
function addPerson() {
  const person = {
    "fName": document.getElementById("fname").value,
    "lName": document.getElementById("lname").value,
    "phone": document.getElementById("phone").value,
    "street": document.getElementById("street").value,
    "zip": document.getElementById("zip").value,
    "city": document.getElementById("city").value
  }

  personFacade.addPerson(person)
    .catch(err => {
      if (err.status) {
        err.fullError.then(e => errorTag.innerHTML = e.message);
      } else {
        errorTag.innerHTML = "Network error";
      }
    });
}

// Used to edit a existing person
function editPerson(id) {
  document.getElementById("id").value = id;

  personFacade.getPersonById(id)
    .then(person => {
      document.getElementById("fname").value = person.fName;
      document.getElementById("lname").value = person.lName;
      document.getElementById("phone").value = person.phone;
      document.getElementById("street").value = person.street
      document.getElementById("zip").value = person.zip;
      document.getElementById("city").value = person.city;
    })
    .catch(err => {
      if (err.status) {
        err.fullError.then(e => errorTag.innerHTML = e.message);
      } else {
        errorTag.innerHTML = "Network error";
      }
    });
}

function updatePerson() {
  const person = {
    "id": document.getElementById("id").value,
    "fName": document.getElementById("fname").value,
    "lName": document.getElementById("lname").value,
    "phone": document.getElementById("phone").value,
    "street": document.getElementById("street").value,
    "zip": document.getElementById("zip").value,
    "city": document.getElementById("city").value
  }

  personFacade.editPerson(person)
    .catch(err => {
      if (err.status) {
        err.fullError.then(e => errorTag.innerHTML = e.message);
      } else {
        errorTag.innerHTML = "Network error";
      }
    });
}

// Used to delete a person
function deletePerson(id) {
  personFacade.deletePerson(id)
    .catch(err => {
      if (err.status) {
        err.fullError.then(e =>
          errorTag.innerHTML = e.message
        );
      } else {
        errorTag.innerHTML = "Network error"
      }
    });
}

/*
    EventListeners
*/

// Reload persons
document.getElementById("reload").addEventListener("click", function (e) {
  getAllPersons();
});

// Add and edit person handler
document.getElementById("savebtn").addEventListener("click", function (e) {
  let action = document.getElementById("id").value;

  if (action === "0") {
    // Create new person
    addPerson();
  } else {
    // Update person
    updatePerson();
  }
});

// Delete and edit person action links
document.getElementById("tbody").addEventListener("click", function (e) {
  let request = e.target;
  let id = request.id;

  if (request.name === "deleteRequest") {
    deletePerson(id);
  } else if (request.name === "editRequest") {
    editPerson(id);
  }
})

document.getElementById("addPerson").addEventListener("click", function (e) {
  document.getElementById("id").value = "0";
})
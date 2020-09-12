let svend = {
    name: "Svend Larsen",
    birthday: "01-08-1678",
    hobby: "Elsker at spise sun lolly",
    email: "lars@larsen.org"
};

for (prop in svend) {
    console.log(prop, svend[prop]);
}

delete svend.email;

for (prop in svend) {
    console.log(prop, svend[prop]);
}

svend.death = "23-05-2001";

for (prop in svend) {
    console.log(prop, svend[prop]);
}

function Person(firstName, lastName, age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;

    this.details = function () {
        return "Navn: " + this.firstName + " " + this.lastName + ", alder: " + this.age;
    };
}

let p = new Person("Nicklas", "Nielsen", 23);

console.log(p.details());
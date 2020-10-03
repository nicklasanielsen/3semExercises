var numbers = [1, 3, 5, 10, 11];
let count = 0;
let sum = 0;
var result = numbers.map(function () {
    if (count != numbers.length - 1) {
        sum = numbers[count] + numbers[count + 1];
    } else {
        sum = numbers[count];
    }
    count++;
    return sum;
})
console.log(result);


const names = ["Hassan", "Peter", "Jan", "Boline"];
let listItems = names.map(function (name) {
    return `<a href=""> ${names} </a>`
}).join("\n")

let str = "<nav>\n" + listItems + "\n</nav>";
console.log(str);


var persons = [{ name: "Hassan", phone: "1234567" },
{ name: "Peter", phone: "675843" },
{ name: "Jan", phone: "98547" },
{ name: "Boline", phone: "79345" }];

let personTableString = persons.map(function (persons) {
    return `${persons.name}: ${persons.phone}`;
}).join("\n");
console.log(personTableString)
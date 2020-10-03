var all = ["Hassan", "Peter", "Carla", "Boline"];

let str = all.join("#");
console.log(str)


var numbers = [2, 3, 67, 33];

let reduce = numbers.reduce(function (n1, n2) {
    return n1 + n2;
})
console.log(reduce);

var members = [
    { name: "Peter", age: 18 },
    { name: "Jan", age: 35 },
    { name: "Janne", age: 25 },
    { name: "Martin", age: 22 }]

function avg(accumulator, member) {
    accumulator.age += member.age;
    return accumulator;
}

let sumOfMembers = members.reduce(avg);
let avgMemberAge = sumOfMembers.age / members.length;

console.log(avgMemberAge);
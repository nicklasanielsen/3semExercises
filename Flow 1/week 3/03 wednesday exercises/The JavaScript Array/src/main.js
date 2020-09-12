console.log("Exercise A:");

var boys = ["Peter", "lars", "Ole"];
var girls = ["Janne", "hanne", "Sanne"];

console.log(boys);
console.log(girls);


console.log("Exercise B:");

var all = boys.concat(girls);

console.log(all);


console.log("Exercise C:");

var singleStringComma = all.join();
var singleStringHyphen = all.join("-");

console.log("Comma: " + singleStringComma);
console.log("Hyphen: " + singleStringHyphen);


console.log("Exercise D:");

all.push("Lone", "Gitte");

console.log(all);


console.log("Exercise E:");

all.unshift("Hans", "Kurt");
console.log(all);


console.log("Exercise F:");

all.shift();

console.log(all);


console.log("Exercise G:");

all.pop();

console.log(all);


console.log("Exercise H:");

all.splice(3, 2);

console.log(all);


console.log("Exercise I:");

all.reverse();

console.log(all);


console.log("Exercise J:");

all.sort();

console.log(all);


console.log("Exercise K:");

all.sort(function (a, b) {
    return a.toLowerCase().localeCompare(b.toLowerCase());
});

console.log(all);


console.log("Exercise L:");

var allMap = all.map(x => x.toUpperCase());

console.log(allMap);


console.log("Exercise M:");

var allFilter = allMap.filter(name => name.charAt(0) === "l" || name.charAt(0) === "L");

console.log(allFilter);
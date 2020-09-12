// Function Declaration
// Observe: no return type, no type on parameters
function add(n1, n2) {
    return n1 + n2;
}

// Function Expression
var sub = function (n1, n2) {
    return n1 - n2;
};

// Callback example
var cb = function (n1, n2, callback) {
    try {
        if (typeof n1 !== "number")
            throw "n1 er ikke et tal!";
        if (typeof n2 !== "number")
            throw "n2 er ikke et tal!";
        if (typeof callback !== "function")
            throw "callback er ikke et funktionsnavn!";
    } catch (err) {
        return "Fejl: " + err;
    }

    return "Result from the two numbers: " + n1 + "+" + n2 + "=" + callback(n1, n2);
};

console.log(add(1, 2));
// Q: What will this print?
// A: Lægger tallene sammen

console.log(add);
// Q: What will it print and what does add represent?
// A: Funktionen bliver kaldt, men uden parameter, og den udskriver navnet på funktionen

console.log(add(1, 2, 3));
// Q: What will it print
// A: Lægger 1 og 2 sammen, og gør intet med tallet 3, eftersom funktionen kun tager imod to parameter

console.log(add(1));
// Q: What will it print     
// A: NaN - Den mangler parameter nr 2

console.log(cb(3, 3, add));
// Q: What will it print
// A: Udregner regnestykket - lægger tallene sammen

console.log(cb(4, 3, sub));
// Q: What will it print
// A: Udregner regnestykket - trækker 3 fra 4

console.log(cb(3, 3, add()));
// Q: What will it print (and what was the problem)
// A: Error - add bliver kaldt og returnere NaN/undefined og eftersom det ikke er et methodenavn fejler cb kaldet

console.log(cb(3, "hh", add));
// Q: What will it print
// A: 3+hh=3hh

var mul = function (n1, n2) {
    return n1 * n2;
};

console.log(cb(3, 3, mul));

var div = function (n1, n2) {
    try {
        if (n1 === 0)
            throw "n1";
        if (n2 === 0)
            throw "n2";
    } catch (err) {
        console.error("Fejl: " + err + " må ikke er nul!");
        return;
    }

    return n1 / n2;
};

console.log(cb(3, 0, div));
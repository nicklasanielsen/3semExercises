const fisk = ["Ali", "Hassan", "Jan", "Frederik"]

function myFilter(array, callback) {
    let arrayCopy = [];
    array.forEach(element => {
        const newItem = callback(element);
        if (newItem) {
            arrayCopy.push(newItem);
        }
    });
    return arrayCopy;
}

function isA(str) {
    if (str.includes("A") || str.includes("a")) {
        return str;
    }
}

let array1 = myFilter(fisk, isA);
console.log(array1);

var numbers = [1, 3, 5, 10, 11];

function changeSign(number) {
    return number * -1;
}

function myMap(callback, array) {
    let arrayCopy = [];
    array.forEach(element => {
        const newItem = callback(element)
        arrayCopy.push(newItem)
    });
    return arrayCopy;
}

let listItems2 = myMap(changeSign, numbers);
console.log(listItems2)
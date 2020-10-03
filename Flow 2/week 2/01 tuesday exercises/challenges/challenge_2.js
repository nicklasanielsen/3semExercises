var numbers = [1, 3, 5, 10, 11];

function makeListItem(number) {
    return `<li>${number}</li>`
}

function changeSign(number) {
    return number * -1;
}

let listItems = numbers.map(makeListItem).join("");

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
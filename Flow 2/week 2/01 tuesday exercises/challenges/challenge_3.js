var numbers = [1, 3, 5, 10, 11];

function makeListItem(number) {
    return `<li>${number}</li>`
}

let listItems = numbers.map(makeListItem).join("\n")
//console.log(listItems)

listItems = numbers.map(function (number) {
    return `<li>${number}</li>`
}).join("\n")
//console.log(listItems)

listItems = numbers.map(number => `<li>${number}</li>`).join("\n")
//console.log(listItems)

listItems = numbers.map(number => {
    `<li>${number}</li>`
}).join("\n")
//console.log(listItems)

listItems = numbers.map(number => (
    `<li>${number}</li>`
)).join("\n")
console.log(listItems)

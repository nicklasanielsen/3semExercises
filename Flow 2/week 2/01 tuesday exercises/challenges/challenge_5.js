let name = { name: "Peter", age: 14, gender: "male", phone: "123" }

https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/entries
function printObjectDetails(obj) {
    //const count = -1;
    const count = Object.keys(obj).length;
    console.log(`This object has ${count} properties`)
    console.log("Keys and Values for the object are: ")

    for (const [key, value] of Object.entries(obj)) {
        console.log(`${key}: ${value}`);
    }

}

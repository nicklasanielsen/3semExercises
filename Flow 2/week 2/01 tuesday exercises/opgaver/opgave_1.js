const fisk = ["Ali", "Hassan", "Jan", "Frederik"]

const res = fisk.filter(name => name.includes("A") || name.includes("a"));
console.log(res);

function reverse(str) {
    let reversed = "";
    for (let char of str) {
        reversed = char + reversed;
    }
    return reversed;
}

const resu = fisk.map(n => reverse(n));
console.log(resu);
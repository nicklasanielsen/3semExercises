//Using the bind(..) function
function Person(name) {
    this.name = name;
    console.log("Name: " + this.name);
    setTimeout(function () {
        console.log("Hi  " + this.name);
    }.bind(this), 2000);
}

//call it like this (do it, even if you know it’s silly ;-)
Person("Kurt Wonnegut");  //This calls the function
console.log("I'm global: " + name);  //Explain this

// Værdien name bliver sat som global variable

var p = new Person("Hans Hansen");  //Create an instance using the constructor function
console.log("I'm global: " + name);  //What’s different ?

// Her bliver navne værdien sat på "Person" og ikke globalt

var greeter = function () {
    console.log(this.message);
};

var comp1 = {message: "Hello World"};
var comp2 = {message: "Hi"};

var g1 = greeter.bind(comp1);//We can store a reference, with a specific “this” to use
var g2 = greeter.bind(comp2);//And here another “this”
setTimeout(g1, 500);
setTimeout(g2, 1000);

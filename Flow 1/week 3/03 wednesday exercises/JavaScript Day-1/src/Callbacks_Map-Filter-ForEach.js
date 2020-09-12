var names = ["Lars", "Peter", "Jan", "Ian"];

var toHTML = function(array){
    var result = ["<ul>", "</ul>"];
    var li = array.map(name => "<li>" + name + "</li>");
    
    result.splice(1, 0, li.join(""));
    
    return result.join("");
};

console.log(toHTML(names));

var filteredNames = names.filter(name => name.length <= 3);

names.forEach(name => console.log(name));
filteredNames.forEach(filteredName => console.log(filteredName));

var namesMap = names.map(name => name.toUpperCase());

console.log(namesMap);


var cars = [
  { id: 1, year: 1997, make: 'Ford', model: 'E350', price: 3000 },
  { id: 2, year: 1999, make: 'Chevy', model: 'Venture', price: 4900 },
  { id: 3, year: 2000, make: 'Chevy', model: 'Venture', price: 5000 },
  { id: 4, year: 1996, make: 'Jeep', model: 'Grand Cherokee', price: 4799 },
  { id: 5, year: 2005, make: 'Volvo', model: 'V70', price: 44799 }
];

var newerCars = cars.filter(car => car.year > 1999);

console.log(newerCars);

var volvo = cars.filter(car => car.make ==="Volvo");

console.log(volvo);

var price = cars.filter(car => car.price < 5000);

console.log(price);


var toSQL = function(array){
    var result = [];
    var stmt = array.map(car => "INSERT INTO cars (id,year,make,model,price) VALUES (" 
            + car.id + ","
            + car.year + ","
            + "\"" + car.make + "\","
            + "\"" + car.model + "\","
            + car.price
            + ");");
    
    result.push(stmt.join(""));
    
    return result.join("");
};

console.log(toSQL(cars));
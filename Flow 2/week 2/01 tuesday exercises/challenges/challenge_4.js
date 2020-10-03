var members = [
    { name: "Peter", age: 14, gender: "male" },
    { name: "Jan", age: 35, gender: "male" },
    { name: "Janne", age: 25, gender: "female" },
    { name: "Martin", age: 22, gender: "male" }]
const tableRows = members.map(member => `
<tr>
<td>${member.name}</td>
<td>${member.age}</td>
<td>${member.gender}</td>
</tr>  
  `).join("\n")

console.log(tableRows)  
import 'bootstrap/dist/css/bootstrap.css'

const URL = "http://restcountries.eu/rest/v1/alpha?codes=";
document.getElementById("svg2").addEventListener("click", getCountry);

function getCountry(evnt) {
    let countryCode = evnt.target.id;
    const countryContainer = document.getElementById("coutryContainer");
    console.log(countryCode);
    
    if(countryCode.includes("-")) {
        countryCode = countryCode.split("-")[0];
        console.log(countryCode);
    }

    const URLwCode = URL + countryCode;

    fetch(URLwCode).then(res => fetchWithErrorCheck(res))
        .then(data => {
            console.log(data);
            countryContainer.innerHTML = `<p>Country: ${data[0].name}</p>
                                        <p>Population: ${data[0].population}</p>
                                        <p>Area: ${data[0].area}</p>
                                        <p>Borders: ${data[0].borders}</p>`;
        });
}

function fetchWithErrorCheck(res) {
    if(!res.ok) {
        return Promise.reject({status: res.status, fullError: res.json()})
    }
    return res.json();
}
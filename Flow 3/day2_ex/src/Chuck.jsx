import { useState } from "react";

function Chuck() {
  const [joke, setJoke] = useState();

  function getJoke() {
    const URL = "https://api.chucknorris.io/jokes/random";

    fetch(URL)
      .then((res) => res.json())
      .then((data) => {
        setJoke(data.value);
      });
  }

  return (
    <div>
      <button onClick={getJoke}>Get Chuck Norris Joke</button>
      <p>{joke}</p>
    </div>
  );
}

export default Chuck;

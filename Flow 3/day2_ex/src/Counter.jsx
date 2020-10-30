import { useState, useEffect } from "react";
import PropTypes from "prop-types";

function Counter(props) {
  useEffect(() => {
    if (localStorage.getItem("counter") != null) {
      setCount(Number(localStorage.getItem("counter")));
    }
  }, []);

  const { count, crement } = props;

  const [counter, setCount] = useState(count);

  useEffect(() => {
    localStorage.setItem("counter", counter);
  }, [counter]);

  function incrementCounter() {
    setCount(counter + crement);
  }

  function decrementCounter() {
    setCount(counter - crement);
  }

  return (
    <div>
      <p>Counter: {counter}</p>
      <button onClick={incrementCounter}>Increment Counter</button>
      <button onClick={decrementCounter}>Decrement Counter</button>
    </div>
  );
}

Counter.propTypes = {
  count: PropTypes.number,
  crement: PropTypes.number,
};

Counter.defaultProps = {
  count: 0,
  crement: 1,
};

export default Counter;

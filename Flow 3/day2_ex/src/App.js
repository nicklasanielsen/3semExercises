import "./App.css";
import Counter from "./Counter";
import Chuck from "./Chuck";
import Dad from "./Dad";
import ListDemo from "./ListDemo";

function App() {
  return (
    <div className="App">
      <Counter count={25} crement={5} />
      <Chuck />
      <Dad />
      <ListDemo />
    </div>
  );
}

export default App;

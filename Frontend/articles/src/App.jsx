import { render } from "solid-js/web";

import "./index.scss";
import Articles from "./Articles";

const App = () => (
  <Articles/>
);
render(App, document.getElementById("app"));

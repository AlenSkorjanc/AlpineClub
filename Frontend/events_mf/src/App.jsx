import { render } from "solid-js/web";

import "./index.scss";
import Events from "./Events";

const App = () => (
  <Events/>
);
render(App, document.getElementById("app"));

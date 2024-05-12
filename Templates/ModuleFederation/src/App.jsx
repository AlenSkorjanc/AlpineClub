import { render } from "solid-js/web";

import "./index.scss";
import Example from "./Example";

const App = () => (
  <div class="mt-10 text-3xl mx-auto max-w-6xl">
    <div>Name: template</div>
    <div>Framework: solid-js</div>
    <div>Language: JavaScript</div>
    <div>CSS: Tailwind</div>

    <Example/>
  </div>
);

render(App, document.getElementById("app"));
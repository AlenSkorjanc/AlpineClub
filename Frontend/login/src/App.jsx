import { render } from "solid-js/web";

import "./index.scss";
import Login from "./Login";

const App = () => {
	return (
		<Login/>
	);
};

render(App, document.getElementById("app"));
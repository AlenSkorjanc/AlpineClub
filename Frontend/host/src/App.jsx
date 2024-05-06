import { render } from "solid-js/web";
import { Router, Route } from "@solidjs/router";

import Login from "login/Login";
import Articles from "articles/Articles";
import Article from "articles/Article";
import Events from "events_mf/Events";
import Event from "events_mf/Event";

import Header from "./Header";

import "./index.scss";

const App = props => (
	<>
		<Header />
		{props.children}
	</>
);

render(() => (
	<Router root={App}>
		<Route path="/" component={Login} />
		<Route path="/articles" component={Articles} />
		<Route path="/events" component={Events} />
		<Route path="/event/:id" component={Event} />
		<Route path="/article/:id" component={Article} />
	</Router>
), document.getElementById("app"));
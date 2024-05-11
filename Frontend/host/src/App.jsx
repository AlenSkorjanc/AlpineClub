import { render } from "solid-js/web";
import { Router, Route } from "@solidjs/router";
import { ErrorBoundary } from "solid-js";
import Bugsnag from '@bugsnag/js';

import Login from "login/Login";
import Articles from "articles/Articles";
import Article from "articles/Article";
import Events from "events_mf/Events";
import Event from "events_mf/Event";

import Header from "./Header";

import "./index.scss";

const App = props => {
	Bugsnag.start({
		apiKey: process.env.BUGSNAG_API_KEY,
		appVersion: process.env.APP_VERSION,
		releaseStage: process.env.RELEASE_STAGE
	})

	return (
		<ErrorBoundary fallback={(err) => {
			Bugsnag.notify(`${err.message}:\n${err.stack}`);

			if (process.env.RELEASE_STAGE === 'production') {
				return (
					<div class="flex justify-center items-center">
						<div class="bg-white shadow-md p-8 rounded-xl mt-20 max-w-md">
							<h1 class="flex justify-center items-center text-2xl font-bold mb-4">AlpClub</h1>
							<p class="error-message text-lg">Looks like our website is taking a quick nap. Don't worry, we've set up a wake-up call, and it should be up and running in no time! In the meantime, feel free to grab a virtual snack and come back soon! 🍪💤</p>
						</div>
					</div>
				);
			} else {
				return (
					<div class="p-4">
						<pre>{err.stack}</pre>
					</div>
				);
			}
		}}>
			<Header />
			{props.children}
		</ErrorBoundary>
	)
};

render(() => (
	<Router root={App}>
		<Route path="/" component={Login} />
		<Route path="/articles" component={Articles} />
		<Route path="/events" component={Events} />
		<Route path="/event/:id" component={Event} />
		<Route path="/article/:id" component={Article} />
	</Router>
), document.getElementById("app"));
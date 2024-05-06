import { render } from "solid-js/web";
import { createSignal, onMount } from "solid-js";

import Login from "remote/Login"
import Header from "./Header";

import "./index.scss";

const App = () => {
	const [showLogin, setShowLogin] = createSignal(false);
	const [loggedInName, setLoggedInName] = createSignal(null);

	onMount(() => {
		const user = null//getUser();
		console.log(user)
		if (user && user.name) {
			setShowLogin(false);
			setLoggedInName(user.name);
		}
	});

	const handleLoginClick = () => {
		console.log("login")
		setShowLogin(true);
	};

	const handleLogoutClick = () => {
		//setShowLogin(true);
		//setLoggedInName(null);
	//	Login.handleLogout();
	};

	const updateLoginName = (name) => {
		setLoggedInName(name);
	};

	return (
		<>
			<Header onLoginClick={handleLoginClick} onLogoutClick={handleLogoutClick} loggedInName={loggedInName}/>
			{showLogin() && (
				<div class="flex justify-center mt-20">
					<div class="w-96">
						<Login/>
					</div>
				</div>
			)}
		</>
	)
};

render(App, document.getElementById("app"));
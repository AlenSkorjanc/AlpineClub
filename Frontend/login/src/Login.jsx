import { createSignal, onMount } from "solid-js";
import axios from "axios";

import "./index.scss";

const loginUser = async (email, password) => {
	try {
		const response = await axios.post(`${process.env.GATEWAY_URL}/users/login`, {
			email,
			password,
		});

		if (response.status === 200) {
			return response.data;
		} else {
			return null;
		}
	} catch (error) {
		console.error("Error:", error);
		return null;
	}
};

const getUser = () => {
	const loggedInUser = sessionStorage.getItem('user');
	return loggedInUser ? JSON.parse(loggedInUser) : null;
};

const Login = () => {
	const [email, setEmail] = createSignal("");
	const [password, setPassword] = createSignal("");
	const [loggedInUser, setLoggedInUser] = createSignal(null);
	const [loginError, setLoginError] = createSignal(false);

	onMount(() => {
		const user = getUser();
		if (user) {
			setLoggedInUser(user);
			location.replace('articles');
		}
	});

	const handleLogin = async (e) => {
		e.preventDefault();
		const user = await loginUser(email(), password());
		if (user) {
			sessionStorage.setItem('user', JSON.stringify(user));
			location.replace('articles');
		} else {
			setLoginError(true);
		}
	};
	
	return (
		<div class="flex justify-center items-center">
			<div class="bg-white shadow-md p-5 rounded-xl mt-20 w-96">
				{!loggedInUser() ? (
					<form onSubmit={handleLogin} class="text-center mt-4">
						<h1 class="text-center text-gray-800 text-2xl">Login</h1>
						<div class="form-group mb-4">
							<label for="email" class="text-left block">Email:</label>
							<input type="email" id="email" value={email()} onInput={(e) => setEmail(e.target.value)} class="px-4 py-2 w-full border border-gray-300 rounded-md" />
						</div>
						<div class="form-group mb-4">
							<label for="password" class="text-left block">Password:</label>
							<input type="password" id="password" value={password()} onInput={(e) => setPassword(e.target.value)} class="px-4 py-2 w-full border border-gray-300 rounded-md" />
						</div>
						{loginError() && <p class="error-message text-red-500 mb-2">Login failed.<br />Please check your email and password.</p>}
						<button type="submit" class="bg-emerald-300 text-gray-700 px-4 py-2 rounded-md">Login</button>
					</form>
				) : <></>}
			</div>
		</div>
	);
};

export default Login;
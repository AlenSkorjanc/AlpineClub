import "./index.scss";

const Header = () => {
	const loggedInUser = sessionStorage.getItem('user');
	const userName = loggedInUser ? JSON.parse(loggedInUser).name : null;

	const logoutUser = () => {
		sessionStorage.removeItem('user');
		location.reload();
	}

	return (
		<header class="bg-gray-800 py-4">
			<div class="container mx-auto flex text-white justify-between items-center">
				<a href="/"><h1 class="text-xl font-bold">AlpClub</h1></a>
	
				<span class="flex items-center">
					<a href="/articles" class="px-4 py-2 w-full mr-4">Articles</a>
					<a href="/events" class="px-4 py-2 w-full">Events</a>
				</span>
	
				<div>
					{userName ? (
						<span class="flex items-center">
							<span class="mr-2">Hello {userName}!</span>
							<button onClick={logoutUser} class="px-4 py-2 border border-gray-300 rounded-md">Logout</button>
						</span>
					) : (
						<a href="/" class="px-4 py-2 w-full border border-gray-300 rounded-md">Login</a>
					)}
				</div>
			</div>
		</header>
	)
};

export default Header;
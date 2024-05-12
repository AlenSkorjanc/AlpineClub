import { createSignal, onMount } from "solid-js";
import axios from "axios";

import "./index.scss";

const callApi = async () => {
	try {
		const response = await axios.get(`${process.env.SERVICE_URL}/endpoint`);

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

const Example = () => {
	const [data, setData] = createSignal("Default data");

	onMount(() => {
		const data = callApi();
		if (data) {
            setData(data);
		}
	});

	return (
		<div class="flex justify-center items-center">
			<div class="bg-slate-400 shadow-md p-5 rounded-xl mt-20 w-96">
				{data() ? (
					<div class="text-center">
						{data()}<br/>
						<button class="bg-red-300 text-gray-700 px-4 py-2 rounded-md mt-4" onClick={() => {setData(null)}}>Clear data</button>
					</div>
				) : <div class="text-center">
						No data<br/>
						<button class="bg-emerald-300 text-gray-700 px-4 py-2 rounded-md mt-4" onClick={() => {setData("New data")}}>Add data</button>
					</div>}
			</div>
		</div>
	);
};

export default Example;
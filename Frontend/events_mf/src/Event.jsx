import { onMount, createSignal } from "solid-js";
import { useParams } from "@solidjs/router";

import "./index.scss";
import axios from "axios";

const getEvent = async (id) => {
    try {
        const response = await axios.get(`${process.env.GATEWAY_URL}/${id}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching articles:", error);
        throw error;
    }
};

const Event = () => {
    const [event, setEvent] = createSignal(null);
    const params = useParams();

    onMount(async () => {
        const event = await getEvent(params.id);
        if (event) {
            setEvent(event);
        }
    });

    const formatDate = (dateEpoch) => {
        const date = new Date(dateEpoch * 1000);
        return `${date.getDate()}. ${date.getMonth() + 1}. ${date.getFullYear()}`
    }

    return (
        <div class="max-w-4xl mx-auto pt-20">
            {event() ? (
                <div class="bg-white shadow-md rounded-lg overflow-hidden">
                    <div class="px-6 py-4">
                        <span class="inline-block bg-emerald-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700">
                            Start: {formatDate(event().start)}
                        </span>
                        {event().end && event().end !== 0 ?
                            <span class="ml-2 inline-block bg-red-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700">
                                End: {formatDate(event().end)}
                            </span>
                            : <></>}
                    </div>
                    <div class="px-6 py-4">
                        <h2 class="font-bold text-3xl mb-2">{event().name}</h2>
                        <p class="text-gray-700 text-base">{event().description}</p>
                    </div>
                </div>
            ) : <></>}
        </div>
    );
}

export default Event;
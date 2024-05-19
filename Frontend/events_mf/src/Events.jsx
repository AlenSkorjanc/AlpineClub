import { For, onMount, createSignal } from "solid-js"

import "./index.scss";
import axios from "axios";

const getEvents = async () => {
    try {
        const response = await axios.get(`${process.env.GATEWAY_URL}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching articles:", error);
        throw error;
    }
};

const Events = () => {
    const [events, setEvents] = createSignal(null);

    onMount(async () => {
        const events = await getEvents();
        if (events) {
            setEvents(events);
        }
    });

    const formatDate = (dateEpoch) => {
        const date = new Date(dateEpoch);
        return `${date.getDate()}. ${date.getMonth() + 1}. ${date.getFullYear()}`
    }

    return (
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4 max-w-4xl mx-auto mt-10 mb-10">
            <For each={events()}>
                {(event) => {
                    return (
                        <a href={`/event/${event.id}`} class="bg-white shadow-md rounded-lg overflow-hidden">
                            <div class="px-6 py-4">
                                <h2 class="font-bold text-xl mb-2">{event.name}</h2>
                                <p class="text-gray-700 text-base">{event.description}</p>
                            </div>
                            <div class="px-6 py-4">
                                <span class="inline-block bg-emerald-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700">
                                    Start: {formatDate(event.start)}
                                </span>
                                {event.end && event.end !== 0 ?
                                    <span class="ml-2 inline-block bg-red-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700">
                                        End: {formatDate(event.end)}
                                    </span>
                                    : <></>}
                            </div>
                        </a>
                    );
                }}
            </For>
        </div>
    );
}

export default Events;
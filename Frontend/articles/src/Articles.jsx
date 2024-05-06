import { For, onMount, createSignal } from "solid-js"

import "./index.scss";
import axios from "axios";

const getArticles = async () => {
    try {
        const response = await axios.get(`${process.env.GATEWAY_URL}/articles`);
        return response.data;
    } catch (error) {
        console.error("Error fetching articles:", error);
        throw error;
    }
};

const Articles = () => {
    const [articles, setArticles] = createSignal(null);

    onMount(async () => {
        const articles = await getArticles();
        if (articles) {
            setArticles(articles);
        }
    });

    const formatDate = (dateStr) => {
        const date = new Date(dateStr);
        return `${date.getDate()}. ${date.getMonth() + 1}. ${date.getFullYear()}`
    }

    return (
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4 max-w-4xl mx-auto mt-10 mb-10">
            <For each={articles()}>
                {(article) => {
                    return (
                        <a href={`/article/${article.id}`} class="bg-white shadow-md rounded-lg overflow-hidden">
                            <div class="px-6 py-4">
                                <h2 class="font-bold text-xl mb-2">{article.title}</h2>
                                <p class="text-gray-700 text-base">{article.summary}</p>
                            </div>
                            <div class="px-6 py-4 flex justify-between items-center">
                                <span class="inline-block bg-gray-300 rounded-full px-3 py-1 text-sm font-semibold text-gray-700">
                                    Published: {formatDate(article.created)}
                                </span>
                                <span class="inline-block bg-gray-300 rounded-full px-3 py-1 text-sm font-semibold text-gray-700">
                                    Views: {article.views}
                                </span>
                            </div>
                        </a>
                    );
                }}
            </For>
        </div>
    );
}

export default Articles;
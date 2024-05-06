import { onMount, createSignal } from "solid-js";
import { useParams } from "@solidjs/router";

import "./index.scss";
import axios from "axios";

const updateArticle = async (article) => {
    try {
        const response = await axios.put(`${process.env.GATEWAY_URL}/articles`, article);
        return response.data;
    } catch (error) {
        console.error("Error updating article view count:", error);
        throw error;
    }
};

const getArticle = async (id) => {
    try {
        const response = await axios.get(`${process.env.GATEWAY_URL}/articles/${id}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching articles:", error);
        throw error;
    }
};

const Article = () => {
    const [article, setArticle] = createSignal(null);
    const params = useParams();

    onMount(async () => {
        const article = await getArticle(params.id);
        if (article) {
            article.views += 1;
            setArticle(article);
            updateArticle(article);
        }
    });

    const formatDate = (dateStr) => {
        const date = new Date(dateStr);
        return `${date.getDate()}. ${date.getMonth() + 1}. ${date.getFullYear()}`
    }

    return (
        <div class="max-w-4xl mx-auto pt-20">
            {article() ? (
                <div class="bg-white shadow-md rounded-lg overflow-hidden">
                    <div class="px-6 py-4 flex justify-between items-center">
                        <span class="inline-block bg-gray-300 rounded-full px-3 py-1 text-sm font-semibold text-gray-700">
                            Published: {formatDate(article().created)}
                        </span>
                        <span class="inline-block bg-gray-300 rounded-full px-3 py-1 text-sm font-semibold text-gray-700">
                            Views: {article().views}
                        </span>
                    </div>
                    <div class="px-6 py-4">
                        <h2 class="font-bold text-3xl mb-2">{article().title}</h2>
                        <p class="text-gray-700 text-base">{article().body}</p>
                    </div>
                </div>
            ) : <div>Loading...</div>}
        </div>
    );
}

export default Article;
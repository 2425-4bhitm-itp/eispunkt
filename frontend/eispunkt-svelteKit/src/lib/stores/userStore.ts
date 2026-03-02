import { writable } from "svelte/store";

export const user = writable<{
	team: any | null;
	authenticated: boolean;
    role: string | null;
}>({
	team: null,
	authenticated: false,
    role: null
});
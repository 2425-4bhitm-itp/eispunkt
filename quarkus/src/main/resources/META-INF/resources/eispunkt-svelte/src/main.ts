import { mount } from 'svelte'
import './app.css'
import App from './App.svelte'
import ChooseTeam from "./lib/ChooseTeam.svelte";

const app = mount(ChooseTeam, {
  target: document.getElementById('app')!,
})

export default app

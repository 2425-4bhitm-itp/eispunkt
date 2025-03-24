import {Score} from "./interfaces/score";
import {Stage} from "./interfaces/stage";
import {Team} from "./interfaces/team";
import {Player} from "./interfaces/player";
import {Game} from "./interfaces/game";
import {Observable} from "./observable";


interface Model{
    games: Game[];
    players: Player[];
    teams: Team[];
    stages: Stage[];
    scores: Score[];
}

const state: Model = {
    games: [],
    players: [],
    teams: [],
    stages: [],
    scores: []
}

const store = new Observable<Model>(state)
export {store};
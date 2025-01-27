import {Score} from "./objects/score";
import {Stage} from "./objects/stage";
import {Team} from "./objects/team";
import {Player} from "./objects/player";
import {Game} from "./objects/game";
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
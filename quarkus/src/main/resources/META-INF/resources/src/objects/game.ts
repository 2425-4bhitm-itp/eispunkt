import {Team} from "./team";
import {Stage} from "./stage";

export interface Game{
    readonly gameId: number;
    readonly gameDate: Date;
    readonly teams: Team[];
    readonly stages: Stage[];
}
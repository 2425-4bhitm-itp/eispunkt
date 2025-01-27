import {Team} from "./team";
import {Turn} from "./turn";

export interface Score{
    readonly scoreId: number;
    readonly team: Team;
    readonly turn: Turn;
    readonly score: number;
}
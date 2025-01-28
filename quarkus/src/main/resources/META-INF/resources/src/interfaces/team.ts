import {Player} from "./player";

export interface Team {
    readonly teamId:number;
    readonly name:String;
    readonly players: Player[]
}
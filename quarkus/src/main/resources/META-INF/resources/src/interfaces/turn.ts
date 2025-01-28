import {Stage} from "./stage";
import {Score} from "./score";

export interface Turn{
    readonly turnId: number;
    readonly scores: Score[];
    readonly stage: Stage;
}
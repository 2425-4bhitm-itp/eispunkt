import {Turn} from "./turn";

export interface Stage{
    readonly stageId: number;
    readonly turns: Turn[];
}
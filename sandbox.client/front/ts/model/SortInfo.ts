export class SortInfo{
    type: SortType;
    direction: number = 0;
}

export enum SortType{
    AGE, TOTAL_SCORE, MAX_SCORE, MIN_SCORE, NON
}
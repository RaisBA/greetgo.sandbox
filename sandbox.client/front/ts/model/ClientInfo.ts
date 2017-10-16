export class ClientInfo {
    public id : string;
    public fullName : string;
    public charm : string;
    public age : number;
    public totalScore : number;
    public maxScore : number;
    public minScore : number;


    public assign(o: ClientInfo): ClientInfo {
        this.id = o.id;
        this.fullName = o.fullName;
        this.charm = o.charm;
        this.age = o.age;
        this.totalScore = o.totalScore;
        this.maxScore = o.maxScore;
        this.minScore = o.minScore;

        return this;
    }
}

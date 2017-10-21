export class PhoneInfo{
    public id: string;
    public num : string;
    public type : string;


    public assign (o : PhoneInfo): PhoneInfo {
        this.id = o.id;
        this.num = o.num;
        this.type = o.type;

        return this;
    }

}
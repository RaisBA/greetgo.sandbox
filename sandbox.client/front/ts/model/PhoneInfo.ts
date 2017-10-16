export class PhoneInfo{
    public num : string;
    public type : string;


    public assign (o : PhoneInfo): PhoneInfo {
        this.num = o.num;
        this.type = o.type;

        return this;
    }

}
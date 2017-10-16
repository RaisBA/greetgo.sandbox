export class AddressInfo{
    public street : string;
    public house : string;
    public flat : string;


    public assign(o : AddressInfo) : AddressInfo{
        this.street = o.street;
        this.house = o.house;
        this.flat = o.flat;

        return this;
    }

}
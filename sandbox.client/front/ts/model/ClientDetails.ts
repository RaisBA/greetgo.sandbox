import {AddressInfo} from "./AddressInfo";
import {PhoneInfo} from "./PhoneInfo";
export class ClientDetails {
    public id : string;
    public name : string;
    public surname : string;
    public patronymic : string;
    public gender : string;
    public charm : string;
    public birthDate : Date;
    public regAddress : AddressInfo;
    public factAddress : AddressInfo;
    public phones : Array<PhoneInfo>;


    public assign(o: ClientDetails): ClientDetails {
        this.id = o.id;
        this.name = o.name;
        this.surname = o.surname;
        this.patronymic = o.patronymic;
        this.gender = o.gender;
        this.charm = o.charm;
        this.birthDate = o.birthDate;
        this.regAddress = o.regAddress;
        this.factAddress = o.factAddress;
        this.phones = o.phones;

        return this;
    }
}

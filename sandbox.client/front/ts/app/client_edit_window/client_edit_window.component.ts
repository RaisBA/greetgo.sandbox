import {Component, EventEmitter, Output} from "@angular/core";
import {ClientDetails} from "../../model/ClientDetails";
import {Directory} from "../../model/Directory";
import {AddressInfo} from "../../model/AddressInfo";
import {PhoneInfo} from "../../model/PhoneInfo";


@Component({
    selector: 'client-edit-window',
    styles: [require('./client_edit_window.component.css')],
    template: require('./client_edit_window.component.html'),
})
export class ClientEditWindowComponent{
    showWindow: boolean = false;
    canEdit: boolean = false;
    isNew: boolean = true;
    client: ClientDetails = new ClientDetails();

    charms: Array<Directory> = [];
    genders: Array<Directory> = [];
    phoneTypes: Array<Directory> = [];

    @Output() newPhone = new EventEmitter<any>();
    @Output() deletePhoneOut = new EventEmitter<any>();
    @Output() save = new EventEmitter<any>();

    closeEditWindow() {
        this.showWindow = false;
        this.client = new ClientDetails();
    }

    saveEdit(clientInfo, addresses, phoneList){
        let result = new ClientDetails();
        result.id = this.client.id;
        result.birthDate = clientInfo["birthday"];
        result.name = clientInfo["name"];
        result.surname = clientInfo["surname"];
        result.patronymic = clientInfo["patronymic"];
        result.charm = (<HTMLInputElement>document.getElementsByName("charm")[0]).value;
        result.gender = (<HTMLInputElement>document.getElementsByName("gender")[0]).value;


        result.regAddress = new AddressInfo();
        result.regAddress.street = addresses["regStreet"];
        result.regAddress.house  = addresses["regHouse"];
        result.regAddress.flat = addresses["regFlat"];

        result.factAddress = new AddressInfo();
        result.factAddress.street = addresses["factStreet"];
        result.factAddress.house = addresses["factHouse"];
        result.factAddress.flat = addresses["factFlat"];

        result.phones = [];

        for (let phoneId of Object.keys(phoneList)){
            let phone = new PhoneInfo();
            phone.id = phoneId;
            phone.num = phoneList[phoneId];
            phone.type = (<HTMLInputElement>document.getElementById("selectPhone" + phoneId)).value;
            result.phones.push(phone);
        }

        this.save.emit(result);
    }// end saveEdit

    addNewPhone(num, type){
        this.newPhone.emit({"clientID":this.client.id, "num":num, "type":type});
        (<HTMLInputElement>document.getElementsByName("newPhoneNum")[0]).value = '';
    }

    deletePhone(phone){
        let q = window.confirm("Удалить телефон?");
        if (q){
            this.deletePhoneOut.emit({id:phone["id"], num:phone["num"], clientId:this.client.id});
        }
    }
}
import {Component, EventEmitter, Output} from "@angular/core";
import {ClientDetails} from "../../model/ClientDetails";
import {Directory} from "../../model/Directory";
import {AddressInfo} from "../../model/AddressInfo";


@Component({
    selector: 'client-edit-window',
    styles: [require('./client_edit_window.component.css')],
    template: require('./client_edit_window.component.html'),
})
export class ClientEditWindowComponent{
    showWindow: boolean = false;
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
        this.client.name = clientInfo["name"];
        this.client.surname = clientInfo["surname"];
        this.client.patronymic = clientInfo["patronymic"];
        this.client.gender = clientInfo["gender"];
        this.client.charm = clientInfo["charm"];

        if (!this.client.regAddress){
            this.client.regAddress = new AddressInfo();
        }
        this.client.regAddress.street = addresses["regStreet"];
        this.client.regAddress.house  = addresses["regHouse"];
        this.client.regAddress.flat = addresses["regFlat"];

        if (!this.client.factAddress){
            this.client.factAddress = new AddressInfo();
        }
        this.client.factAddress.street = addresses["factStreet"];
        this.client.factAddress.house = addresses["factHouse"];
        this.client.factAddress.flat = addresses["factFlat"];

        if (!this.client.phones){
            this.client.phones = [];
        }
        for (let phoneId of Object.keys(phoneList)){
            //TODO
        }

    }

    addNewPhone(num, type){
        this.newPhone.emit({"num":num, "type":type});
    }

    deletePhone(phone){
        console.log("delete phone" + phone["id"] + "    " + phone["num"] + "  " + phone["type"]);
        if (!this.isNew){
            this.deletePhoneOut.emit(phone);
        }
    }

    ediPhone(phone){
        console.log(phone);
    }
}
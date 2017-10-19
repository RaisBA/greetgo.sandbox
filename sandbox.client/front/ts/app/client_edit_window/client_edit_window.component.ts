import {Component} from "@angular/core";
import {ClientDetails} from "../../model/ClientDetails";
import {Directory} from "../../model/Directory";


@Component({
    selector: 'client-edit-window',
    styles: [require('./client_edit_window.component.css')],
    template: require('./client_edit_window.component.html'),
})
export class ClientEditWindowComponent {
    showWindow: boolean = false;
    isNew: boolean = true;
    client: ClientDetails = new ClientDetails();


    charms: Array<Directory> = [];
    genders: Array<Directory> = [];
    phoneTypes: Array<Directory> = [];

    closeEditWindow() {
        this.showWindow = false;
    }
}
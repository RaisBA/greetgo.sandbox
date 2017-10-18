import {Component, EventEmitter, OnInit, Output} from "@angular/core";
import {HttpService} from "../HttpService";
import {ClientInfo} from "../../model/ClientInfo";
import {ClientDetails} from "../../model/ClientDetails";
import {Directory} from "../../model/Directory";


@Component({
    selector:'client-edit-window',
    styles:[require('./client_edit_window.component.css')],
    template:`
        <div *ngIf="showWindow" class="editWindow">
            <div class="content">
                <label class="close" (click)="closeEditWindow()"> &#215; </label>
                <div class="form">
                    <div class="client">
                        <div class="title">
                                   <label *ngIf="isNew">Добавление</label>                 
                                   <label *ngIf="!isNew">Редактирование</label>                 
                        </div>
                        <div>  
                            <input type="text" tabindex="2" class="client-info"
                                placeholder="Фамилия" required
                                ngModel="{{client ? client.surname : ''}}">
                            <input type="text" tabindex="2" class="client-info"
                                placeholder="Имя" required
                                ngModel="{{client ? client.name : ''}}">
                            <input type="text" tabindex="2" class="client-info"
                                placeholder="Отчество" required
                                ngModel="{{client ? client.patronymic : ''}}">
                                
                            <select *ngIf="charms" required title="Характер" class="client-info">
                                <option *ngFor="let charm of charms" value="{{charm.code}}"
                                    [selected]="charm.code == client.charm">
                                    {{charm.name}}
                                </option>
                            </select>
                            <select *ngIf="genders" required title="Пол" class="client-info">
                                <option *ngFor="let gender of genders" value="{{gender.code}}"
                                    [selected]="gender.code == client.gender">
                                    {{gender.name}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `,
})
export class ClientEditWindowComponent{
    showWindow: boolean = false;
    isNew : boolean = true;
    client : ClientDetails = new ClientDetails();

    charms: Array<Directory> = [];
    genders: Array<Directory> = [];

    closeEditWindow(){
        this.showWindow = false;
    }
}
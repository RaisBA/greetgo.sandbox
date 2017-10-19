import {Component, EventEmitter, OnInit, Output, ViewChild} from "@angular/core";
import {HttpService} from "../HttpService";
import {ClientInfo} from "../../model/ClientInfo";
import {ClientEditWindowComponent} from "../client_edit_window/client_edit_window.component";
import {ClientDetails} from "../../model/ClientDetails";
import {error} from "util";
@Component({
    selector: 'client-list-component',
    styles: [require('./client_list.component.css')],
    template: `
    <div>
        <div>
            &nbsp;
        </div>
        <div align="center">
        <table>
            <tr class="tbl-head">
                <th><label>ФИО</label></th>
                <th><label>Характер</label></th>
                <th (click)="changeSort('AGE')">
                    <div name="ageSort">Возраст
                        <label *ngIf="ageSortFlag == 1">&#8595;</label>
                        <label *ngIf="ageSortFlag == -1">&#8593;</label>
                        <label *ngIf="ageSortFlag == 0">&nbsp;&#183;</label>
                    </div>
                </th>
                <th (click)="changeSort('TOTAL_SCORE')">Общий остаток счета
                    <label *ngIf="totalScoreSortFlag == 1">&#8595;</label>
                    <label *ngIf="totalScoreSortFlag == -1">&#8593;</label>
                    <label *ngIf="totalScoreSortFlag == 0">&nbsp;&#183;</label>
                </th>
                <th (click)="changeSort('MAX_SCORE')">Максимальный счет
                    <label *ngIf="maxScoreSortFlag == 1">&#8595;</label>
                    <label *ngIf="maxScoreSortFlag == -1">&#8593;</label>
                    <label *ngIf="maxScoreSortFlag == 0">&nbsp;&#183;</label>
                </th>
                <th (click)="changeSort('MIN_SCORE')">Минимальный счет
                    <label *ngIf="minScoreSortFlag == 1">&#8595;</label>  
                    <label *ngIf="minScoreSortFlag == -1">&#8593;</label>
                    <label *ngIf="minScoreSortFlag == 0">&nbsp;&#183;</label>
                </th>
                <th>&nbsp;</th>
                <th>&nbsp;</th>
                <th>&nbsp;</th>
                <th class="button-th">
                    <label (click)="addNewClient()">| + |</label>
                </th>
            </tr>
            <tr class="client-row" *ngFor="let client of clients">
                <td class="string-td">{{client.fullName}}</td>
                <td class="string-td">{{client.charm}}</td>
                <td class="number-td">{{client.age}}</td>
                <td class="number-td">{{client.totalScore}}</td>
                <td class="number-td">{{client.maxScore}}</td>
                <td class="number-td">{{client.minScore}}</td>
                <td></td>
                <td class="button-td">
                    <label (click)="editClient(client.id)">|...|</label>
                </td>
                <td class="button-td">
                    <label (click)="deleteClient(client.id)">| - |</label>
                </td>
            </tr>
        <client-edit-window #clientEditWindowComponent></client-edit-window>
        </table>
        </div>
    <div>
    `,
})
export class ClientListComponent  implements OnInit{
    @Output() exit = new EventEmitter<void>();

    clients: Array<ClientInfo> | null = [];
    canEdit: boolean = true;
    loadUserInfoError: string | null;
    ageSortFlag: number = 0;
    totalScoreSortFlag: number = 0;
    maxScoreSortFlag: number = 0;
    minScoreSortFlag: number = 0;

    @ViewChild('clientEditWindowComponent') clientEditWindowComponent: ClientEditWindowComponent;

    constructor(private httpService: HttpService) {
    }

    ngOnInit(): void {
        this.getClientList();
    }

    changeSort(type: string) {
        switch (type) {
            case "AGE":
                this.totalScoreSortFlag = 0;
                this.maxScoreSortFlag = 0;
                this.minScoreSortFlag = 0;
                if (this.ageSortFlag == 0) {
                    this.ageSortFlag = 1;
                }
                else {
                    this.ageSortFlag *= -1;
                }
                break;
            case  "TOTAL_SCORE":
                this.ageSortFlag = 0;
                this.maxScoreSortFlag = 0;
                this.minScoreSortFlag = 0;
                if (this.totalScoreSortFlag == 0) {
                    this.totalScoreSortFlag = 1;
                }
                else {
                    this.totalScoreSortFlag *= -1;
                }
                break;
            case "MAX_SCORE":
                this.ageSortFlag = 0;
                this.totalScoreSortFlag = 0;
                this.minScoreSortFlag = 0;
                if (this.maxScoreSortFlag == 0) {
                    this.maxScoreSortFlag = 1;
                }
                else {
                    this.maxScoreSortFlag *= -1;
                }
                break;
            case "MIN_SCORE":
                this.ageSortFlag = 0;
                this.totalScoreSortFlag = 0;
                this.maxScoreSortFlag = 0;
                if (this.minScoreSortFlag == 0) {
                    this.minScoreSortFlag = 1;
                }
                else {
                    this.minScoreSortFlag *= -1;
                }
                break;
        }
    }// end changeSort

    editClient(id){
        this.clientEditWindowComponent.isNew = false;
        this.getClientDetails(id);
        this.getGenders();
        this.getCharms();
        this.getPhoneTypes();
        this.clientEditWindowComponent.showWindow = true;
        console.log("edit " + id);
    }

    deleteClient(id){
        console.log("delete " + id);
    }

    addNewClient(){
        this.clientEditWindowComponent.isNew = true;
        this.getGenders();
        this.getCharms();
        this.getPhoneTypes();
        this.clientEditWindowComponent.showWindow = true;
        console.log("add new client");
    }

    getClientList(){
        this.httpService.get("/client/list").toPromise().then(res => {
                this.clients = res.json();
            },
            error => {
                console.log(error);
            }
        );
    }

    getClientDetails(id){
        this.httpService.get("/client/details", {clientId:id}).toPromise().then(res => {
                this.clientEditWindowComponent.client = new ClientDetails().assign(res.json() as ClientDetails);
            }, error => {
                console.log("/client/details" + error);
            }

        );
    }



    getGenders(){
        this.httpService.get("/client/genders").toPromise().then(res => {
                this.clientEditWindowComponent.genders = res.json();
            }, error => {
                console.log("/client/genders" + error);
            }

        );
    }
    getCharms(){
        this.httpService.get("/client/charms").toPromise().then(res => {
                this.clientEditWindowComponent.charms = res.json();
            }, error => {
                console.log("/client/charms" + error);
            }

        );
    }
    getPhoneTypes(){
        this.httpService.get("/client/phoneTypes").toPromise().then(res => {
                this.clientEditWindowComponent.phoneTypes = res.json();
            }, error => {
                console.log("/client/phoneTypes" + error);
            }

        );
    }
}

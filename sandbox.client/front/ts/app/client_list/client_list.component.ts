import {Component, EventEmitter, OnInit, Output, ViewChild} from "@angular/core";
import {HttpService} from "../HttpService";
import {ClientInfo} from "../../model/ClientInfo";
import {ClientEditWindowComponent} from "../client_edit_window/client_edit_window.component";
import {ClientDetails} from "../../model/ClientDetails";
import {PhoneInfo} from "../../model/PhoneInfo";
import {SortType} from "../../model/SortInfo";

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
                        <label *ngIf="sortFlag == 1 && sortT == 1">&#8595;</label>
                        <label *ngIf="sortFlag == -1 && sortT == 1">&#8593;</label>
                        <label *ngIf="sortT != 1">&nbsp;&#183;</label>
                    </div>
                </th>
                <th (click)="changeSort('TOTAL_SCORE')">Общий остаток счета
                    <label *ngIf="sortFlag == 1 && sortT == 2">&#8595;</label>
                    <label *ngIf="sortFlag == -1 && sortT == 2">&#8593;</label>
                    <label *ngIf="sortT != 2">&nbsp;&#183;</label>
                </th>
                <th (click)="changeSort('MAX_SCORE')">Максимальный счет
                    <label *ngIf="sortFlag == 1 && sortT == 3">&#8595;</label>
                    <label *ngIf="sortFlag == -1 && sortT == 3">&#8593;</label>
                    <label *ngIf="sortT != 3">&nbsp;&#183;</label>
                </th>
                <th (click)="changeSort('MIN_SCORE')">Минимальный счет
                    <label *ngIf="sortFlag == 1 && sortT == 4">&#8595;</label>  
                    <label *ngIf="sortFlag == -1 && sortT == 4">&#8593;</label>
                    <label *ngIf="sortT != 4">&nbsp;&#183;</label>
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
            <client-edit-window #clientEditWindowComponent
                (newPhone)="addNewPhone($event)"
                (deletePhoneOut)="deletePhone($event)"
                (save)="saveClientEdit($event)"
                ></client-edit-window>
            </table>
            <div align="center" style="width: 25%">
                <ul class="pager">
                    <li class="previous"><a href="#" (click)="previousPage()">Назад</a></li>
                    <li>
                        <a style="font-size: small" href="#" *ngFor="let page of pages" id="pageNum{{page}}"
                            [ngClass]="{'active':page==selectPage}" (click)="changePage(page)">{{page}}</a>
                    </li>
                    <li class="next"><a href="#" (click)="nextPage()">Вперед</a></li>
                </ul>
            </div>
        </div>      
    <div>
    `,
})
export class ClientListComponent implements OnInit {
    @Output() exit = new EventEmitter<void>();

    pages: Array<any> = [1];
    selectPage: number = 1;
    clients: Array<ClientInfo> | null = [];
    sortT: SortType = SortType.NON;
    canEdit: boolean = true;
    loadUserInfoError: string | null;
    sortFlag: number = 0;

    @ViewChild('clientEditWindowComponent') clientEditWindowComponent: ClientEditWindowComponent;


    constructor(private httpService: HttpService) {
    }

    ngOnInit(): void {
        this.changePage(1);
        this.getClientList();
    }

    changeSort(type: string) {
        if (!this.sortFlag) {
            this.sortFlag = 1;
        }
        switch (type) {
            case "AGE":
                if (this.sortT != SortType.AGE) {
                    this.sortT = SortType.AGE;
                    this.sortFlag = 1;
                } else {
                    this.sortFlag *= -1;
                }
                this.getClientList();
                break;
            case  "TOTAL_SCORE":
                if (this.sortT != SortType.TOTAL_SCORE) {
                    this.sortT = SortType.TOTAL_SCORE;
                    this.sortFlag = 1;
                } else {
                    this.sortFlag *= -1;
                }
                this.getClientList();
                break;
            case "MAX_SCORE":
                if (this.sortT != SortType.MAX_SCORE) {
                    this.sortT = SortType.MAX_SCORE;
                    this.sortFlag = 1;
                } else {
                    this.sortFlag *= -1;
                }
                this.getClientList();
                break;
            case "MIN_SCORE":
                if (this.sortT != SortType.MIN_SCORE) {
                    this.sortT = SortType.MIN_SCORE;
                    this.sortFlag = 1;
                } else {
                    this.sortFlag *= -1;
                }
                this.getClientList();
                break;
        }
    }// end changeSort

    editClient(id) {
        this.clientEditWindowComponent.isNew = false;
        this.getClientDetails(id);
        this.getGenders();
        this.getCharms();
        this.getPhoneTypes();
        this.clientEditWindowComponent.showWindow = true;
        console.log("edit " + id);
    }

    deleteClient(id) {
        let q = window.confirm("Удалить клиента?");
        if (q) {
            this.httpService.get("/client/delete", {clientId: id}).toPromise().then(res => {
                    window.alert("Клиент удален");
                    this.getClientList();
                }, error => {
                    console.log(error);
                }
            );
        }
    }

    addNewClient() {
        this.clientEditWindowComponent.isNew = true;
        this.getClientDetails(null)
        this.getGenders();
        this.getCharms();
        this.getPhoneTypes();
        this.clientEditWindowComponent.showWindow = true;
        console.log("add new client");
    }

    getClientList() {
        console.log({'sort': this.sortT, 'direction': this.sortFlag, 'pageNum': this.selectPage});
        this.httpService.get("/client/list", {
            'sort': this.sortT,
            'direction': this.sortFlag,
            'pageNum': this.selectPage
        }).toPromise().then(res => {
                this.clients = res.json();
            },
            error => {
                console.log(error);
            }
        );
    }

    getClientDetails(id) {
        this.httpService.get("/client/details", {clientId: id}).toPromise().then(res => {
                this.clientEditWindowComponent.client = new ClientDetails().assign(res.json() as ClientDetails);
            }, error => {
                console.log("/client/details" + error);
            }
        );
    }


    getGenders() {
        this.httpService.get("/client/genders").toPromise().then(res => {
                this.clientEditWindowComponent.genders = res.json();
            }, error => {
                console.log("/client/genders" + error);
            }
        );
    }

    getCharms() {
        this.httpService.get("/client/charms").toPromise().then(res => {
                this.clientEditWindowComponent.charms = res.json();
            }, error => {
                console.log("/client/charms" + error);
            }
        );
    }

    getPhoneTypes() {
        this.httpService.get("/client/phoneTypes").toPromise().then(res => {
                this.clientEditWindowComponent.phoneTypes = res.json();
            }, error => {
                console.log("/client/phoneTypes" + error);
            }
        );
    }

    addNewPhone(event) {
        this.httpService.get("/client/newPhone", event).toPromise().then(res => {
            if (!this.clientEditWindowComponent.client.phones) {
                this.clientEditWindowComponent.client.phones = [];
            }
            if (res != null) {
                this.clientEditWindowComponent.client.phones.push(new PhoneInfo().assign(res.json()));
            } else {
                window.alert("Такой телефон уже есть!");
            }
            console.log(res.json());
        }, error => {
            console.log("ERROR ADD NEW PHONE" + error);
        });
    }

    saveClientEdit(event) {
        this.clientEditWindowComponent.canEdit = false;
        this.httpService.post("/client/save", {client: JSON.stringify(event)}).toPromise().then(res => {
            this.clientEditWindowComponent.showWindow = false;
            this.clientEditWindowComponent.client = new ClientDetails();
            this.getClientList();
        }, error => {
            console.log(error);
        });
        this.clientEditWindowComponent.canEdit = true;
    }

    deletePhone(event) {
        this.clientEditWindowComponent.canEdit = false;
        this.httpService.post("/client/deletePhone", event).toPromise().then(res => {
            this.clientEditWindowComponent.client.phones = res.json();
        }, error => {
            console.log(error);
        });
        this.clientEditWindowComponent.canEdit = true;
    }

    changePage(page) {
        if (page.toString() == '...') {
            return;
        }
        this.httpService.get("/client/pages", {'page': page}).toPromise().then(
            res => {
                console.log(res.json());
                this.selectPage = res.json()["page"];
                this.pages = res.json()["pagesList"];
                this.getClientList();
            },
            error => {
                console.log(error);
            }
        );
    }

    previousPage() {
        if (this.selectPage > 1) {
            this.changePage(this.selectPage - 1);
        }
    }

    nextPage() {
        this.changePage(this.selectPage + 1);
    }
}

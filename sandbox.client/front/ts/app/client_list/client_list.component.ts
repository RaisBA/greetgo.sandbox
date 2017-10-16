import {Component, EventEmitter, Output} from "@angular/core";
import {UserInfo} from "../../model/UserInfo";
import {HttpService} from "../HttpService";
import {ClientInfo} from "../../model/ClientInfo";
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
            <tr>
                <th>ФИО</th>
                <th>Характер</th>
                <th>Возраст</th>
                <th>Общий остаток счета</th>
                <th>Максимальный счет</th>
                <th>Минимальный счет</th>
            </tr>
            <tr *ngFor="let client of clients">
                <td>{{client.fullName}}</td>
                <td>{{client.charm}}</td>
                <td>{{client.age}}</td>
                <td>{{client.totalScore}}</td>
                <td>{{client.maxScore}</td>
                <td>{{client.minScore}</td>
            </tr>
        </table>
        </div>
    <div>
    `,
})
export class ClientListComponent {
  @Output() exit = new EventEmitter<void>();

  clients: Array<ClientInfo> | null = [];
  canEdit: boolean = true;
  loadUserInfoError: string | null;

  constructor(private httpService: HttpService) {}

  ngOnInit() : void{
    this.httpService.get("/client/list").toPromise().then(res => {
        let c1 = new ClientInfo();
        c1.fullName = "Иванов Иван Иванович";
        c1.age = 12;
        c1.charm = "Спокойный";
        c1.totalScore = 12000;
        c1.maxScore = 11000;
        c1.minScore = 100;
        this.clients.push(c1);
        console.log(c1);
      },
        error => {
      console.log(error);
      }
    );
  }


}

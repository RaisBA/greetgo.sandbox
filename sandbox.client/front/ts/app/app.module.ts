import {NgModule} from "@angular/core";
import {HttpModule, JsonpModule} from "@angular/http";
import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {RootComponent} from "./root.component";
import {LoginComponent} from "./input/login.component";
import {MainFormComponent} from "./main_form/main_form.component";
import {HttpService} from "./HttpService";
import {ClientListComponent} from "./client_list/client_list.component";
import {ClientEditWindowComponent} from "./client_edit_window/client_edit_window.component";

@NgModule({
  imports: [
    BrowserModule, HttpModule, JsonpModule, FormsModule
  ],
  declarations: [
    RootComponent, LoginComponent, MainFormComponent, ClientListComponent, ClientEditWindowComponent
  ],
  bootstrap: [RootComponent],
  providers: [HttpService],
  entryComponents: [],
})
export class AppModule {
}
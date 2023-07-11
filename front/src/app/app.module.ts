import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {StyleClassModule} from 'primeng/styleclass';
import {CheckboxModule} from 'primeng/checkbox';
import {ButtonModule} from 'primeng/button';
import {TableModule} from "primeng/table";
import {InputTextModule} from 'primeng/inputtext';
import { RegComponent } from './reg/reg.component';
import { LogComponent } from './log/log.component';
import { MainComponent } from './main/main.component';
import {DropdownModule} from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import {SliderModule} from 'primeng/slider';
import {HttpClientModule} from "@angular/common/http";
import {MainCanvasService} from "./services/main-canvas.service";


@NgModule({
  declarations: [
    AppComponent,
    RegComponent,
    LogComponent,
    MainComponent
  ],
  imports: [
    HttpClientModule,
    SliderModule,
    DropdownModule,
    InputTextModule,
    ButtonModule,
    CheckboxModule,
    BrowserModule,
    AppRoutingModule,
    StyleClassModule,
    TableModule,
    FormsModule
  ],
  providers: [MainCanvasService],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegComponent} from "./reg/reg.component";
import { LogComponent } from './log/log.component';
import {MainComponent} from "./main/main.component";
import {AuthGuard} from "./auth.guard";

const routes: Routes = [
  {path: '', component: MainComponent, canActivate: [AuthGuard]},
  {path: 'reg', component: RegComponent},
  {path: 'log', component: LogComponent},
  {path: 'main', component: MainComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

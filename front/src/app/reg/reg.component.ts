import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

export interface User {
  login: string
  password: string
}

export interface info {
  login: string
  token: string
}

@Component({
  selector: 'app-reg',
  templateUrl: './reg.component.html',
  styleUrls: ['./reg.component.css']
})
export class RegComponent implements OnInit{
  login: string = "";
  password: string = "";
  regError: string= "";

  constructor(private http: HttpClient, private router: Router) {
  }

  ngOnInit() {
  }

  logUser: User={
    login: "",
    password: ""
  }

  sendpost(){
    this.logUser.login=this.login
    this.logUser.password=this.password
    this.http.post<info>("/rest/api/reg", this.logUser)
      .subscribe(info=>{
        console.log('Response', info.login, " ", info.token)
        if(info.token!="error"){
          this.regError= "";
          localStorage.setItem("token", info.token)
          localStorage.setItem("login", info.login)
          this.router.navigate(['/main'])
        }
        else {
          this.regError= "Пользователь с таким логином уже существует!";
        }
      })
  }

}

import { Component, OnInit} from '@angular/core';
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
  selector: 'app-log',
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.css']
})
export class LogComponent implements OnInit{
  login: string = "";
  password: string = "";
  loginError: string= "";

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
    this.http.post<info>("/rest/api/login", this.logUser)
      .subscribe(info=>{
        console.log('Response', info.login, " ", info.token)
        if(info.token!="error"){
          this.loginError=""
          localStorage.setItem("token", info.token)
          localStorage.setItem("login", info.login)
          this.router.navigate(['/main'])
        }
        else {
          this.loginError="Неправильный логин или пароль!"
        }
      })
  }

}

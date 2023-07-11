import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({providedIn: 'root'})
export class AuthService{
  private isAuth = false

  constructor(private http: HttpClient) {
  }

  isAuthenticated(): Promise<boolean> {
    let token = localStorage.getItem("token")
    return new Promise(resolve => {
      this.http.get<boolean>("/rest/api/login", {
        headers: new HttpHeaders({
          "token": token+""
        })
      })
        .subscribe(result=>{
          this.isAuth=result
          resolve(this.isAuth)
        })
    })
  }
}

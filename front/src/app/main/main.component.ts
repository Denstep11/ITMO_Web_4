import { Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {MainCanvasService} from "../services/main-canvas.service";
import * as events from "events";
import {Router} from "@angular/router";

export interface SendPoint {
  x: string
  y: string
  r: string
}

export interface SavePoint {
  x: number
  y: number
  r: number
  color:string
}

export interface Point{
  x: string
  y: string
  r: string
  result: string
  time: string
  long_time: string
}

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})


export class MainComponent implements OnInit{
  y: string = "0";
  x: string = "0";
  r: string = "1";
  canvasX:string="";
  canvasY:string="";
  isCanvasPoint: boolean = false

  sPoint: SavePoint={
    x: 0,
    y: 0,
    r: 0,
    color: "red"
  }



  private canvas: HTMLCanvasElement;
  private context: CanvasRenderingContext2D;

  points: Point[] = []
  localPoints: SavePoint[] = []

  constructor(private http: HttpClient, private mainCanvasService: MainCanvasService, private router: Router) {
  }

  ngOnInit() {


    this.canvas = document.getElementById('canvas') as HTMLCanvasElement;
    this.context = <CanvasRenderingContext2D>this.canvas.getContext("2d")

    this.http.get<Point[]>("/rest/api/points", {
      headers: new HttpHeaders({
        "token": localStorage.getItem("token")+""
      })
    })
      .subscribe(points=>{
        this.points=points
        for (let i in points){
          let point = points[i]
          let color = ""
          if(point.result){
            color = "green"
          }
          if(!point.result){
            color = "red"
          }
          this.mainCanvasService.savePointInLocal((Number(point.x)/Number(point.r)*112+140), (-Number(point.y)/Number(point.r)*112+140), Number(point.r), color, this.localPoints)
          //this.mainCanvasService.drawCircle(this.context,(Number(point.x)/ Number(point.r))*112+140, (-Number(point.y)/ Number(point.r))*112+140, color)
        }
          this.mainCanvasService.drawPoints(this.context, Number(this.r), this.localPoints)
      })
  }

  newPoint: SendPoint={
    x: this.x,
    y: this.y,
    r: this.r
  }

  sendpost(x:string,y:string,r:string){
    this.newPoint.x=x;
    this.newPoint.y=y;
    this.newPoint.r=r;
    this.http.post<Point>("/rest/api/points", this.newPoint, {
      headers: new HttpHeaders({
        "token": localStorage.getItem("token")+"",
        "canvas": this.isCanvasPoint+""
      })
    })
      .subscribe(point=>{

        let color=""
        if(point.result){
          color = "green"
        }
        if(!point.result){
          color = "red"
        }
        console.log('Response', point)
        if (point.result!="error"){
          this.points.push(point)
          this.mainCanvasService.savePointInLocal((Number(point.x)/Number(point.r)*112+140), (-Number(point.y)/Number(point.r)*112+140), Number(point.r), color, this.localPoints)
          this.mainCanvasService.drawCircle(this.context,(Number(point.x)/ Number(point.r))*112+140, (-Number(point.y)/ Number(point.r))*112+140, color)
        }
        else{
          console.log("Ошибка валидации")
        }

      })
  }

  dropdownX(value:string){
    this.x=value;
  }

  dropdownR(value:string){
    this.r=value;
    this.updateR()
  }

  drawPoint(e:MouseEvent){
    this.isCanvasPoint=true
    let x:number;
    let y:number;
    x = e.offsetX;
    y= e.offsetY;
    this.canvasX=x.toString()
    this.canvasY=y.toString()
    let sendX = (x-140)/112*Number(this.r)
    let sendY = -(y-140)/112*Number(this.r)
    this.sendpost(sendX.toFixed(3), sendY.toFixed(3), this.r)

    console.log(x+" "+y+" и "+sendX +" " + sendY)
    console.log( this.localPoints)
  }

  updateR(){
    this.context.clearRect(0, 0, this.canvas.width, this.canvas.height)
    this.mainCanvasService.drawPoints(this.context, Number(this.r), this.localPoints)
  }

  private timerLog = setInterval(() => {
    this.http.get<boolean>("/rest/api/login", {
      headers: new HttpHeaders({
        "token": localStorage.getItem("token")+""
      })
    })
      .subscribe(result=>{
        if(!result){
          this.router.navigate(['/log'])
        }
      })

    this.http.get<Point[]>("/rest/api/points", {
      headers: new HttpHeaders({
        "token": localStorage.getItem("token")+""
      })
    })
      .subscribe(points=>{
        let newPoints: SavePoint[] = [];
        this.points=points
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
        for (let i in points){
          let point = points[i]
          let color = ""
          if(point.result){
            color = "green"
          }
          if(!point.result){
            color = "red"
          }
          this.mainCanvasService.savePointInLocal((Number(point.x)/Number(point.r)*112+140), (-Number(point.y)/Number(point.r)*112+140), Number(point.r), color, newPoints)
          //this.mainCanvasService.drawCircle(this.context,(Number(point.x)/ Number(point.r))*112+140, (-Number(point.y)/ Number(point.r))*112+140, color)
        }
        this.localPoints = newPoints
        //this.context.clearRect(0, 0, this.canvas.width, this.canvas.height)
        this.mainCanvasService.drawPoints(this.context, Number(this.r), this.localPoints)
      })
  }, 2000)

  cleanSession(){
    localStorage.clear()
    clearInterval(this.timerLog);
  }


}

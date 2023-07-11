import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Point, SavePoint} from "../main/main.component";



export class MainCanvasService {

  //localPoints: SavePoint[] = []

  private canvas: HTMLCanvasElement;
  private context: CanvasRenderingContext2D|null;

  constructor() {
    //this.localPoints = <SavePoint[]>JSON.parse(<string>localStorage.getItem("circles")) || [];
  }

  public drawCircle(context: CanvasRenderingContext2D, x: number, y: number, color:string){
    context.beginPath();
    context.arc(x, y, 5, 0, 2 * Math.PI);
    context.fillStyle = color;
    context.fill();
    context.closePath();
    context.stroke();
  }


  public drawPoints(context: CanvasRenderingContext2D, r:number, points: SavePoint[]){
    for (let i in points){
      let point = points[i]
      this.drawCircle(context,((point.x-140)/112)*(point.r/r)*112+140, ((point.y-140)/112)*(point.r/r)*112+140,point.color)
    }
  }

  savePointInLocal(x:number, y:number, r:number, color:string, points: SavePoint[]){
    let circle = {
      "x": x,
      "y": y,
      "r": r,
      "color": color
    };
    points.push(circle)
    //console.log(this.localPoints)
    //localStorage.setItem("circles", JSON.stringify(this.localPoints))
  }



}

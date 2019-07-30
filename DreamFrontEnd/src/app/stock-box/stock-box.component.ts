import { Component, OnInit, NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import * as $ from 'jquery';
// import * as angular from "angular";

@Component({
  selector: 'app-stock-box',
  templateUrl: './stock-box.component.html',
  styleUrls: ['./stock-box.component.css']
})

@NgModule({
    imports: [NgbModule]
})

export class StockBoxComponent implements OnInit {
  showTradesPage:boolean = true;
  stratCounter:number = 0;
  addEles:boolean = false;

  changeLabel(obj1 ,obj2) {
    (<HTMLInputElement>document.getElementById(obj1)).innerHTML = obj2;
  }

  exitModule(obj){
    (<HTMLInputElement>document.getElementById("module1")).remove();
    //WILL HAVE TO ADD LOGIC TO EXIT TRADE HERE, ALSO WITH ALERT
  }

  hideTrades(){
    this.showTradesPage = false;
    document.getElementById("menu2").classList.add("active");
    document.getElementById("menu1").classList.remove("active");
  }

  showTrades(){
    this.showTradesPage = true;
    document.getElementById("menu2").classList.remove("active");
    document.getElementById("menu1").classList.add("active")
  }

  toggleTradePage(){
    this.showTradesPage = !this.showTradesPage;
  }

  buttonThreeClick(){
    // var stratCounter = 0;
    // var addEles = false;

    this.stratCounter++;
        var stockName = (<HTMLInputElement>document.getElementById("stockSelector")).value;
        var stockQuantity = (<HTMLInputElement>document.getElementById("quantSelector")).value;
        var strat = (<HTMLInputElement>document.getElementById("strategySelector")).innerHTML;
        var stratId = "strat" + this.stratCounter;
        var yId = "y" + this.stratCounter;
        var gId = "g" + this.stratCounter;

        var postObj = {
          "type":"two moving averages",
          "stock": stockName,
          "shortPeriod": 1,
          "longPeriod":2,
          "size":stockQuantity
        }

        if(stockName == "" || stockQuantity == ""
          || strat == "Select Strategy" ){
          alert("All fields are required")
        }else{
          this.addEles = true;
        }

        var markup =
        `<tbody id=${stratId} style="background:#e1f5e6">
          <tr>
            <td>${stockName}</td>
            <td>${stockQuantity}</td>
            <td>${strat}</td>
            <td>1</td>
            <td>good</td>
            <td>
              <button type="button" class="btn btn-success" style="margin:0px" id=${this.stratCounter}>Start</button>
              <button type="button" class="btn btn-warning" style="margin:0px" id=${this.stratCounter}>Pause</button>
              <button type="button" class="btn btn-danger" style="margin:0px" id=${this.stratCounter}>Exit</button>
            </td>
          </tr>
        </tbody>`

        if(this.addEles){
          console.log("ehehehe")
          document.getElementById("t1").style["display"] = "";
          $("#t1").append(markup);

          $(".btn-danger").click(
            function () {
              var id = $(this).attr("id");
              var remove;
              remove = "#strat" + id;
              $(remove).remove();
            }
          )

          $(".btn-warning").click(
            function () {
              console.log('he')
              var id = $(this).attr("id");
              var remove;
              remove = "#strat" + id;
              $(remove).css("background","#feffd4");
            }
          )

          $(".btn-success").click(
            function () {
              var id = $(this).attr("id");
              var remove;
              remove = "#strat" + id;
              $(remove).css("background","#e1f5e6");
            }
          )
        }

        $.ajax({
          type: "POST",
          url: 'http://localhost:8080/strategy/start',
          contentType:"application/json",
          data: JSON.stringify(postObj),
          success: function(response) {
            console.log("yayy made it here")
          }
        });

  }

  constructor() {

  }

  ngOnInit() {

    var stratCounter = 0;
    var addEles = false;

    $("#bt3").click(
      function () {

        // stratCounter++;
        // var stockName = (<HTMLInputElement>document.getElementById("stockSelector")).value;
        // var stockQuantity = (<HTMLInputElement>document.getElementById("quantSelector")).value;
        // var strat = (<HTMLInputElement>document.getElementById("strategySelector")).innerHTML;
        // var stratId = "strat" + stratCounter;
        // var yId = "y" + stratCounter;
        // var gId = "g" + stratCounter;

        // var postObj = {
        //   "type":"two moving averages",
        //   "stock": stockName,
        //   "shortPeriod": 1,
        //   "longPeriod":2,
        //   "size":stockQuantity
        // }

        // if(stockName == "" || stockQuantity == ""
        //   || strat == "Select Strategy" ){
        //   alert("All fields are required")
        // }else{
        //   addEles = true;
        // }

        // var markup =
        // `<tbody id=${stratId} style="background:#e1f5e6">
        //   <tr>
        //     <td>${stockName}</td>
        //     <td>${stockQuantity}</td>
        //     <td>${strat}</td>
        //     <td>1</td>
        //     <td>good</td>
        //     <td>
        //       <button type="button" class="btn btn-success" style="margin:0px" id=${stratCounter}>Start</button>
        //       <button type="button" class="btn btn-warning" style="margin:0px" id=${stratCounter}>Pause</button>
        //       <button type="button" class="btn btn-danger" style="margin:0px" id=${stratCounter}>Exit</button>
        //     </td>
        //   </tr>
        // </tbody>`

        // if(addEles){
        //   console.log("ehehehe")
        //   document.getElementById("t1").style["display"] = "";
        //   $("#t1").append(markup);

        //   $(".btn-danger").click(
        //     function () {
        //       var id = $(this).attr("id");
        //       var remove;
        //       remove = "#strat" + id;
        //       $(remove).remove();
        //     }
        //   )

        //   $(".btn-warning").click(
        //     function () {
        //       console.log('he')
        //       var id = $(this).attr("id");
        //       var remove;
        //       remove = "#strat" + id;
        //       $(remove).css("background","#feffd4");
        //     }
        //   )

        //   $(".btn-success").click(
        //     function () {
        //       var id = $(this).attr("id");
        //       var remove;
        //       remove = "#strat" + id;
        //       $(remove).css("background","#e1f5e6");
        //     }
        //   )
        // }

        // $.ajax({
        //   type: "POST",
        //   url: 'http://localhost:8080/strategy/start',
        //   contentType:"application/json",
        //   data: JSON.stringify(postObj),
        //   success: function(response) {
        //     console.log("yayy made it here")
        //   }
        // });
      }
    )

  }


}

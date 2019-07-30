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


  stockboxes: Array<StockBoxComponent> = [];

  changeLabel(obj) {
    (<HTMLInputElement>document.getElementById("strategySelector")).innerHTML = obj;
  }

  exitModule(obj){
    (<HTMLInputElement>document.getElementById("module1")).remove();
    //WILL HAVE TO ADD LOGIC TO EXIT TRADE HERE, ALSO WITH ALERT
  }

  constructor() {
    
  }


  ngOnInit() {
    var stratCounter = 0;
    var addEles = false;
        $("#bt3").click(
            function () {
                stratCounter++;
                var stockName = document.getElementById("stockSelector").value;
                var stockQuantity = document.getElementById("quantSelector").value;
                var strat = document.getElementById("strategySelector").innerHTML;


                if(stockName == "" || stockQuantity == "" 
                  || strat == "Select Strategy" ){
                  alert("All fields are required")
                }else{
                  addEles = true;
                }

                var markup = 
                `<div id="strat${stratCounter}"class = "floaty style="width=30%">
                  <div class="col">
                    <div class = "row">
                      Stock: ${stockName}
                      <br>
                      Quantity: ${stockQuantity}
                      <br>
                      Strategy: ${strat}
                      <br>
                      Time Frames: 1 to 2
                      <br>
                      Status
                    </div>
                    <div class="row">
                      <button type="button" class="btn btn-success" id="bt3">Restart</button>
                      <button type="button" class="btn btn-warning" id="bt3">Pause</button>
                      <button type="button" id="${stratCounter}" class="btn btn-danger" id="bt3">End</button>
                    </div>
                  </div>
                </div>`

                if(addEles){
                  $("body").append(markup);

                  $(".btn-danger").click(
                      function () {
                          var id = $(this).attr("id");
                          var remove;
                          remove = "#strat" + id;
                          $(remove).remove();
                      }
                  )
                }
            }
        )
    
  }


}

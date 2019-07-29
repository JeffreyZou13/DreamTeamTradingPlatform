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

  addModule(obj){
    // var copy = document.getElementById("module1");
    // var clone = copy.cloneNode(true);
    // document.getElementById("top-level").appendChild(clone);

        // $("#bt3").click(
        //     function () {
        //       console.log('this was fired')
                
        //     }
        // )

  }

  exitModule(obj){
    (<HTMLInputElement>document.getElementById("module1")).remove();
    //WILL HAVE TO ADD LOGIC TO EXIT TRADE HERE, ALSO WITH ALERT
  }

  constructor() {
    
  }

  ngOnInit() {
    var stratCounter = 0;
        $("#bt3").click(
            function () {
                stratCounter++;
                var markup = `<div id="stratBox${stratCounter}" class="stratBox" > <button id="redButton${stratCounter}" class="redButtonClass">red button</button> <input type="text" id="redText${stratCounter}" class="redTextClass"  value=""/>  </div>`;
                $("body").append(markup);

                $(".redButtonClass").click(
                    function () {
                        var id = $(this).attr("id");
                        var idLength = id.length;
                        var strNumber = id.substr(idLength - 1, 1);
                        $("#redText" + strNumber).val("I was clicked");
                        $("#topDiv").text("A click occurred in stratBox numbered: " + strNumber);
                    }
                )
            }
        )
    
  }


}

import { Component, OnInit, NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { map } from "rxjs/operators";
declare let $: any;


@Component({
  selector: 'app-stock-box',
  templateUrl: './stock-box.component.html',
  styleUrls: ['./stock-box.component.css']
})

@NgModule({
    imports: [NgbModule,HttpClient]
})

export class StockBoxComponent implements OnInit {
  stratCounter:number = 0;
  addEles:boolean = false;


  changeLabel(obj1 ,obj2) {
    (<HTMLInputElement>document.getElementById(obj1)).innerHTML = obj2;
  }

  exitModule(obj){
    (<HTMLInputElement>document.getElementById("module1")).remove();
    //WILL HAVE TO ADD LOGIC TO EXIT TRADE HERE, ALSO WITH ALERT
  }


  stockClick(){
    console.log('here');
  }

  // pauseClick(){
  //
  // }

  buttonThreeClick(){

    this.stratCounter++;
    var stockName = (<HTMLInputElement>document.getElementById("stockSelector")).value;
    var stockQuantity = (<HTMLInputElement>document.getElementById("quantSelector")).value;
    var strat = (<HTMLInputElement>document.getElementById("strategySelector")).innerHTML;
    var shortVal = (<HTMLInputElement>document.getElementById("shortSelector")).innerHTML;
    var longVal = (<HTMLInputElement>document.getElementById("longSelector")).innerHTML;

    var stratId = "strat" + this.stratCounter;
    var yId = "y" + this.stratCounter;
    var gId = "g" + this.stratCounter;

    console.log(shortVal)

    var newStrat = {
      "type":"two moving averages",
      "stock": stockName,
      "shortPeriod": 1,
      "longPeriod": 3,
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
        <td>Running</td>
        <td>
          <button type="button" class="btn btn-success" style="margin:0px" id=${this.stratCounter}>Start</button>
          <button type="button" class="btn btn-warning" style="margin:0px" id=${yId}>Pause</button>
          <button type="button" class="btn btn-danger" style="margin:0px" id=${this.stratCounter}>Exit</button>
        </td>
      </tr>
    </tbody>`


    if(this.addEles){
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

      //PAUSING A STRATEGY
      $(".btn-warning").click(
        function () {
          // var yId = $(this).attr("id");
          var id =  ($(this).attr("id")).substring(2);
          // var btn = "#strat" + id;
          $("#strat" + id).css("background","#feffd4");
          var pauseStrat = {
            "id":id
          }
          $.ajax({
            type: "POST",
            url: 'http://localhost:8081/strategy/pause',
            contentType:"application/json",
            data: JSON.stringify(pauseStrat),
            success: function(response) {
              console.log(response)
              console.log("Paused a trade")
            }

        });
      })

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
      url: 'http://localhost:8081/strategy/start',
      contentType:"application/json",
      data: JSON.stringify(newStrat),
      success: function(response) {
        console.log(response)
        var firstId = document.getElementById(stratId).id;
        document.getElementById(stratId).id = "strat" + response.id;
        document.getElementById(yId).id = "Y-" + response.id;
        console.log("Posted a new trade")
      }
    });

  }

  constructor(private http: HttpClient) {

  }

  ngOnInit() {
    var store1;
      $.ajax({
      type: "GET",
      async: false,
      url: 'http://nyc31.conygre.com:31/Stock/getSymbolList',
      contentType:"application/json",
      success: function(response) {
        var store = [];
        for (const key of Object.keys(response)) {
          store.push(response[key].symbol.toUpperCase());
        }
        store1 = store;
        console.log(store1)
      }
    });

    $('#stockSelector').autocomplete({
      source: store1
    })
  }
}

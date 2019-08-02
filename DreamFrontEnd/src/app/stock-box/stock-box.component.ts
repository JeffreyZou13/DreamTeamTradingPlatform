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
    var rId = "r" + this.stratCounter;
    var aId = "a" +  this.stratCounter;
    var nId = "n" +  this.stratCounter;
    var qId = "q" +  this.stratCounter;
    var kId = "k" +  this.stratCounter;
    var newStrat;

    if(strat  == "Two Moving Averages"){ //tma
      console.log('I DID TMA')
      newStrat = {
        "type":strat.toLowerCase(),
        "stock": stockName,
        "shortPeriod": shortVal,
        "longPeriod": longVal,
        "size":stockQuantity
      }
      console.log(newStrat)
    }else{  //bollinger band
      newStrat = {
        "type":strat.toLowerCase(),
        "stock": stockName,
        "durationTime": shortVal,
        "size":stockQuantity
      }
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
        <td id=${nId}>${stockName}</td>
        <td id=${qId}>${stockQuantity}</td>
        <td id=${kId}>${strat}</td>
        <td>1</td>
        <td>Running</td>
        <td>
          <button type="button" class="btn btn-success" style="margin:0px" id=${gId}>Restart</button>
          <button type="button" class="btn btn-warning" style="margin:0px" id=${yId}>Pause</button>
          <button type="button" class="btn btn-danger" style="margin:0px" id=${rId}>Exit</button>
        </td>
        <td>
          <button type="button" class="btn btn-secondary" style="margin:0px" id=${aId}>View Analytics</button>
        </td
      </tr>
    </tbody>`

    if(this.addEles){
      document.getElementById("t1").style["display"] = "";
      $("#t1").append(markup);
    }
      $(".btn-danger").click(
        function () {
          var id = ($(this).attr("id")).substring(2);
          $('#strat'  + id).remove();
          var endStrat = {
            "id":id
          }
          $.ajax({
            type: "POST",
            url: 'http://localhost:8081/strategy/stop',
            contentType:"application/json",
            data: JSON.stringify(endStrat),
            success: function(response) {
              console.log("Ended a trade")
            }
        })
      })

      //CLICKING Analytics
      $(".btn-secondary").click(
        function(){
          var id = ($(this).attr("id")).substring(2);
          document.getElementById("graphPage").style.display = "";
          document.getElementById("allTrades").style.display = "none";
          document.getElementById("chartContainer").style.display = "";
          document.getElementById("chartContainer2").style.display = "";
          document.getElementById("menu2").classList.add("active");
          document.getElementById("menu1").classList.remove("active");
          console.log(document.getElementById("K-" +  id).innerHTML)
          doThis(id,document.getElementById("N-" +  id).innerHTML,
                document.getElementById("K-" +  id).innerHTML, document.getElementById("Q-" +  id).innerHTML)
        }
      )

      //PAUSING A STRATEGY
      $(".btn-warning").click(
        function () {
          console.log('i see@')
          var id =  ($(this).attr("id")).substring(2);
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
              console.log("Paused a strategy")
            }
        });
      })

      // $(remove).css("background","#e1f5e6");
      $(".btn-success").click(
        function () {
          var id =  ($(this).attr("id")).substring(2);
          $("#strat" + id).css("background","#e1f5e6");
          var resumeStrat = {
            "id":id
          }
          $.ajax({
            type: "POST",
            url: 'http://localhost:8081/strategy/resume',
            contentType:"application/json",
            data: JSON.stringify(resumeStrat),
            success: function(response) {
              console.log(response)
              console.log("Resume Strat")
            }
        });
      })

    $.ajax({
      type: "POST",
      url: 'http://localhost:8081/strategy/start',
      contentType:"application/json",
      data: JSON.stringify(newStrat),
      success: function(response) {
        console.log(response)
        var firstId = document.getElementById(stratId).id;
        $("#viewHistories").append(
            `<button ngbDropdownItem>${response.id}</button>`
        )
        document.getElementById(stratId).id = "strat" + response.id;
        document.getElementById(yId).id = "Y-" + response.id;
        document.getElementById(rId).id = "R-" + response.id;
        document.getElementById(gId).id = "G-" + response.id;
        document.getElementById(aId).id = "A-" + response.id;
        document.getElementById(nId).id = "N-" + response.id;
        document.getElementById(qId).id = "Q-" + response.id;
        document.getElementById(kId).id = "K-" + response.id;
        console.log("Posted a new strategy")
      }
    });

  }

  constructor(private http: HttpClient) {
    this.stratCounter  = 0

    //THIS IS FOR RELOADING THE PAGE
    $.ajax({
      type: "GET",
      url: 'http://localhost:8081/history/strategies/notstopped',
      contentType: 'application/json',
      success: (response)=> {
        console.log(response)

        //BOLLINGER BAND RELOAD
        for (var i = 0; i < response.strategies["bollinger band"].length; i++){
          var backgroundColor;

          if(response.strategies["bollinger band"][i][6]  == "paused"){
            backgroundColor = "#feffd4";
          }else{
            backgroundColor = "#e1f5e6";
          }

          var markup =
          `<tbody id=${"strat"+response.strategies["bollinger band"][i][0]} style="background:${backgroundColor}">
            <tr>
              <td id=${"N-"+response.strategies["bollinger band"][i][0]}>${response.strategies["bollinger band"][i][3]}</td>
              <td id=${"Q-"+response.strategies["bollinger band"][i][0]}>${response.strategies["bollinger band"][i][4]}</td>
              <td id=${"K-"+response.strategies["bollinger band"][i][0]}>bollinger band</td>
              <td>1</td>
              <td>Running</td>
              <td>
                <button type="button" class="btn btn-success" style="margin:0px" id=${"G-"+response.strategies["bollinger band"][i][0]}>Restart</button>
                <button type="button" class="btn btn-warning" style="margin:0px" id=${"Y-"+response.strategies["bollinger band"][i][0]}>Pause</button>
                <button type="button" class="btn btn-danger" style="margin:0px" id=${"R-"+response.strategies["bollinger band"][i][0]}>Exit</button>
              </td>
              <td>
                <button type="button" class="btn btn-secondary" style="margin:0px" id=${"A-"+response.strategies["bollinger band"][i][0]}>View Analytics</button>
              </td>
            </tr>
          </tbody>`

          $("#t1").append(markup);
          document.getElementById("t1").style["display"] = "";
        }

        //TWO MOVING AVERAGES RELOAD
        for (var i = 0; i < response.strategies["two moving averages"].length; i++){
          var backgroundColor;

          if(response.strategies["two moving averages"][i][6]  == "paused"){
            backgroundColor = "#feffd4";
          }else{
            backgroundColor = "#e1f5e6";
          }

          var markup =
          `<tbody id=${"strat"+response.strategies["two moving averages"][i][0]} style="background:${backgroundColor}">
            <tr>
              <td id=${"N-"+response.strategies["two moving averages"][i][0]}>${response.strategies["two moving averages"][i][3]}</td>
              <td id=${"Q-"+response.strategies["two moving averages"][i][0]}>${response.strategies["two moving averages"][i][4]}</td>
              <td id=${"K-"+response.strategies["two moving averages"][i][0]}>two moving averages</td>
              <td>1</td>
              <td>Running</td>
              <td>
                <button type="button" class="btn btn-success" style="margin:0px" id=${"G-"+response.strategies["two moving averages"][i][0]}>Restart</button>
                <button type="button" class="btn btn-warning" style="margin:0px" id=${"Y-"+response.strategies["two moving averages"][i][0]}>Pause</button>
                <button type="button" class="btn btn-danger" style="margin:0px" id=${"R-"+response.strategies["two moving averages"][i][0]}>Exit</button>
              </td>
              <td>
              <button type="button" class="btn btn-secondary" style="margin:0px" id=${"A-"+response.strategies["two moving averages"][i][0]}>View Analytics</button>
              </td>
            </tr>
          </tbody>`

          $("#t1").append(markup);
          document.getElementById("t1").style["display"] = "";
        }

        //CLICKING Analytics
        $(".btn-secondary").click(
          function(){
            var id = ($(this).attr("id")).substring(2);
            document.getElementById("graphPage").style.display = "";
            document.getElementById("allTrades").style.display = "none";
            document.getElementById("chartContainer").style.display = "";
            document.getElementById("chartContainer2").style.display = "";
            document.getElementById("menu2").classList.add("active");
            document.getElementById("menu1").classList.remove("active");
            console.log(document.getElementById("K-" +  id).innerHTML)
            doThis(id,document.getElementById("N-" +  id).innerHTML,
                  document.getElementById("K-" +  id).innerHTML, document.getElementById("Q-" +  id).innerHTML)
          }
        )

        //PAUSING A STRATEGY
        $(".btn-warning").click(
          function () {
            console.log('i see@')
            var id =  ($(this).attr("id")).substring(2);
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
                console.log("Paused a strategy")
              }
          });
        })

        // $(remove).css("background","#e1f5e6");
        $(".btn-success").click(
          function () {
            var id =  ($(this).attr("id")).substring(2);
            $("#strat" + id).css("background","#e1f5e6");
            var resumeStrat = {
              "id":id
            }
            $.ajax({
              type: "POST",
              url: 'http://localhost:8081/strategy/resume',
              contentType:"application/json",
              data: JSON.stringify(resumeStrat),
              success: function(response) {
                console.log(response)
                console.log("Resume Strat")
              }
          });
        })

        $(".btn-danger").click(
          function () {
            var id = ($(this).attr("id")).substring(2);
            $('#strat'  + id).remove();
            var endStrat = {
              "id":id
            }
            $.ajax({
              type: "POST",
              url: 'http://localhost:8081/strategy/stop',
              contentType:"application/json",
              data: JSON.stringify(endStrat),
              success: function(response) {
                console.log("Ended a trade")
              }
          })
        })
      }
    })

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
      }
    });

    $('#stockSelector').autocomplete({
      source: store1
    })
  }
}

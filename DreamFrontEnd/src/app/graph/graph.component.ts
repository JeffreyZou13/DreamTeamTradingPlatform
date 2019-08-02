import { Component, OnInit } from '@angular/core';
import * as CanvasJS from './canvasjs.min.js';
declare let $:any;

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.css']
})
export class GraphComponent implements OnInit {
  history:any;

  constructor() {

  }

  	ngOnInit() {

      function doThis() {
        console.log("I AM HERE");
      }

      $.ajax({
        type: "GET",
        url: 'http://localhost:8081/history/strategies',
        contentType: 'application/json',
        success: (response)=> {
          console.log('this is the one im looking for')

          for (var i = 0; i < response.strategies["bollinger band"].length; i++){
            var id = response.strategies["bollinger band"][i]['strategyID']
            var volume = response.strategies["bollinger band"][i]['volume']
            var type = 'Bollinger Band'
            var name  = response.strategies["bollinger band"][i]['stockName']
            var markup =
            `<button class="dropdown-item" ngbDropdownItem onclick="doThis('${id}', '${name}', '${type}','${volume}')">
            ${response.strategies["bollinger band"][i]['strategyID']}
            </button>`
            $("#performanceSelector2").append(markup);
          }

          for (var i = 0; i < response.strategies["two moving averages"].length; i++){
            var id= response.strategies["two moving averages"][i]['strategyID']
            var name= response.strategies["two moving averages"][i]['stockName']
            var volume= response.strategies["two moving averages"][i]['volume']
            var type = 'Two Moving Averages'
            var markup =
            `<button class="dropdown-item" ngbDropdownItem onclick="doThis('${id}','${name}','${type}','${volume}')">
            ${response.strategies["two moving averages"][i]['strategyID']}
            </button>`
            $("#performanceSelector2").append(markup);
          }
        }
      })


    // setInterval(worker,5000)
  }
}

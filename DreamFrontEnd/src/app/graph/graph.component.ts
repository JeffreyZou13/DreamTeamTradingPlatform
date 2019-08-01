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

  // doThis(){
  //   console.log('HEY CMOND')
  // }

   doThis(){
    console.log('i need help');
  }

  constructor() {


  }

  	ngOnInit() {

      $.ajax({
        type: "GET",
        url: 'http://localhost:8081/history/strategies/notstopped',
        contentType: 'application/json',
        success: (response)=> {
          console.log(response)

          for (var i = 0; i < response.strategies["bollinger band"].length; i++){
            var markup =
            `<button ngbDropdownItem onclick="console.log('${response.strategies['bollinger band'][i][0]}')">${response.strategies["bollinger band"][i][0]}</button>`
            $("#performanceSelector2").append(markup);
          }
        }
      })



      $.ajax({
        type: "GET",
        url: 'http://localhost:8081/history/orders/5e5f2c7f-7122-4a0b-9f7b-627798139204',
        contentType:"application/json",
        success: function(response) {
          console.log(response)
          let dataPoints = [];
        	let y = 0;
        	for ( var i = 0; i < response.orders.length; i++ ) {
        		y = response.orders[i].profit
        		dataPoints.push({ y: y});
        	}

        	let chart = new CanvasJS.Chart("chartContainer", {
        		zoomEnabled: true,
        		animationEnabled: true,
        		exportEnabled: true,
        		title: {
        			text: "Two Moving Averages Example"
        		},
        		subtitles:[{
        			text: "Try Zooming and Panning"
        		}],
        		data: [
        		{
        			type: "line",
        			dataPoints: dataPoints
        		}]
        	});
        	chart.render();

        }
      });

    }
}

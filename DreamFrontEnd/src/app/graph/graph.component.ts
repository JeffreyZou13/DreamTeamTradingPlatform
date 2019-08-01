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
  //   $.ajax({
  //     type: "GET",
  //     url: 'http://localhost:8081/history/orders/pnl/two moving averages',
  //     contentType:"application/json",
  //     success: function(response) {
  //       console.log('HISTORY OF STRATEGIES')
  //       console.log(response)
  //     }
  //   )
  }

  	ngOnInit() {
//"12d4fd2b-7e58-4d89-9bc3-c2b3e2445836"
      console.log('HERE');
      $.ajax({
        type: "GET",
        url: 'http://localhost:8081/history/orders/12d4fd2b-7e58-4d89-9bc3-c2b3e2445836',
        contentType:"application/json",
        success: function(response) {
          console.log(response)
          let dataPoints = [];
        	let y = 0;
        	for ( var i = 0; i < response.orders.length; i++ ) {
        		y += response.orders[i].profit
        		dataPoints.push({ y: y});
        	}

        	let chart = new CanvasJS.Chart("chartContainer", {
        		zoomEnabled: true,
        		animationEnabled: true,
        		exportEnabled: true,
        		title: {
        			text: "Performance Demo - 10000 DataPoints"
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

      $.ajax({
        type: "GET",
        url: 'http://localhost:8081/history/strategies/notstopped',
        contentType: 'application/json',
        success: function(response) {
          console.log(response);
        }
      });
    }
}

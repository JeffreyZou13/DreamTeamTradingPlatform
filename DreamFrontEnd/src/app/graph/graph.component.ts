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

    }
}

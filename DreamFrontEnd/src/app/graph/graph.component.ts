import { Component, OnInit } from '@angular/core';
import * as CanvasJS from './canvasjs.min.js';

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.css']
})
export class GraphComponent implements OnInit {

  constructor() { }

  	ngOnInit() {
	let dataPoints = [];
	let y = 0;		
	for ( var i = 0; i < 10000; i++ ) {		  
		y += Math.round(5 + Math.random() * (-5 - 5));	
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

}

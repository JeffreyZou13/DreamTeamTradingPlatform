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
    var copy = document.getElementById("module1");
    var clone = copy.cloneNode(true);
    // clone.id = "module2"
    document.getElementById("top-level").appendChild(clone);

  }

  exitModule(obj){
    (<HTMLInputElement>document.getElementById("module1")).remove();
    //WILL HAVE TO ADD LOGIC TO EXIT TRADE HERE, ALSO WITH ALERT
  }

  constructor() {
    
  }

  ngOnInit() {
    
  }


}

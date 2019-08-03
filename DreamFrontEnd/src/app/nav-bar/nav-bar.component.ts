import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})

export class NavBarComponent implements OnInit {
	showTradesPage:boolean = true;

  hideTrades(){
    this.showTradesPage = false;
    document.getElementById("graphPage").style.display = "";
    document.getElementById("allTrades").style.display = "none";
    document.getElementById("chartContainer").style.display = "inline-block";
    document.getElementById("chartContainer2").style.display = "inline-block";
    document.getElementById("menu2").classList.add("active");
    document.getElementById("menu1").classList.remove("active");
  }

  showTrades(){
    this.showTradesPage = true;
    document.getElementById("graphPage").style.display = "none";
    document.getElementById("allTrades").style.display = "";
    document.getElementById("chartContainer").style.display = "none";
    document.getElementById("chartContainer2").style.display = "none";
    document.getElementById("menu2").classList.remove("active");
    document.getElementById("menu1").classList.add("active")
  }

  constructor() { }

  ngOnInit() {
  }

}

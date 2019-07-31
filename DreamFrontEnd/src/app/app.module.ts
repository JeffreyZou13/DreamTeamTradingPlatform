import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StockBoxComponent } from './stock-box/stock-box.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap'; 
import { HttpClientModule } from '@angular/common/http';
import { GraphComponent } from './graph/graph.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';

@NgModule({
  declarations: [
    AppComponent,
    StockBoxComponent,
    GraphComponent,
    NavBarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { Component, NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

declare let $: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

@NgModule({
	declarations: [AppComponent],
  imports: [NgbModule],
  bootstrap: [AppComponent]
})

export class AppComponent {
  title = 'DreamFrontEnd';

  ngOnInit(){
  }
}

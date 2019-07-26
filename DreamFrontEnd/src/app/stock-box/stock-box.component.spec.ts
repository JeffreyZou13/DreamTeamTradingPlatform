import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StockBoxComponent } from './stock-box.component';

describe('StockBoxComponent', () => {
  let component: StockBoxComponent;
  let fixture: ComponentFixture<StockBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StockBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StockBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

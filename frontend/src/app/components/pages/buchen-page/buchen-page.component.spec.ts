import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuchenPageComponent } from './buchen-page.component';

describe('BuchenPageComponent', () => {
  let component: BuchenPageComponent;
  let fixture: ComponentFixture<BuchenPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BuchenPageComponent]
    });
    fixture = TestBed.createComponent(BuchenPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

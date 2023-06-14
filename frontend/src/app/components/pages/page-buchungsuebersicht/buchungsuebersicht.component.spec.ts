import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuchungsuebersichtComponent } from './buchungsuebersicht.component';

describe('BuchungsuebersichtComponent', () => {
  let component: BuchungsuebersichtComponent;
  let fixture: ComponentFixture<BuchungsuebersichtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuchungsuebersichtComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuchungsuebersichtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

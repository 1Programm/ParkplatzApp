import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuchungsuebersichtAdminViewDateComponent } from './view-date.component';

describe('ViewDateComponent', () => {
  let component: BuchungsuebersichtAdminViewDateComponent;
  let fixture: ComponentFixture<BuchungsuebersichtAdminViewDateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuchungsuebersichtAdminViewDateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuchungsuebersichtAdminViewDateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

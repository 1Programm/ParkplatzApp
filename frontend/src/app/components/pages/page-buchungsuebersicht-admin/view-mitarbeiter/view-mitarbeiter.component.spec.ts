import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuchungsuebersichtAdminViewMitarbeiterComponent } from './view-mitarbeiter.component';

describe('ViewMitarbeiterComponent', () => {
  let component: BuchungsuebersichtAdminViewMitarbeiterComponent;
  let fixture: ComponentFixture<BuchungsuebersichtAdminViewMitarbeiterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuchungsuebersichtAdminViewMitarbeiterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuchungsuebersichtAdminViewMitarbeiterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

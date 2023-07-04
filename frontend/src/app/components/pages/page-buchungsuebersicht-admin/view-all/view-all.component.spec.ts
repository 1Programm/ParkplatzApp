import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuchungsuebersichtAdminViewAllComponent } from './view-all.component';

describe('ViewAllComponent', () => {
  let component: BuchungsuebersichtAdminViewAllComponent;
  let fixture: ComponentFixture<BuchungsuebersichtAdminViewAllComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuchungsuebersichtAdminViewAllComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuchungsuebersichtAdminViewAllComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

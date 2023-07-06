import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageBuchungsuebersichtAdminComponent } from './page-buchungsuebersicht-admin.component';

describe('PageBuchungsuebersichtAdminComponent', () => {
  let component: PageBuchungsuebersichtAdminComponent;
  let fixture: ComponentFixture<PageBuchungsuebersichtAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageBuchungsuebersichtAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageBuchungsuebersichtAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

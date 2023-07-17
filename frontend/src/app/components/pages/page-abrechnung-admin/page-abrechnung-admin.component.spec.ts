import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageAbrechnungAdminComponent } from './page-abrechnung-admin.component';

describe('PageAbrechnungAdminComponent', () => {
  let component: PageAbrechnungAdminComponent;
  let fixture: ComponentFixture<PageAbrechnungAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageAbrechnungAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageAbrechnungAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

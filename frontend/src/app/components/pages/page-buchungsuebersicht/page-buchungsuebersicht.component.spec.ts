import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageBuchungsuebersichtComponent } from './page-buchungsuebersicht.component';

describe('BuchungsuebersichtComponent', () => {
  let component: PageBuchungsuebersichtComponent;
  let fixture: ComponentFixture<PageBuchungsuebersichtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageBuchungsuebersichtComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageBuchungsuebersichtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

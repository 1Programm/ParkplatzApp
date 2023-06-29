import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageVerstossComponent } from './page-verstoss.component';

describe('PageVerstossComponent', () => {
  let component: PageVerstossComponent;
  let fixture: ComponentFixture<PageVerstossComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageVerstossComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageVerstossComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

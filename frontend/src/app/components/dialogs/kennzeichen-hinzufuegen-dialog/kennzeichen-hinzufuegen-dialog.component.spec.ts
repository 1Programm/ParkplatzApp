import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KennzeichenHinzufuegenDialogComponent } from './kennzeichen-hinzufuegen-dialog.component';

describe('KennzeichenHinzufuegenDialogComponent', () => {
  let component: KennzeichenHinzufuegenDialogComponent;
  let fixture: ComponentFixture<KennzeichenHinzufuegenDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KennzeichenHinzufuegenDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KennzeichenHinzufuegenDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

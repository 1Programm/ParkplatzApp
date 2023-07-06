import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditParkhausComponent } from './edit-parkhaus-dialog.component';

describe('EditParkhausComponent', () => {
  let component: EditParkhausComponent;
  let fixture: ComponentFixture<EditParkhausComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditParkhausComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditParkhausComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

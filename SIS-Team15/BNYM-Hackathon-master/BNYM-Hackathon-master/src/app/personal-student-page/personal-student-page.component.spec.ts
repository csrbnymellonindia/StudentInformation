import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalStudentPageComponent } from './personal-student-page.component';

describe('PersonalStudentPageComponent', () => {
  let component: PersonalStudentPageComponent;
  let fixture: ComponentFixture<PersonalStudentPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonalStudentPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonalStudentPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

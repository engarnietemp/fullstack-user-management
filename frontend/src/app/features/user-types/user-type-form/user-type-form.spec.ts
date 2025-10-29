import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserTypeForm } from './user-type-form';

describe('UserTypeForm', () => {
  let component: UserTypeForm;
  let fixture: ComponentFixture<UserTypeForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserTypeForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserTypeForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

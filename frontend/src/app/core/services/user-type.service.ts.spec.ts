import { TestBed } from '@angular/core/testing';

import { UserTypeServiceTs } from './user-type.service.ts';

describe('UserTypeServiceTs', () => {
  let service: UserTypeServiceTs;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserTypeServiceTs);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

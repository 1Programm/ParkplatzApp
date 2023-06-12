import { TestBed } from '@angular/core/testing';

import { BuchungService } from './buchung.service';

describe('BuchungService', () => {
  let service: BuchungService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BuchungService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

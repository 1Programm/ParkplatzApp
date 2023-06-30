import { TestBed } from '@angular/core/testing';

import { VerstossService } from './verstoss.service';

describe('VerstossService', () => {
  let service: VerstossService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VerstossService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

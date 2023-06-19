import { TestBed } from '@angular/core/testing';

import { BuchungsuebersichtService } from './buchungsuebersicht.service';

describe('BuchungsuebersichtService', () => {
  let service: BuchungsuebersichtService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BuchungsuebersichtService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { PrintHouseServices } from './print-house-services';

describe('PrintHouseServices', () => {
  let service: PrintHouseServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrintHouseServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

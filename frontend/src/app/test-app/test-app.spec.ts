import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestApp } from './test-app';

describe('TestApp', () => {
  let component: TestApp;
  let fixture: ComponentFixture<TestApp>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TestApp]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestApp);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

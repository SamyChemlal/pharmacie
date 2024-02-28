import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProduitPharmaceutiqueComponent } from './produit-pharmaceutique.component';

describe('ProduitPharmaceutiqueComponent', () => {
  let component: ProduitPharmaceutiqueComponent;
  let fixture: ComponentFixture<ProduitPharmaceutiqueComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProduitPharmaceutiqueComponent]
    });
    fixture = TestBed.createComponent(ProduitPharmaceutiqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

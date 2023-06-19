import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor() { }

  public getMitarbeiterID(): number {
    return 1;
  }

  public getMitarbeiterIDAsObservable() : Observable<number> {
    return of(this.getMitarbeiterID());
  }
}

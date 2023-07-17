import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AddressValidationService {

  constructor() { }


  
  public getAllParkhausAndParkflaeche(): Observable<ParkhausParkflaecheDto[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<ParkhausParkflaecheDto[]>(`${environment.apiServerUrl}/admin/parkhaus`)
    );
  }


  
}

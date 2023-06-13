import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { ParkflaecheAuswahlDto } from '../dtos/parkflaeche-auswahl.dto';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BuchungService {

  constructor(private http: HttpClient){}

  public getParkanlagen(): Observable<ParkflaecheAuswahlDto[]> {
    return this.http.get<ParkflaecheAuswahlDto[]>(`${environment.apiServerUrl}/buchung/parkanlagen`).pipe(
      retry(1),
      catchError(this.handleError)
    )
  }


  // Error handling
  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }

}

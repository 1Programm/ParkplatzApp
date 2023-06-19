import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { ParkflaecheAuswahlDto } from '../dtos/parkflaeche-auswahl.dto';
import { environment } from 'src/environments/environment';
import { Parkhaus } from '../facade/Parkhaus';
import { Parkflaeche } from '../facade/Parkflaeche';
import { Parkplatz } from '../facade/Parkplatz';
import { Parkplatztyp } from '../facade/Parkplatztyp';

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

  public getParkplaetzeOfParkflaeche(parkflaecheID:number): Observable<Parkplatz[]> {
    return this.http.get<Parkplatz[]>(`${environment.apiServerUrl}/buchung/parkplatz/${parkflaecheID}`)
  }

  public getParkplatztypen(): Observable<Parkplatztyp[]> {
    return this.http.get<Parkplatztyp[]>(`${environment.apiServerUrl}/buchung/parkplatztypen`)
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

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { ParkflaecheAuswahlDto } from '../facade/dto/parkflaeche-auswahl.dto';
import { environment } from 'src/environments/environment';
import { Parkhaus } from '../facade/Parkhaus';
import { Parkflaeche } from '../facade/Parkflaeche';
import { Parkplatz } from '../facade/Parkplatz';
import { Parkplatztyp } from '../facade/Parkplatztyp';
import { Preiskategorie } from '../facade/Preiskategorie';
import { Kennzeichen } from '../facade/Kennzeichen';
import { AccountService } from './account.service';
import { BuchungDto } from '../facade/dto/BuchungDto';

@Injectable({
  providedIn: 'root'
})
export class BuchungService {

  constructor(private http: HttpClient, private accountService: AccountService){}

  public getParkanlagen(): Observable<ParkflaecheAuswahlDto[]> {
    return this.http.get<ParkflaecheAuswahlDto[]>(`${environment.apiServerUrl}/buchung/parkanlagen`).pipe(
      retry(1),
      catchError(this.handleError)
    )
  }

  public getParkplaetzeOfParkflaeche(parkflaecheID:number): Observable<Parkplatz[]> {
    return this.http.get<Parkplatz[]>(`${environment.apiServerUrl}/buchung/parkplatz/${parkflaecheID}`)
    .pipe(
      retry(1),
      catchError(this.handleError)
    )
  }

  public getParkplatztypen(): Observable<Parkplatztyp[]> {
    return this.http.get<Parkplatztyp[]>(`${environment.apiServerUrl}/buchung/parkplatztypen`)
    .pipe(
      retry(1),
      catchError(this.handleError)
    )
  }

  public getPreiskategorien(): Observable<Preiskategorie[]> {
    return this.http.get<Preiskategorie[]>(`${environment.apiServerUrl}/buchung/preiskategorien`)    
    .pipe(
      retry(1),
      catchError(this.handleError)
    )
  }

  public saveParkplatz(parkplatz: Parkplatz): Observable<Parkplatz[]> {
    return this.http.post<Parkplatz[]>(`${environment.apiServerUrl}/buchung/parkplatz`, parkplatz)
    .pipe(
      retry(1),
      catchError(this.handleError)
    )
  }

  public getBuchungenForMitarbeiter(): Observable<BuchungDto[]> {
    let mitarbeiterID = this.accountService.getMitarbeiterID();
    
    return this.http.get<BuchungDto[]>(`${environment.apiServerUrl}/buchungen/${mitarbeiterID}`)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  public getKennzeichenForMitarbeiter(): Observable<Kennzeichen[]> {
    let mitarbeiterID = this.accountService.getMitarbeiterID();

    return this.http.get<Kennzeichen[]>(`${environment.apiServerUrl}/buchungen/${mitarbeiterID}/kennzeichen`)
    .pipe(
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

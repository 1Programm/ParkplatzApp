import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { Mitarbeiter } from '../facade/Mitarbeiter';
import { Kennzeichen } from '../facade/Kennzeichen';
import { environment } from 'src/environments/environment';
import { AccountService } from './account.service';
import { Buchung } from '../facade/Buchung';
@Injectable({
  providedIn: 'root'
})
export class ProfilServiceService {

  constructor(private http: HttpClient, private accountService: AccountService) { }
  

  public getMitarbeiter(mitarbeiterID: number): Observable<Mitarbeiter> {
    return this.http.get<Mitarbeiter>(`${environment.apiServerUrl}/profil/${mitarbeiterID}`)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  public createKennzeichenForMitarbeiter(kennzeichen: string): Observable<Mitarbeiter> {
    let mitarbeiterID = this.accountService.getMitarbeiterID();
    
    return this.http.post<Mitarbeiter>(`${environment.apiServerUrl}/profil/${mitarbeiterID}`, {kennzeichen: kennzeichen})
      .pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  public updateKennzeichenForMitarbeiter(kennzeichen: Kennzeichen): Observable<Mitarbeiter> {
    let mitarbeiterID = this.accountService.getMitarbeiterID();
    
    return this.http.put<Mitarbeiter>(`${environment.apiServerUrl}/profil/` + mitarbeiterID, kennzeichen)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  public deleteKennzeichenFromMitarbeiter(kennzeichenID: number): Observable<Mitarbeiter> {
    let mitarbeiterID = this.accountService.getMitarbeiterID();
    
    return this.http.delete<Mitarbeiter>(`${environment.apiServerUrl}/profil/${mitarbeiterID}/kennzeichen/${kennzeichenID}`)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  public getBuchungForKennzeichen(kennzeichenID: number): Observable<Buchung[]> {
    let mitarbeiterID = this.accountService.getMitarbeiterID();
    
    return this.http.get<Buchung[]>(`${environment.apiServerUrl}/profil/${kennzeichenID}/${mitarbeiterID}`)
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


import { Injectable } from '@angular/core';
import { BuchungDto } from '../facade/dto/BuchungDto';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Kennzeichen } from '../facade/Kennzeichen';
import { Buchung } from '../facade/Buchung';
import { AccountService } from './account.service';

@Injectable({
  providedIn: 'root'
})
export class BuchungsuebersichtService {
  constructor(private http: HttpClient, private accountService: AccountService) { }
  

  public getBuchungenForMitarbeiter(): Observable<BuchungDto[]> {
    let mitarbeiterID = this.accountService.getMitarbeiterID();
    
    return this.http.get<BuchungDto[]>(`${environment.apiServerUrl}/buchungen/${mitarbeiterID}`)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  public deleteBuchungFromMitarbeiter(buchungID: number): Observable<BuchungDto[]> {
    let mitarbeiterID = this.accountService.getMitarbeiterID();

    const url = `${environment.apiServerUrl}/buchungen/${mitarbeiterID}/buchung/${buchungID}`; 
    return this.http.delete<BuchungDto[]>(url)
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

  public saveKennzeichenForBuchung(buchungID: number, kennzeichenID: number) {
    return this.http.post<BuchungDto[]>(`${environment.apiServerUrl}/buchungen/${buchungID}/kennzeichen/${kennzeichenID}`, null)
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

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { Mitarbeiter } from '../facade/Mitarbeiter';
import { Kennzeichen } from '../facade/Kennezeichen';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class ProfilServiceService {

  constructor(private http: HttpClient) { }
  

  public getMitarbeiter(mitarbeiterID: number): Observable<Mitarbeiter> {
    return this.http.get<Mitarbeiter>(`${environment.apiServerUrl}/profil/${mitarbeiterID}`)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  public createKennzeichenForMitarbeiter(mitarbeiterID: number, kennzeichen: string): Observable<Mitarbeiter> {
    return this.http.post<Mitarbeiter>(`${environment.apiServerUrl}/profil/${mitarbeiterID}`, {kennzeichen: kennzeichen})
      .pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  public updateKennzeichenForMitarbeiter(mitarbeiterID: number, kennzeichen: Kennzeichen): Observable<Mitarbeiter> {
    return this.http.put<Mitarbeiter>(`${environment.apiServerUrl}/profil/` + mitarbeiterID, kennzeichen)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  public deleteKennzeichenFromMitarbeiter({ mitarbeiterID, kennzeichenID }: { mitarbeiterID: number; kennzeichenID: number; }): Observable<Mitarbeiter> {
      const url = `${environment.apiServerUrl}/profil/${mitarbeiterID}/kennzeichen/${kennzeichenID}`; 
     return this.http.delete<Mitarbeiter>(url)
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


import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { Mitarbeiter } from '../facade/Mitarbeiter';
import { Kennzeichen } from '../facade/Kennezeichen';
@Injectable({
  providedIn: 'root'
})
export class ProfilServiceService {

  constructor(private http: HttpClient) { }
  apiServerUrl = "localhost:8080";

  getMitarbeiter(mitarbeiterId: number): Observable<Mitarbeiter> {
    return this.http.get<any>(`{$this.apiServerUrl}/profil/`)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  public createKennzeichen(mitarbeiterId: number, kennzeichen: string): Observable<Mitarbeiter> {
    return this.http.post<Mitarbeiter>(`${this.apiServerUrl}/umweltgutachter/` + mitarbeiterId, kennzeichen)
      .pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  public updateKennzeichen(mitarbeiterId: number, kennzeichenId: string): Observable<Mitarbeiter> {
    return this.http.put<Mitarbeiter>(`${this.apiServerUrl}/umweltgutachter/` + mitarbeiterId, kennzeichenId)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  public loescheKennzeichenFromMitarbeiter(mitarbeiterId: number, kennzeichenId: number): Observable<Mitarbeiter> {
    return this.http.post<Mitarbeiter>(`${this.apiServerUrl}/profil/delete-kennzeichen` + mitarbeiterId, kennzeichenId)
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


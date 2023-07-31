import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { Mitarbeiter } from '../facade/Mitarbeiter';
import { Kennzeichen } from '../facade/Kennzeichen';
import { environment } from 'src/environments/environment';
import { AccountService } from './account.service';
import { ServiceBase } from './service-utils';
import { Buchung } from '../facade/Buchung';
@Injectable({
  providedIn: 'root'
})
export class ProfilServiceService extends ServiceBase {

  mitarbeiterID: number;
  constructor(private http: HttpClient) {
    super();
  }
  
  public getMitarbeiter(mitarbeiterID: number): Observable<Mitarbeiter> {
    return this.wrapRetryAndCatchError(
      this.http.get<Mitarbeiter>(`${environment.apiServerUrl}/profil/${mitarbeiterID}`)
    );
  }

  public createKennzeichenForMitarbeiter(mitarbeiterID: number, kennzeichen: string): Observable<Mitarbeiter> {
    return this.wrapRetryAndCatchError(
      this.http.post<Mitarbeiter>(`${environment.apiServerUrl}/profil/${mitarbeiterID}`, kennzeichen)
    );
  }

  public updateKennzeichenForMitarbeiter(mitarbeiterID: number, kennzeichen: Kennzeichen): Observable<Mitarbeiter> {
    return this.wrapRetryAndCatchError(
      this.http.put<Mitarbeiter>(`${environment.apiServerUrl}/profil/` + mitarbeiterID, kennzeichen)
    );
  }

  public deleteKennzeichenFromMitarbeiter(mitarbeiterID: number, kennzeichenID: number): Observable<Mitarbeiter> {
    return this.wrapRetryAndCatchError(
      this.http.delete<Mitarbeiter>(`${environment.apiServerUrl}/profil/${mitarbeiterID}/kennzeichen/${kennzeichenID}`)
    );
  }

}


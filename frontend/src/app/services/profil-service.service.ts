import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mitarbeiter } from '../facade/Mitarbeiter';
import { Kennzeichen } from '../facade/Kennzeichen';
import { environment } from 'src/environments/environment';
import { AccountService } from './account.service';
import { ServiceBase } from './service-utils';
@Injectable({
  providedIn: 'root'
})
export class ProfilServiceService extends ServiceBase {

  constructor(private http: HttpClient, private accountService: AccountService) {
    super();
  }
  

  public getMitarbeiter(mitarbeiterID: number): Observable<Mitarbeiter> {
    return this.wrapRetryAndCatchError(
      this.http.get<Mitarbeiter>(`${environment.apiServerUrl}/profil/${mitarbeiterID}`)
    );
  }

  public createKennzeichenForMitarbeiter(mitarbeiterID: number, kennzeichen: string): Observable<Mitarbeiter> {
    return this.wrapRetryAndCatchError(
      this.http.post<Mitarbeiter>(`${environment.apiServerUrl}/profil/${mitarbeiterID}`, {kennzeichen: kennzeichen})
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

